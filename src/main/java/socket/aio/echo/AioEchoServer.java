package socket.aio.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CommonKey;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.Date;

public class AioEchoServer {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(CommonKey.PORT));
            logger.info("Server is listening on " + CommonKey.PORT);
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
                                public void completed(Integer result, Object attachment) {
                                }

                                @Override
                                public void failed(Throwable exc, Object attachment) {
                                }
                            });
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    if (listener != null) try {
                        listener.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            while (true){
                Thread.sleep(60000);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
