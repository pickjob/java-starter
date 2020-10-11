package basic.socket.udp.echo;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 * 简单UDP服务端
 */
public class UdpEchoServerShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(UdpEchoServerShowCase.class);

    @Override
    public void showSomething() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(8080);
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
