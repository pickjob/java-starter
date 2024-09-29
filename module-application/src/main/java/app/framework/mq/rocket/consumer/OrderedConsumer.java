//package app.framework.mq.rocket.consumer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.MessageExt;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class OrderedConsumer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(OrderedConsumer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_name");
//            consumer.setNamesrvAddr("wsl2:9876");
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//            consumer.subscribe("TopicTest", "TagA || TagC || TagD");
//            consumer.registerMessageListener(new MessageListenerOrderly() {
//                AtomicLong consumeTimes = new AtomicLong(0);
//
//                @Override
//                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
//                    context.setAutoCommit(false);
//                    logger.info(" {} Receive New Messages: {}", Thread.currentThread().getName(), msgs);
//                    this.consumeTimes.incrementAndGet();
//                    if ((this.consumeTimes.get() % 2) == 0) {
//                        return ConsumeOrderlyStatus.SUCCESS;
//                    } else if ((this.consumeTimes.get() % 3) == 0) {
//                        return ConsumeOrderlyStatus.ROLLBACK;
//                    } else if ((this.consumeTimes.get() % 4) == 0) {
//                        return ConsumeOrderlyStatus.COMMIT;
//                    } else if ((this.consumeTimes.get() % 5) == 0) {
//                        context.setSuspendCurrentQueueTimeMillis(3000);
//                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
//                    }
//                    return ConsumeOrderlyStatus.SUCCESS;
//
//                }
//            });
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
