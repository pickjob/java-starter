package basic.coucurrent;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;

/**
 * @author pickjob@126.com
 * @time 2019-04-26
 */
public class SemaphoreShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(SemaphoreShowCase.class);

    @Override
    public void showSomething() {
        logger.info("并发工具Semaphore, 用于控制同时执行数量");
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                try{
                    semaphore.acquire();
                    logger.info("Semaphore正在执行-{}。。。 ", Thread.currentThread().getName());
                    Thread.sleep(10000);
                    semaphore.release();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
            logger.info("Semaphore剩余-{}", semaphore.availablePermits());
            semaphore.acquire();
            logger.info("Semaphore结束-{}", Thread.currentThread().getName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
