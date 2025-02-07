package showcase.smpp.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.BodyPdu;
import showcase.smpp.pdu.HeaderPdu;
import showcase.smpp.pdu.Pdu;
import showcase.smpp.pdu.PduFactory;
import showcase.smpp.pdu.body.BindTransceiverBody;
import showcase.smpp.pdu.body.BindTransceiverRespBody;
import showcase.smpp.pdu.body.SubmitAndDeliverBody;
import showcase.smpp.pdu.body.SubmitAndDeliverRespBody;
import showcase.smpp.pdu.entity.BindTransceiverPdu;
import showcase.smpp.pdu.entity.TLV;
import showcase.smpp.util.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class SmppHandler extends ChannelDuplexHandler {
    private final Logger logger = LogManager.getLogger();
    private ChannelHandlerContext ctx;
    private volatile boolean open = false;
    private volatile boolean login = false;
    private CountDownLatch connectCountDownLatch;
    private CountDownLatch loginCountDownLatch;

    private static final AtomicLong idGenerator = new AtomicLong(0);

    public SmppHandler() {}

    public SmppHandler(CountDownLatch connectCountDownLatch, CountDownLatch loginCountDownLatch) {
        this.connectCountDownLatch = connectCountDownLatch;
        this.loginCountDownLatch = loginCountDownLatch;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        if (message instanceof Pdu) {
            Pdu pdu = (Pdu) message;
            int commandId = pdu.getHeaderPdu().getCommandId();
            switch (commandId) {
                case CommandId.BIND_TRANSCEIVER:  // 登陆请求
                    handleBind(ctx, pdu);
                    break;
                case CommandId.BIND_TRANSCEIVER_RESP: // 登陆响应
                    handleBindResp(ctx, pdu);
                    break;
                case CommandId.SUBMIT_SM:
                    handleSubmitSm(ctx, pdu);
                    break;
                case CommandId.SUBMIT_SM_RESP:
                    handleSubmitSmResp(ctx, pdu);
                    break;
                case CommandId.DELIVER_SM:  // 上行 / 状态报告
                    handleDeliver(ctx, pdu);
                    break;
                case CommandId.DELIVER_SM_RESP: // 上行或状态报告响应
                    break;
                case CommandId.ENQUIRE_LINK: // 心跳连接
                    handlePing(ctx, pdu);
                    break;
                case CommandId.ENQUIRE_LINK_RESP: // 心跳响应
                    break;
                case CommandId.UNBIND:
                    handleUnbind(ctx, pdu);
                    break;
                case CommandId.UNBIND_RESP: // 断开连接响应
                    ctx.channel().close();
                    break;
                default:
                    break;
            }
        }
        ReferenceCountUtil.release(message);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent){
            Pdu pdu = PduFactory.newPduInstance(CommandId.ENQUIRE_LINK);
            HeaderPdu headerPdu = PduFactory.newHeaderInstance(CommandId.ENQUIRE_LINK);
            BodyPdu bodyPdu = PduFactory.newBodyInstance(CommandId.ENQUIRE_LINK);
            pdu.setHeaderPdu(headerPdu);
            pdu.setBodyPdu(bodyPdu);
            ctx.writeAndFlush(pdu);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("通道激活");
        this.ctx = ctx;
        open = true;
        if (connectCountDownLatch != null) connectCountDownLatch.countDown();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("通道失效");
        open = false;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
    }

    // 发送短信请求
    public void send(Pdu p) {
        ctx.writeAndFlush(p);
    }

    // 处理短信提交
    private void handleSubmitSm(ChannelHandlerContext ctx, Pdu pdu) {
        Pdu p = PduFactory.newPduInstance(CommandId.SUBMIT_SM_RESP);
        HeaderPdu h = PduFactory.newHeaderInstance(CommandId.SUBMIT_SM_RESP);
        h.setSequenceNumber(pdu.getHeaderPdu().getSequenceNumber());
        String msgId = String.valueOf(idGenerator.incrementAndGet());
        SubmitAndDeliverRespBody b = new SubmitAndDeliverRespBody();
        b.setMessageId(msgId);
        p.setHeaderPdu(h);
        p.setBodyPdu(b);
        ctx.writeAndFlush(p);
        DeliveryReciptTask task = new DeliveryReciptTask(ctx, msgId, pdu);
        ctx.executor().schedule(task, 10, TimeUnit.SECONDS);
    }

    // 发送短信提交响应
    public void handleSubmitSmResp(ChannelHandlerContext ctx, Pdu pdu) {
        if(pdu.getHeaderPdu().getCommandStatus() != 0) {
            logger.error("提交失败, 响应状态 {}", pdu.getHeaderPdu().getCommandStatus());
            return;
        }
        String messageId = ((SubmitAndDeliverRespBody)pdu.getBodyPdu()).getMessageId();
        logger.info("提交成功, messageId {} ", messageId);
        DataHolder.putMessageIdToSeq(messageId, String.valueOf(pdu.getHeaderPdu().getSequenceNumber()));
    }

    public void handleDeliver(ChannelHandlerContext ctx, Pdu pdu) {
        SubmitAndDeliverBody body = (SubmitAndDeliverBody)pdu.getBodyPdu();
        if ((body.getEsmClass() & EsmClassEnum.ConcatenatedMessages.getEsmClass()) == EsmClassEnum.ConcatenatedMessages.getEsmClass()) {
            logger.info("收到长短信上行短信 {}", pdu);
        }
        if ((body.getEsmClass() & EsmClassEnum.DeliveryReceipt.getEsmClass()) == EsmClassEnum.DeliveryReceipt.getEsmClass()) {
            logger.info("收到状态报告 {}", pdu);
            SubmitAndDeliverBody.Receipt receipt = body.getReceipt();
            String content = DataHolder.getContent(receipt.getMessageId());
            if (StringUtils.isBlank(content) && body.getTlvs() != null && body.getTlvs().size() > 0) {
                for (TLV tlv : body.getTlvs()) {
                    if (tlv.getTag() == TLVTagIds.RECEIPTED_MESSAGE_ID) {
                        try {
                            String messageId = StringCodeUtil.decodingCString(tlv.getValues());
                            content = DataHolder.getContent(messageId);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
            DataHolder.remove(receipt.getMessageId());
            if (StringUtils.isNoneBlank(content)) {
                logger.info("成功响应 {}", content);
            }
            if (DataHolder.getSize() == 0) {
                ctx.executor().schedule(() -> {
                    Pdu p = PduFactory.newPduInstance(CommandId.UNBIND);
                    HeaderPdu h = PduFactory.newHeaderInstance(CommandId.UNBIND);
                    BodyPdu b = PduFactory.newBodyInstance(CommandId.UNBIND);
                    p.setHeaderPdu(h);
                    p.setBodyPdu(b);
                    ctx.writeAndFlush(p);
                }, 5, TimeUnit.SECONDS);
            }
        }
        Pdu p = PduFactory.newPduInstance(CommandId.DELIVER_SM_RESP);
        HeaderPdu h = PduFactory.newHeaderInstance(CommandId.DELIVER_SM_RESP);
        h.setSequenceNumber(pdu.getHeaderPdu().getSequenceNumber());
        BodyPdu b = PduFactory.newBodyInstance(CommandId.DELIVER_SM_RESP);
        p.setHeaderPdu(h);
        p.setBodyPdu(b);
        ctx.writeAndFlush(p);

    }

    // 登录
    public void login(Pdu p) {
        ctx.writeAndFlush(p);
    }

    // 处理登陆
    private void handleBind(ChannelHandlerContext ctx, Pdu pdu) {
        Pdu p = PduFactory.newPduInstance(CommandId.BIND_TRANSCEIVER_RESP);
        HeaderPdu header = PduFactory.newHeaderInstance(CommandId.BIND_TRANSCEIVER_RESP);
        BindTransceiverRespBody body = new BindTransceiverRespBody();
        if (pdu instanceof BindTransceiverPdu) {
            String systemId = ((BindTransceiverBody)((BindTransceiverPdu)pdu).getBodyPdu()).getSystemId();
            body.setSystemId(systemId);
        }
        p.setHeaderPdu(header);
        p.setBodyPdu(body);
        ctx.writeAndFlush(p);
    }

    // 粗粒退出
    private void handleUnbind(ChannelHandlerContext ctx, Pdu pdu) {
        Pdu p = PduFactory.newPduInstance(CommandId.UNBIND_RESP);
        HeaderPdu h = PduFactory.newHeaderInstance(CommandId.UNBIND_RESP);
        BodyPdu b = PduFactory.newBodyInstance(CommandId.UNBIND_RESP);
        p.setHeaderPdu(h);
        p.setBodyPdu(b);
        ctx.writeAndFlush(p);
    }

    // 处理登陆响应
    private void handleBindResp(ChannelHandlerContext ctx, Pdu pdu) {
        int status = pdu.getHeaderPdu().getCommandStatus();
        logger.info("{}", status == 0 ? "登陆成功":"登陆失败");
        if (status == 0) {
            login = true;
        } else {
            login = false;
            ctx.channel().close();
        }
        if (loginCountDownLatch != null) loginCountDownLatch.countDown();
    }

    // 心跳响应
    private void handlePing(ChannelHandlerContext ctx, Pdu pdu) {
        Pdu p = PduFactory.newPduInstance(CommandId.ENQUIRE_LINK_RESP);
        HeaderPdu header = PduFactory.newHeaderInstance(CommandId.ENQUIRE_LINK_RESP);
        BodyPdu body = PduFactory.newBodyInstance(CommandId.ENQUIRE_LINK_RESP);
        p.setHeaderPdu(header);
        p.setBodyPdu(body);
        ctx.writeAndFlush(p);
    }

    // 推状态报告任务
    private class DeliveryReciptTask implements Runnable {
        private ChannelHandlerContext ctx;
        private String messageId;
        private Pdu pdu;

        public DeliveryReciptTask(ChannelHandlerContext ctx, String messageId, Pdu pdu) {
            this.ctx = ctx;
            this.messageId = messageId;
            this.pdu = pdu;
        }

        @Override
        public void run() {
            Pdu p = PduFactory.newPduInstance(CommandId.DELIVER_SM);
            HeaderPdu h = PduFactory.newHeaderInstance(CommandId.DELIVER_SM);
            SubmitAndDeliverBody b = new SubmitAndDeliverBody();
            SubmitAndDeliverBody.Receipt receipt = SubmitAndDeliverBody.Receipt.success(messageId, DeliveryReceiptStatesEnum.DELIVERED);
            SubmitAndDeliverBody body = (SubmitAndDeliverBody)pdu.getBodyPdu();
            receipt.setText(body.getShortMessage().length() > 20 ? body.getShortMessage().substring(0, 20):body.getShortMessage());
            b.setSourceAddr(body.getDestinationAddr());
            b.setDestinationAddr(body.getSourceAddr());
            b.setReceipt(receipt);
            p.setHeaderPdu(h);
            p.setBodyPdu(b);
            ctx.writeAndFlush(p);
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
