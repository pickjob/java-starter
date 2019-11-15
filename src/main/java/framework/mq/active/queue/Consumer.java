package framework.mq.active.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;

public class Consumer {
    private static Logger logger = LogManager.getLogger(Consumer.class);

    public static void main(String[] args){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue("queue");
            MessageConsumer consumer = session.createConsumer(destination);

            int i = 0;
            while (true) {
                Message message = consumer.receive();
                if (message != null) {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        logger.info("Got #{}, message: {}", i, text, message.getJMSDeliveryMode());
                    }
                } else {
                    break;
                }
            }

            consumer.close();
            session.close();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}