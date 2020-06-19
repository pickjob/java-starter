package basic.socket.nio.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;


public class NioEchoServerShowCase {
    private static Logger logger = LogManager.getLogger(NioEchoServerShowCase.class);

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                logger.info("Nio server is running ... ");
                selector.select();
                Iterator<SelectionKey> selectKeyIterator = selector.selectedKeys().iterator();
                while (selectKeyIterator.hasNext()) {
                    SelectionKey key = selectKeyIterator.next();
                    selectKeyIterator.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel sc = (ServerSocketChannel) key.channel();
                        SocketChannel clientSocketChanel = sc.accept();
                        clientSocketChanel.configureBlocking(false);
                        clientSocketChanel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }
                    if (key.isReadable()) {
                        SocketChannel clientSocketChannel = null;
                        try {
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            buffer.clear();
                            clientSocketChannel = (SocketChannel) key.channel();
                            clientSocketChannel.read(buffer);
                            buffer.flip();
                            String receivedMessage = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
                            logger.info(receivedMessage);
                            String sendString = "你好,客户端. @" + new Date().toString() + "，已经收到你的信息" + receivedMessage;
                            buffer = ByteBuffer.wrap(sendString.getBytes("utf-8"));
                            clientSocketChannel.write(buffer);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            if (clientSocketChannel != null) clientSocketChannel.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
