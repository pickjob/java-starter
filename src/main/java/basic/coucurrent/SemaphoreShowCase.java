package basic.coucurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;

/**
 * 信号量，线程调度
 */
public class SemaphoreShowCase {
    private static final Logger logger = LogManager.getLogger(SemaphoreShowCase.class);

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try{
                    semaphore.acquire();
                    logger.info(semaphore.availablePermits());
                    Thread.sleep(10000);
                    semaphore.release();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }).start();
        }
        try {
            Thread.sleep(2000);
            semaphore.acquire();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("继续执行");
    }
}
