package mq.active.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;

public class Publisher {
    private static Logger logger = LogManager.getLogger(Publisher.class);

    public static void main(String []args) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = new ActiveMQTopic("topic");
            MessageProducer producer = session.createProducer(dest);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 0; i < 10; i++) {
                TextMessage msg = session.createTextMessage("Hello world");
                msg.setIntProperty("id", i);
                producer.send(msg);
                logger.info("sending id: {}, message: {}", i, msg.getText());
            }

            producer.send(session.createTextMessage("SHUTDOWN"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if( connection != null) {
                try{
                    connection.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }


    }
}