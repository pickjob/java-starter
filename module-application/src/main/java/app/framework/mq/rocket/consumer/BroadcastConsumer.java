//package app.framework.mq.rocket.consumer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//
//import java.util.List;
//
//public class BroadcastConsumer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(BroadcastConsumer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_name");
//            consumer.setNamesrvAddr("wsl2:9876");
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//            consumer.setMessageModel(MessageModel.BROADCASTING);
//            consumer.subscribe("TopicTest", "TagA || TagC || TagD");
//            consumer.registerMessageListener(new MessageListenerConcurrently() {
//                @Override
//                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
//                                                                ConsumeConcurrentlyContext context) {
//                    logger.info("{} Receive New Messages: {}", Thread.currentThread().getName(), msgs);
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//            });
//            consumer.start();
//            logger.info("Broadcast Consumer Started.%n");
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
