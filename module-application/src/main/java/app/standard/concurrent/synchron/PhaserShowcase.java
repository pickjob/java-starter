package app.standard.concurrent.synchron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Phaser;

/**
 * Phaser: 一定程度上替代CountDown、CyclicBarrier, 更强大的同步器
 *      arrive(): 到达指定位置
 *      arriveAndAwaitAdvance(): 到达指定位置并等待
 *
 *      awaitAdvance(int): 等待条件
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class PhaserShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Phaser phaser = new Phaser(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                int sleepMilliseconds = (int) (Math.random() * 10 * 1000); // 随机生成10秒以内毫秒数
                logger.info("Thread: {}, sleepMilliseconds: {}", Thread.currentThread(), sleepMilliseconds);
                try {
                    Thread.sleep(sleepMilliseconds);
                    phaser.arriveAndAwaitAdvance(); // arrive phaser
                    logger.info("Thread: {} 结束", Thread.currentThread(), sleepMilliseconds);
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }).start();
        }
        phaser.awaitAdvance(0);
        logger.info("PhaserShowCase结束");
    }
}