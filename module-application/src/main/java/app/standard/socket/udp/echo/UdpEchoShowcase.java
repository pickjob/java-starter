package app.standard.socket.udp.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * UDP示例
 *
 * @author: pickjob@126.com
 * @date: 2022-12-19
 */
public class UdpEchoShowcase {
    private static final Logger logger = LogManager.getLogger();
    private static final int SERVER_PORT = 8080;

    private static final InetSocketAddress serverInet = new InetSocketAddress("localhost", SERVER_PORT);
    private static final CountDownLatch serverReady = new CountDownLatch(1);

    public static void main(String[] args) {
        // 启动服务端
        new Thread(UdpEchoShowcase::serverRunnable).start();
        // 启动客户端
        new Thread(UdpEchoShowcase::clientRunnable).start();
    }

    private static void serverRunnable() {
        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);) {
            logger.info("Udp server is running ...");
            serverReady.countDown();
            byte[] bytes = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);
            serverSocket.receive(receivePacket);
            String receiveStr = new String(receivePacket.getData(), 0, receivePacket.getLength());
            logger.info("{} From Client@{}:{}", receiveStr, receivePacket.getAddress(), receivePacket.getPort());
            String echoString = "你好, UDP Client!";
            byte[] sendBuf = echoString.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, receivePacket.getAddress(), receivePacket.getPort());
            serverSocket.send(sendPacket);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
        } catch (InterruptedException e) {
            logger.error(e);
        }
        try (DatagramSocket clientSocket = new DatagramSocket();) {
            logger.info("Udp client is running ...");
            String echoString = "你好, UDP Server!";
            byte[] sendBuf = echoString.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, serverInet.getAddress(), serverInet.getPort());
            clientSocket.send(sendPacket);
            byte[] bytes = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);
            clientSocket.receive(receivePacket);
            String receiveStr = new String(receivePacket.getData(), 0, receivePacket.getLength());
            logger.info("{} From Server@{}:{}", receiveStr, receivePacket.getAddress(), receivePacket.getPort());
        } catch (Exception e) {
            logger.error(e);
        }
    }
}