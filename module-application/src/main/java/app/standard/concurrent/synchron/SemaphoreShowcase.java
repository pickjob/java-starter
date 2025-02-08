package app.standard.concurrent.synchron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;

/**
 * Semaphore: 用于控制同时执行线程数量同步器
 *      acquire(): 获得信号量, 没有剩余就等待
 *      release(): 释放信号量
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class SemaphoreShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Semaphore, 用于控制同时执行线程数量同步器");
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try{
                    semaphore.acquire();
                    logger.info("Semaphore正在执行-{}。。。 ", Thread.currentThread().getName());
                    Thread.sleep(10000);
                    semaphore.release();
                } catch (Exception e) {
                    logger.error(e);
                }
            }).start();
        }
        while (true) {
            try {
                Thread.sleep(1000);
                if (semaphore.availablePermits() < 3) {
                    logger.info("Semaphore剩余-{}", semaphore.availablePermits());
                } else {
                    logger.info("Semaphore控制结束");
                    break;
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
}