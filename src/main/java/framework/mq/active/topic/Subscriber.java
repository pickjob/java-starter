package framework.mq.active.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;

public class Subscriber {
    private static Logger logger = LogManager.getLogger(Subscriber.class);

    public static void main(String []args) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = new ActiveMQTopic("topic");

            MessageConsumer consumer = session.createConsumer(dest, "id = 7");
            long start = System.currentTimeMillis();
            long count = 0;
            logger.info("Waiting for messages...");
            while (true) {
                Message msg = consumer.receive();
                if (msg instanceof TextMessage) {
                    String body = ((TextMessage) msg).getText();
                    if ("SHUTDOWN".equals(body)) {
                        long diff = System.currentTimeMillis() - start;
                        logger.info("Received {} in {} seconds", count, (1.0 * diff / 1000.0));
                        break;
                    } else {
                        if (msg.getIntProperty("id") == 0) {
                            start = System.currentTimeMillis();
                        }
                        count++;
                    }
                    logger.info("receiving id: {}, message: {}", msg.getIntProperty("id"), ((TextMessage) msg).getText());
                } else {
                    logger.info("Unexpected message type: {}", msg.getClass());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}