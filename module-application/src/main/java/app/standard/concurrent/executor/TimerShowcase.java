package app.standard.concurrent.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer:
 *      异常后定时器停止
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class TimerShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                logger.info("这是TimerTask1");
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                logger.info("这是TimerTask2, 要抛异常");
                throw new RuntimeException("异常啊");
            }
        };
        timer.scheduleAtFixedRate(task1, 0, 1000);
        timer.scheduleAtFixedRate(task2, 10000, 3000);
    }
}
