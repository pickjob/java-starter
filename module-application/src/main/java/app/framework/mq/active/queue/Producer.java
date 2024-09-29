//package app.framework.mq.active.queue;
//
//import app.common.IShowCase;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.jms.*;
//
//public class Producer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(Producer.class);
//
//    @Override
//    public void showSomething() {
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://wsl2:61616");
//        Connection connection = null;
//        try {
//            connection = connectionFactory.createConnection();
//            connection.start();
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Destination destination = session.createQueue("queue");
//            MessageProducer producer = session.createProducer(destination);
//
//            for (int i = 0; i < 100; i++) {
//                TextMessage message = session.createTextMessage("Message #" + i);
//                logger.info("Sending message: {}", message.getText());
//                producer.send(message);
//            }
//
//            producer.close();
//            session.close();
//
//        } catch (Exception e) {
//            logger.error(e);
//        }
//        finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                    logger.error(e);
//                }
//            }
//        }
//    }
//
////    @Override
////    public boolean isShow() {
////        return true;
////    }
////
////    @Override
////    public int order() {
////        return 1;
////    }
//}