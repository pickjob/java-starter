//package app.framework.mq.rocket.consumer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.common.message.MessageExt;
//
//import java.util.List;
//
//public class ScheduledMessageConsumer implements IShowCase {
//    private static Logger logger = LogManager.getLogger(ScheduledMessageConsumer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ExampleConsumer");
//            consumer.setNamesrvAddr("wsl2:9876");
//            consumer.subscribe("TopicTest", "*");
//            consumer.registerMessageListener(new MessageListenerConcurrently() {
//                @Override
//                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
//                    for (MessageExt message : messages) {
//                        // Print approximate delay time period
//                        logger.info("Receive message[msgId={}] {}", message.getMsgId(), (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
//                    }
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//            });
//            consumer.start();
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
