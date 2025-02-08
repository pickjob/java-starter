package app.standard.concurrent.synchron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch: 用于控制一个线程等待置零执行同步器
 *      countDown(): 减一, 置零后所有等待的线程执行
 *      await(): 等待执行
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class CountDownLatchShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                logger.info("{} countDown", Thread.currentThread().getName());
                countDownLatch.countDown();
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                logger.error("{}中断异常", Thread.currentThread().getName());
                logger.error(e);
            }
        });
        t.start();
        try {
            countDownLatch.await();
            logger.info("{}.interrupt()", t.getName());
            t.interrupt();
            logger.info("{}.isInterrupted(): {}", t.getName(), t.isInterrupted());
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}