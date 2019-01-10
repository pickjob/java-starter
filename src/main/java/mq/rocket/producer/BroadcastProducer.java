package mq.rocket.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class BroadcastProducer {
    private static final Logger logger = LogManager.getLogger(BroadcastProducer.class);

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group_name");
        producer.setNamesrvAddr("rocketmq:9876");
        producer.start();
        for (int i = 0; i < 100; i++){
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            logger.info("{}", sendResult);
        }
        producer.shutdown();
    }
}
