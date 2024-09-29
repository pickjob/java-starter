//package app.framework.mq.rocket.producer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.common.message.Message;
//
//public class ScheduledMessageProducer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(ScheduledMessageProducer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            DefaultMQProducer producer = new DefaultMQProducer("group_name");
//            producer.setNamesrvAddr("wsl2:9876");
//            producer.start();
//            for (int i = 0; i < 100; i++) {
//                Message message = new Message("TopicTest", ("Hello scheduled message " + i).getBytes());
//                // This message will be delivered to consumer 10 seconds later.
//                message.setDelayTimeLevel(3);
//                // Send the message
//                producer.send(message);
//            }
//            producer.shutdown();
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
////        return 0;
////    }
//}
