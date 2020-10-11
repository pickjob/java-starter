package basic.socket.aio.echo;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.Date;

public class AioEchoServerShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(AioEchoServerShowCase.class);

    @Override
    public void showSomething() {
        try {
            AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8080));
            logger.info("Server is listening on {}", 8080);
            listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel clientSocketChannel, Object attachment) {
                    listener.accept(null, this);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    clientSocketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            buffer.flip();
                            String receivedMessage = Charset.forName("utf-8").decode(buffer).toString();
                            logger.info(receivedMessage);
                            String sendString = "你好,客户端. @" + new Date().toString() + "，已经收到你的信息" + receivedMessage;
                            ByteBuffer responBuffer = null;
                            try {
                                responBuffer = ByteBuffer.wrap(sendString.getBytes("utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            clientSocketChannel.write(responBuffer, null, new CompletionHandler<Integer, Object>() {
                                @Override
                                public void completed(Integer result, Object attachment) {}

                                @Override
                                public void failed(Throwable exc, Object attachment) {}
                            });
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {}
                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    if (listener != null) try {
                        listener.close();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
