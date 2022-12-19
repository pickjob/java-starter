package stdlib.socket.bio.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * Blocking Socket示例
 *
 * @author: pickjob@126.com
 * @date: 2022-12-19
 */
public class BioEchoShowCase {
    private static Logger logger = LogManager.getLogger();
    private static final int SERVER_PORT = 8080;

    private static final InetSocketAddress serverAddr = new InetSocketAddress("localhost", SERVER_PORT);
    private static final CountDownLatch serverReady = new CountDownLatch(1);

    public static void main(String[] args) {
        // 启动服务端
        new Thread(BioEchoShowCase::serverRunnable).start();
        // 启动客户端
        new Thread(BioEchoShowCase::clientRunnable).start();
    }

    private static void serverRunnable() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {
            logger.info("Blocking Socket server is running ...");
            serverReady.countDown();
            try (Socket clientSocket = serverSocket.accept();) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine =  in.readLine();
                logger.info("{} From Client@{}:{}", inputLine, clientSocket.getInetAddress(), clientSocket.getPort());
                String echoString = "你好, Blocking Socket Client!";
                out.println(echoString);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
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
            String echoString = "你好, Blocking Socket Server!";
            out.println(echoString);
            String inputLine =  in.readLine();
            logger.info("{} From Server@{}:{}", inputLine, clientSocket.getInetAddress(), clientSocket.getPort());
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}
