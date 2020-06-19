package basic.coucurrent;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

/**
 * @author pickjob@126.com
 * @time 2019-12-13
 */
public class ThreadPoolExecutorShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ThreadPoolExecutorShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示ThreadPoolExecutor基本用法");
    }

    @Override
    public void showSomething() {
        int coreSize = 1;
        int maxSize = 1;
        long keepAliveTime = 30;
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, TimeUnit.SECONDS, blockingQueue);
        // AbortPolicy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            try {
                executor.execute(() -> {
                    try {
                        logger.info("AbortPolicy task");
                        Thread.sleep(1000 * 5);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                });
            } catch (Exception e) {
                logger.error("AbortPolicy Error: " + e.getMessage(), e);
            }
        }
        try {
            Thread.sleep(1000 * 10);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // DiscardOldestPolicy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 5; i++) {
            try {
                executor.execute(() -> {
                    try {
                        logger.info("DiscardOldestPolicy task");
                        Thread.sleep(1000 * 5);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                });
            } catch (Exception e) {
                logger.error("DiscardOldestPolicy Error: " + e.getMessage(), e);
            }
        }
        executor.shutdown();
    }
}
