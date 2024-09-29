package app.standard.socket.nio.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

/**
 * NIO Socket 示例
 *
 * @author: pickjob@126.com
 * @date: 2022-12-19
 */
public class NioEchoShowcase {
    private static Logger logger = LogManager.getLogger();
    private static final int SERVER_PORT = 8080;

    private static final InetSocketAddress serverInet = new InetSocketAddress("localhost", SERVER_PORT);
    private static final CountDownLatch serverReady = new CountDownLatch(1);
    private static final CountDownLatch clientFinish = new CountDownLatch(3);

    public static void main(String[] args) {
        // 启动服务端
        new Thread(NioEchoShowcase::serverRunnable).start();
        // 启动客户端\
        for (int i = 0; i < 3; i++) {
            new Thread(NioEchoShowcase::clientRunnable).start();
        }
    }

    private static void serverRunnable() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();) {
            serverSocketChannel.bind(serverInet);
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverReady.countDown();
            logger.info("Nio server is running ... ");
            while (true){
                int readyNum = selector.select();
                if (0 == readyNum) continue;
                Iterator<SelectionKey> selectKeyIterator = selector.selectedKeys().iterator();
                while (selectKeyIterator.hasNext()) {
                    SelectionKey key = selectKeyIterator.next();
                    selectKeyIterator.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel sc = (ServerSocketChannel) key.channel();
                        SocketChannel clientSocketChanel = sc.accept();
                        clientSocketChanel.configureBlocking(false);
                        clientSocketChanel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, clientSocketChanel.socket().getPort());
                    } else if (key.isReadable()) {
                        try {
                            ByteBuffer readBuff = ByteBuffer.allocate(1024);
                            readBuff.clear();
                            SocketChannel clientSocketChannel = (SocketChannel) key.channel();
                            clientSocketChannel.read(readBuff);
                            readBuff.flip();
                            String receivedMessage = Charset.defaultCharset().newDecoder().decode(readBuff).toString();
                            logger.info("{} PORT:{} From Client@{}:{}", readBuff, receivedMessage, clientSocketChannel.socket().getInetAddress(), clientSocketChannel.socket().getPort());
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    } else if (key.isWritable()) {
                        try {
                            String echoString = "你好, Blocking Socket Client! [" + key.attachment() + "]\r\n";
                            ByteBuffer writeBuff = ByteBuffer.wrap(echoString.getBytes());
                            SocketChannel clientSocketChannel = (SocketChannel) key.channel();
                            clientSocketChannel.write(writeBuff);
                            if (clientFinish.getCount() == 0) {
                                return;
                            }
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
        } catch (Exception e) {
            logger.error(e);
        }
        try (Socket clientSocket = new Socket(serverInet.getAddress(), serverInet.getPort())) {
            logger.info("Blocking client is running ...");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String echoString = "你好, NIO Socket Server! [" + clientSocket.getPort() + "]";
            out.println(echoString);
            String inputLine = in.readLine();
            logger.info("{} From Server@{}:{}", inputLine, clientSocket.getInetAddress(), clientSocket.getPort());
        } catch (Exception ex) {
            logger.error(ex);
        }
        clientFinish.countDown();
    }
}