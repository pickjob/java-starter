package basic.coucurrent;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Phaser;

/**
 * @author pickjob@126.com
 * @time 2019-04-28
 */
public class PhaserShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(PhaserShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Phaser一定程度上替代CountDown、CyclicBarrier, 更强大的同步器");
    }

    @Override
    public void showSomething() {
        Phaser phaser = new Phaser(3) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                logger.info("PhaserShowCase is calling onAdvance ");
                return super.onAdvance(phase, registeredParties);
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                int sleepMilliseconds = (int) (Math.random() * 10 * 1000); // 随机生成10秒以内毫秒数
                logger.info("Thread: {}, sleepMilliseconds: {}", Thread.currentThread(), sleepMilliseconds);
                try {
                    Thread.sleep(sleepMilliseconds);
                    phaser.arriveAndAwaitAdvance(); // arrive phaser
                    logger.info("Thread: {} 结束", Thread.currentThread(), sleepMilliseconds);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }).start();
        }
        phaser.awaitAdvance(0);
        logger.info("PhaserShowCase结束");
    }
}
