package showcase.smpp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.handler.SmppHandler;
import showcase.smpp.handler.StreamDecoder;
import showcase.smpp.handler.StreamEncoder;
import showcase.smpp.pdu.HeaderPdu;
import showcase.smpp.pdu.Pdu;
import showcase.smpp.pdu.PduFactory;
import showcase.smpp.pdu.body.BindTransceiverBody;
import showcase.smpp.pdu.body.SubmitAndDeliverBody;
import showcase.smpp.util.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SmppClient {
    private static final Logger logger = LogManager.getLogger();
    private static volatile boolean flag = false;

    public static void main(String[] args) throws Exception {
        SMPPConfig.Client config = SMPPConfig.getClientConfig();

        EventLoopGroup group = null;
        CountDownLatch connectCountDownLatch = new CountDownLatch(1);
        CountDownLatch loginCountDownLatch = new CountDownLatch(1);
        SmppHandler handler = new SmppHandler(connectCountDownLatch, loginCountDownLatch);
        try {
            group = new NioEventLoopGroup(1);
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {

                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(
                             new LoggingHandler(LogLevel.INFO)
                            , new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, -4, 0)
                            , new StreamEncoder()
                            , new StreamDecoder()
                            , new IdleStateHandler(100, 100, 100, TimeUnit.SECONDS)
                            , handler
                     );
                 }
             });
            ChannelFuture f = b.connect(config.getIp(), config.getPort()).addListener(new GenericFutureListener() {

                @Override
                public void operationComplete(Future future) throws Exception {
                    if (future.isSuccess()) {
                        logger.info("连接成功");
                    }
                    else {
                        logger.error("连接失败");
                        logger.error(future.cause().getMessage(), future.cause());
                    }
                }
            });

            try {
                connectCountDownLatch.await();
                if(!handler.isOpen()) return;

                // 登陆
                String account = config.getBind().getAccount();
                String password = config.getBind().getPasswd();
                String systemType = config.getBind().getSystemType();
                Pdu bindPdu = PduFactory.newPduInstance(CommandId.BIND_TRANSCEIVER);
                HeaderPdu bindHeader = PduFactory.newHeaderInstance(CommandId.BIND_TRANSCEIVER);
                BindTransceiverBody bindTransceiverBody = new BindTransceiverBody();
                bindTransceiverBody.setSystemId(account);
                bindTransceiverBody.setPassword(password);
                bindTransceiverBody.setSystemType(systemType);
                bindTransceiverBody.setInterfaceVersion((byte)0x34);
                bindPdu.setHeaderPdu(bindHeader);
                bindPdu.setBodyPdu(bindTransceiverBody);
                handler.login(bindPdu);

                loginCountDownLatch.await();
                if (!handler.isLogin()) return;

                // 发消息
                String sourceAddr = config.getSubmit().getFrom();
                String destionationAddr = config.getSubmit().getTo();
                String content = config.getSubmit().getContent();
                Integer maxLength = config.getSubmit().getMaxLength();
                Integer splitLength = config.getSubmit().getSplitLength();
                CharsetEnum charsetEnum = CharsetEnum.DEFAULT;
                if (config.getSubmit().getCharset() != null) {
                    charsetEnum = charsetEnum.chooseCharsetEnum(config.getSubmit().getCharset());
                }
                Byte dcs = config.getSubmit().getDcs();
                if (content.length() > maxLength.intValue()) {
                    int len = content.length() / splitLength == 0 ?  content.length() / splitLength : content.length() / splitLength  + 1;
                    byte serial = (byte)(SequenceUtil.getNextSquence() & 0xFF);
                    for (int i = 0; i < len; i++) {
                        SubmitAndDeliverBody.UDHI udhi = new SubmitAndDeliverBody.UDHI();
                        udhi.setSerial(serial);
                        udhi.setTotalNum((byte)len);
                        udhi.setIndex((byte)(i+1));
                        String text = i + 1 < len ? content.substring(i * splitLength, (i+1) * splitLength) : content.substring(i * splitLength);
                        Pdu sendPdu = PduFactory.newPduInstance(CommandId.SUBMIT_SM);
                        HeaderPdu sendHeader = PduFactory.newHeaderInstance(CommandId.SUBMIT_SM);
                        SubmitAndDeliverBody sendBody = new SubmitAndDeliverBody();
                        sendBody.setSourceAddr(sourceAddr);
                        sendBody.setDestinationAddr(destionationAddr);
                        if (dcs != null) sendBody.setDataCoding(dcs);
                        sendBody.setCharsetEnum(charsetEnum);
                        sendBody.setUdhi(udhi);
                        sendBody.setShortMessage(text);
                        sendPdu.setHeaderPdu(sendHeader);
                        sendPdu.setBodyPdu(sendBody);
                        handler.send(sendPdu);
                        DataHolder.putSeqToContent(String.valueOf(sendPdu.getHeaderPdu().getSequenceNumber()), config.getSubmit().getContent());
                    }
                } else {
                    Pdu sendPdu = PduFactory.newPduInstance(CommandId.SUBMIT_SM);
                    HeaderPdu sendHeader = PduFactory.newHeaderInstance(CommandId.SUBMIT_SM);
                    SubmitAndDeliverBody sendBody = new SubmitAndDeliverBody();
                    sendBody.setSourceAddr(sourceAddr);
                    sendBody.setDestinationAddr(destionationAddr);
                    if (dcs != null) sendBody.setDataCoding(dcs);
                    sendBody.setCharsetEnum(charsetEnum);
                    sendBody.setShortMessage(content);
                    sendPdu.setHeaderPdu(sendHeader);
                    sendPdu.setBodyPdu(sendBody);
                    handler.send(sendPdu);
                    DataHolder.putSeqToContent(String.valueOf(sendPdu.getHeaderPdu().getSequenceNumber()), config.getSubmit().getContent());
                }
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        } finally {
            if (group != null) group.shutdownGracefully();
        }
    }

    public static void shutdown() {
        flag = true;
    }
}
