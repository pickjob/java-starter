//package app.framework.mq.rocket.producer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//
//public class SyncProducer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(SyncProducer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            // instantiate with a producer group name
//            DefaultMQProducer producer = new DefaultMQProducer("group_name");
//            // specify name server addresses
//            producer.setNamesrvAddr("localhost:9876");
//            // launch the instance
//            producer.start();
//            for (int i = 0; i < 100; i++) {
//                // create a message instance, specifying topic, tag and message body
//                Message msg = new Message("TopicTest" /* Topic */,
//                        "TagA" /* Tag */,
//                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
//                );
//                // call send message to deliver message to one of brokers
//                SendResult sendResult = producer.send(msg);
//                logger.info("{}", sendResult);
//            }
//            // shut down once the producer instance is not longer in use
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
