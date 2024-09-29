//package app.framework.mq.rocket.producer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.producer.LocalTransactionState;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.client.producer.TransactionListener;
//import org.apache.rocketmq.client.producer.TransactionMQProducer;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class TransactionProducer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(TransactionProducer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            TransactionListener transactionListener = new TransactionListenerImpl();
//            ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
//                @Override
//                public Thread newThread(Runnable r) {
//                    Thread thread = new Thread(r);
//                    thread.setName("client-transaction-msg-check-thread");
//                    return thread;
//                }
//            });
//            TransactionMQProducer producer = new TransactionMQProducer("group_name");
//            producer.setNamesrvAddr("wsl2:9876");
//            producer.setExecutorService(executorService);
//            producer.setTransactionListener(transactionListener);
//            producer.start();
//            String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
//            for (int i = 0; i < 10; i++) {
//                Message msg = new Message("TopicTest", tags[i % tags.length], "KEY" + i,
//                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
//                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
//                logger.info("{}", sendResult);
//                Thread.sleep(10);
//            }
//
//            for (int i = 0; i < 100000; i++) {
//                Thread.sleep(1000);
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
//
//    static class TransactionListenerImpl implements TransactionListener {
//        private AtomicInteger transactionIndex = new AtomicInteger(0);
//
//        private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();
//
//        @Override
//        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//            int value = transactionIndex.getAndIncrement();
//            int status = value % 3;
//            localTrans.put(msg.getTransactionId(), status);
//            return LocalTransactionState.UNKNOW;
//        }
//
//        @Override
//        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
//            Integer status = localTrans.get(msg.getTransactionId());
//            if (null != status) {
//                switch (status) {
//                    case 0:
//                        return LocalTransactionState.UNKNOW;
//                    case 1:
//                        return LocalTransactionState.COMMIT_MESSAGE;
//                    case 2:
//                        return LocalTransactionState.ROLLBACK_MESSAGE;
//                }
//            }
//            return LocalTransactionState.COMMIT_MESSAGE;
//        }
//    }
//
//}
//
