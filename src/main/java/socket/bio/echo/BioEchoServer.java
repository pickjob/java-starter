package socket.bio.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CommonKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * BioEchoServer.java
 * Purpose:
 * 		.简单TCP服务器   
 * @author pickjob@126.com
 */
public class BioEchoServer {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(CommonKey.PORT);
            logger.info("Udp server is running ...");
            Socket clientSocket = null;
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    clientSocket.setKeepAlive(false);
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        logger.info("Got from client:" + inputLine);
                        out.println(inputLine + " from Server!");
                    }
                } catch (IOException e) {
                    logger.info("Catch exception on port " + CommonKey.PORT);
                    logger.info(e.getMessage());
                } finally {
                    if (clientSocket != null) clientSocket.close();
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}
