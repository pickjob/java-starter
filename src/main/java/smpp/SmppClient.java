package smpp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.handler.SmppHandler;
import smpp.handler.StreamDecoder;
import smpp.handler.StreamEncoder;
import smpp.pdu.HeaderPdu;
import smpp.pdu.Pdu;
import smpp.pdu.PduFactory;
import smpp.pdu.body.BindTransceiverBody;
import smpp.pdu.body.SubmitAndDeliverBody;
import smpp.util.CharsetEnum;
import smpp.util.CommandId;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SmppClient {
    private static Logger logger = LogManager.getLogger(SmppClient.class);
    private static volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        String ip = "";
        int port = 0;

        SmppHandler handler = new SmppHandler();
        EventLoopGroup group = null;
        CountDownLatch countDownLatch = new CountDownLatch(1);
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
                             new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, -4, 0)
                            , new StreamEncoder()
                            , new StreamDecoder()
                            , new IdleStateHandler(100, 100, 100, TimeUnit.SECONDS)
                            , handler
                     );
                 }
             });
            b.connect(ip, port).addListener(new GenericFutureListener() {

                @Override
                public void operationComplete(Future future) throws Exception {
                    if (future.isSuccess()) {
                        logger.info("连接成功");
                        countDownLatch.countDown();
                    }
                    else {
                        logger.info("连接失败");
                        logger.error(future.cause().getMessage(), future.cause());
                        flag = false;
                    }
                }
            });

            try {
                countDownLatch.await();
                String account = "";
                String password = "";
                String systemType = "";
                String sourceAddr = "";
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
                if (!handler.isLogin()) Thread.sleep(1000);
                String phone = "971589076718";
                String content = "mlwyhqnalogq@£\\$¥èéùìòÇ\\nØø\\rÅå?_F\"?O??ST?ÆæßÉ!\"#¤%&'()*+,-../:;<=>?¡?ÖÑÜ§¿öñüàäÄ {}\\~[|";
                CharsetEnum charsetEnum = CharsetEnum.DEFAULT;
                String serviceType = "1";

                Pdu sendPdu = PduFactory.newPduInstance(CommandId.SUBMIT_SM);
                HeaderPdu sendHeader = PduFactory.newHeaderInstance(CommandId.SUBMIT_SM);
                SubmitAndDeliverBody sendBody = new SubmitAndDeliverBody();
                sendBody.setServiceType(serviceType);
                sendBody.setSourceAddr(sourceAddr);
                sendBody.setDestinationAddr(phone);
                sendBody.setDataCoding((byte)3);
                sendBody.setCharsetEnum(charsetEnum);
                sendBody.setShortMessage(content);
                sendPdu.setHeaderPdu(sendHeader);
                sendPdu.setBodyPdu(sendBody);
                handler.send(sendPdu);
                Thread.sleep(Integer.MAX_VALUE);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        } finally {
            if (group != null) group.shutdownGracefully();
        }
    }

    public static void setInvalid() {
        flag = false;
    }
}
