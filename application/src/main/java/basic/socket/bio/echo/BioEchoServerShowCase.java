package basic.socket.bio.echo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class BioEchoServerShowCase {
    private static Logger logger = LogManager.getLogger(BioEchoServerShowCase.class);

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(8080);
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
                } catch (Exception e) {
                    logger.info("Catch exception on port {} ", 8080);
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
