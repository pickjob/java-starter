//package app.framework.mq.rocket.producer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//
//import java.util.concurrent.CountDownLatch;
//
//public class AsyncProducer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(AsyncProducer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            CountDownLatch countDownLatch = new CountDownLatch(100);
//            // Instantiate with a producer group name
//            DefaultMQProducer producer = new DefaultMQProducer("group_name");
//            // Specify name server addresses
//
//            // Launch the instance
//            producer.start();
//            producer.setRetryTimesWhenSendAsyncFailed(0);
//            for (int i = 0; i < 100; i++) {
//                final int index = i;
//                // Create a message instance, specifying topic, tag and message body.
//                Message msg = new Message("ABC",
//                        "TagB",
//                        "OrderID188",
//                        ("Hello world " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
//                producer.send(msg, new SendCallback() {
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//                        logger.info("{}-{} OK", index, sendResult.getMsgId());
//                        countDownLatch.countDown();
//                    }
//
//                    @Override
//                    public void onException(Throwable e) {
//                        logger.error(e);
//                        countDownLatch.countDown();
//                    }
//                });
//            }
//            // Shut down once the producer instance is not longer in use.
//            countDownLatch.await();
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
