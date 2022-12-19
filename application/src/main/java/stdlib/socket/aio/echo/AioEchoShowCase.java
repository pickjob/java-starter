package stdlib.socket.aio.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * AIO Socket 示例
 *
 * @author: pickjob@126.com
 * @date: 2022-12-19
 */
public class AioEchoShowCase {
    private static Logger logger = LogManager.getLogger();
    private static final int SERVER_PORT = 8080;

    private static final InetSocketAddress serverAddr = new InetSocketAddress("localhost", SERVER_PORT);
    private static final CountDownLatch serverReady = new CountDownLatch(1);
    private static final CountDownLatch clientFinish = new CountDownLatch(3);

    public static void main(String[] args) {
        // 启动服务端
        new Thread(AioEchoShowCase::serverRunnable).start();
        // 启动客户端
        new Thread(AioEchoShowCase::clientRunnable).start();
        new Thread(AioEchoShowCase::clientRunnable).start();
        new Thread(AioEchoShowCase::clientRunnable).start();
    }

    private static void serverRunnable() {
        try (AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(serverAddr);) {
            serverReady.countDown();
            logger.info("Server is running ... ");
            listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel clientSocketChannel, Object attachment) {
                    listener.accept(null, this);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    clientSocketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            buffer.flip();
                            String receivedMessage = Charset.defaultCharset().decode(buffer).toString();
                            logger.info("{} From Client", receivedMessage);
                            ByteBuffer responBuffer = null;
                            String echoString = "你好, Blocking Socket client.";
                            responBuffer = ByteBuffer.wrap(echoString.getBytes());
                            clientSocketChannel.write(responBuffer, null, new CompletionHandler<Integer, Object>() {
                                @Override
                                public void completed(Integer result, Object attachment) {
                                    try {
                                        clientSocketChannel.close();
                                    } catch (IOException e) {
                                        logger.error(e.getMessage(), e);
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, Object attachment) {
                                    try {
                                        clientSocketChannel.close();
                                    } catch (IOException e) {
                                        logger.error(e.getMessage(), e);
                                    }
                                }
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
            try {
                clientFinish.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        try (Socket clientSocket = new Socket(serverAddr.getAddress(), serverAddr.getPort())) {
            logger.info("Blocking client is running ...");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String echoString = "你好, NIO Socket Server!";
            out.println(echoString);
            String inputLine =  in.readLine();
            logger.info("{} From Server@{}:{}", inputLine, clientSocket.getInetAddress(), clientSocket.getPort());
        } catch (Exception ex) {
            logger.error(ex);
        }
        clientFinish.countDown();
    }
}
