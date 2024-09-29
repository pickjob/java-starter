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
//public class BroadcastProducer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(BroadcastProducer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            DefaultMQProducer producer = new DefaultMQProducer("group_name");
//            producer.setNamesrvAddr("wsl2:9876");
//            producer.start();
//            for (int i = 0; i < 100; i++) {
//                Message msg = new Message("TopicTest",
//                        "TagA",
//                        "OrderID188",
//                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
//                SendResult sendResult = producer.send(msg);
//                logger.info("{}", sendResult);
//            }
//            producer.shutdown();
//        } catch (Exception e) {
////            logger.error(e); a
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
