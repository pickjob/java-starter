package basic.coucurrent;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * @author pickjob@126.com
 * @time 2019-04-27
 */
public class ThreadInterruptShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(ThreadInterruptShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Thread.interrupt()中断指定线程");
    }

    @Override
    public void showSomething() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                countDownLatch.countDown();
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                logger.error("{}中断异常", Thread.currentThread().getName());
                logger.error(e.getMessage(), e);
            }
        });
        t.start();
        try {
            countDownLatch.await();
            logger.info("{}.interrupt()", t.getName());
            t.interrupt();
            logger.info("{}.isInterrupted(): {}", t.getName(), t.isInterrupted());
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
