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
//public class PushConsumer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(PushConsumer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            // instantiate with specified consumer group name
//            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_name");
//            // specify name server addresses
//            consumer.setNamesrvAddr("wsl2:9876");
//            // subscribe one more more topics to consume
//            consumer.subscribe("TopicTest", "*");
//            // register callback to execute on arrival of messages fetched from brokers.
//            consumer.registerMessageListener(new MessageListenerConcurrently() {
//                @Override
//                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                    logger.info("{} Receive New Messages: {}", Thread.currentThread().getName(), msgs);
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//            });
//            // launch the consumer instance
//            consumer.start();
//            logger.info("Consumer Started.");
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
