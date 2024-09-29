//package app.framework.mq.active.durable;
//
//import app.common.IShowCase;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.jms.*;
//import java.util.concurrent.CountDownLatch;
//
//public class Subscriber implements MessageListener, IShowCase {
//    private static final Logger logger = LogManager.getLogger(Subscriber.class);
//    private static final CountDownLatch countDownLatch = new CountDownLatch(1);
//    private static final String clientId = "112";
//
//    @Override
//    public void showSomething() {
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://wsl2:61616");
//        Connection connection = null;
//        try {
//            connection = connectionFactory.createConnection();
//            connection.setClientID(clientId);
//            connection.start();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Topic destination = session.createTopic("topic");
//
//            MessageConsumer consumer = session.createDurableSubscriber(destination, clientId) ;
//            consumer.setMessageListener(new Subscriber());
//
//            countDownLatch.await();
//            consumer.close();
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
//    @Override
//    public void onMessage(Message message) {
//        try {
//            if (message instanceof TextMessage) {
//                String text = ((TextMessage) message).getText();
//                if ("SHUTDOWN".equalsIgnoreCase(text)) {
//                    logger.info("Received SHUTDOWN message!");
//                    countDownLatch.countDown();
//                }
//                else {
//                    logger.info("Received message: {}", text);
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e);
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