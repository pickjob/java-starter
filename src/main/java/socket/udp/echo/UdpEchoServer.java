package socket.udp.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CommonKey;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 * 简单UDP服务端
 */
public class UdpEchoServer {
    private static Logger logger = LogManager.getLogger();

    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(CommonKey.PORT);
            logger.info("Udp server is running ...");
            while (true) {
                byte[] bytes = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);
                serverSocket.receive(receivePacket);
                String receiveStr = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");
                logger.info(receiveStr);
                String sendString = "你好,客户端. @" + new Date().toString() + "，已经收到你的信息" + receiveStr;
                byte[] sendBuf = sendString.getBytes("utf-8");
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}
