//package app.framework.mq.rocket.producer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.MessageQueueSelector;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageQueue;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//
//import java.util.List;
//
//public class OrderedProducer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(OrderedProducer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            // instantiate with a producer group name
//            DefaultMQProducer producer = new DefaultMQProducer("group_name");
//            producer.setNamesrvAddr("wsl2:9876");
//            // launch the instance
//            producer.start();
//            String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
//            for (int i = 0; i < 100; i++) {
//                int orderId = i % 10;
//                // create a message instance, specifying topic, tag and message body
//                Message msg = new Message("TopicTest", tags[i % tags.length], "KEY" + i,
//                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
//                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
//                    @Override
//                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                        Integer id = (Integer) arg;
//                        int index = id % mqs.size();
//                        return mqs.get(index);
//                    }
//                }, orderId);
//
//                logger.info("{}", sendResult);
//            }
//            //server shutdown
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
