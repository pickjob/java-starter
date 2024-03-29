package stdlib.jcf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author pickjob@126.com
 * @date 2022-12-16
 */
public class SynchronousQueueShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("SynchronousQueueShowCase可用于实时处理消息，一直等待直至消费未知");
        BlockingQueue<Integer> blockingQueue = new SynchronousQueue<>(true);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            final Integer idx = Integer.valueOf(i);
            new Thread(() -> {
                logger.info("元素{}-生产开始", idx);
                try {
                    blockingQueue.put(Integer.valueOf(idx));
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
                logger.info("元素{}-生产结束", idx);
            }).start();
            new Thread(() -> {
                try {
                    Thread.sleep(idx * random.nextInt(10) * 1000);
                    logger.info("元素{}-消费开始", idx);
                    Integer e = blockingQueue.take();
                    logger.info("{}", e);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
                logger.info("元素{}-消费开始", idx);

            }).start();
        }
    }
}
