package app.standard.concurrent.synchron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier: 用于控制多个线程同时执行同步器
 *      await(): 等待所有线程到达指定位置并等待
 *      reset(): 重置同步器
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class CyclicBarrierShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        int parties = 10;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(parties);
        for (int i = 0; i < parties; i++) {
            new Thread(() -> {
                try {
                    int sleepMilliseconds = (int) (Math.random() * 10 * 1000); // 随机生成10秒以内毫秒数
                    logger.info("Thread: {}, sleepMilliseconds: {}", Thread.currentThread(), sleepMilliseconds);
                    Thread.sleep(sleepMilliseconds);
                    cyclicBarrier.await();
                    logger.info("Thread: {} execute", Thread.currentThread().getName());
                } catch (Exception e) {
                    logger.error(e);
                }
            }).start();
        }
        try {
            Thread.sleep(20 * 1000);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}