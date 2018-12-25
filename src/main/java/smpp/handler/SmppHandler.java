package smpp.handler;


import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.SmppClient;
import smpp.pdu.BodyPdu;
import smpp.pdu.HeaderPdu;
import smpp.pdu.Pdu;
import smpp.pdu.PduFactory;
import smpp.pdu.body.BindTransceiverBody;
import smpp.pdu.body.BindTransceiverRespBody;
import smpp.pdu.body.SubmitAndDeliverBody;
import smpp.pdu.body.SubmitAndDeliverRespBody;
import smpp.pdu.entity.BindTransceiverPdu;
import smpp.util.CommandId;
import smpp.util.DeliveryReceiptStatesEnum;
import smpp.util.SequenceUtil;

import java.util.concurrent.TimeUnit;

public class SmppHandler extends ChannelDuplexHandler {
	private final Logger logger = LogManager.getLogger(SmppHandler.class);
	private ChannelHandlerContext ctx;
	private volatile boolean open = false;
	private volatile boolean login = false;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        if (message instanceof Pdu) {
            Pdu pdu = (Pdu) message;
            int commandId = pdu.getHeaderPdu().getCommandId();
            switch (commandId) {
                case CommandId.BIND_TRANSCEIVER:  // 登陆请求
                    logger.info("正在处理登陆信息");
                    handleBind(ctx, pdu);
                    break;
                case CommandId.BIND_TRANSCEIVER_RESP: // 登陆响应
                    int status = pdu.getHeaderPdu().getCommandStatus();
                    logger.info("{}", status == 0 ? "登陆成功":"登陆失败");
                    if (status == 0) {
                        login = true;
                    } else {
                        login = false;
                        ctx.channel().close();
                    }
                    break;
                case CommandId.SUBMIT_SM:
                    handleSubmitSm(ctx, pdu);
                    break;
                case CommandId.DELIVER_SM:  // 上行 / 状态报告
                    Pdu p = PduFactory.newPduInstance(CommandId.DELIVER_SM_RESP);
                    HeaderPdu h = PduFactory.newHeaderInstance(CommandId.DELIVER_SM_RESP);
                    BodyPdu b = PduFactory.newBodyInstance(CommandId.DELIVER_SM_RESP);
                    p.setHeaderPdu(h);
                    p.setBodyPdu(b);
                    ctx.writeAndFlush(p);
                    break;
                case CommandId.ENQUIRE_LINK: // 心跳连接
                    handlePing(ctx);
                    break;
                case CommandId.UNBIND_RESP: // 断开连接响应
                    open = false;
                    login = false;
                    ctx.channel().close();
                    break;
                case CommandId.ENQUIRE_LINK_RESP: // 心跳响应
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
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        open = false;
        SmppClient.setInvalid();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
    }

    // 登录
    public void login(Pdu p) {
        ctx.writeAndFlush(p);
    }

    // 发送短信请求
    public void send(Pdu p) {
        ctx.writeAndFlush(p);
    }

    // 发送关闭请求
    public void close() {
        Pdu p = PduFactory.newPduInstance(CommandId.UNBIND);
        HeaderPdu h = PduFactory.newHeaderInstance(CommandId.UNBIND);
        BodyPdu b = PduFactory.newBodyInstance(CommandId.UNBIND);
        p.setHeaderPdu(h);
        p.setBodyPdu(b);
        ctx.writeAndFlush(p);
    }

    // 心跳响应
    private void handlePing(ChannelHandlerContext ctx) {
        Pdu p = PduFactory.newPduInstance(CommandId.ENQUIRE_LINK_RESP);
        HeaderPdu header = PduFactory.newHeaderInstance(CommandId.ENQUIRE_LINK_RESP);
        BodyPdu body = PduFactory.newBodyInstance(CommandId.ENQUIRE_LINK_RESP);
        p.setHeaderPdu(header);
        p.setBodyPdu(body);
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

    // 处理短信提交
    private void handleSubmitSm(ChannelHandlerContext ctx, Pdu pdu) {
        Pdu p = PduFactory.newPduInstance(CommandId.SUBMIT_SM_RESP);
        HeaderPdu h = PduFactory.newHeaderInstance(CommandId.SUBMIT_SM_RESP);
        h.setSequenceNumber(pdu.getHeaderPdu().getSequenceNumber());
        String msgId = String.valueOf(SequenceUtil.getNextSquence());
        SubmitAndDeliverRespBody b = new SubmitAndDeliverRespBody();
        b.setMessageId(String.valueOf(msgId));
        p.setHeaderPdu(h);
        p.setBodyPdu(b);
        ctx.writeAndFlush(p);
        DeliveryReciptTask task = new DeliveryReciptTask(ctx, msgId);
        ctx.executor().schedule(task, 10, TimeUnit.SECONDS);
    }

    private class DeliveryReciptTask implements Runnable {
        private ChannelHandlerContext ctx;
        private String messageId;

        public DeliveryReciptTask(ChannelHandlerContext ctx, String messageId) {
            this.ctx = ctx;
            this.messageId = messageId;
        }

        @Override
        public void run() {
            Pdu p = PduFactory.newPduInstance(CommandId.DELIVER_SM);
            HeaderPdu h = PduFactory.newHeaderInstance(CommandId.DELIVER_SM);
            SubmitAndDeliverBody b = new SubmitAndDeliverBody();
            SubmitAndDeliverBody.Receipt receipt = SubmitAndDeliverBody.Receipt.success(messageId, DeliveryReceiptStatesEnum.DELIVERED);
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
