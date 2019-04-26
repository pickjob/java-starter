package basic.coucurrent;

import common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author pickjob@126.com
 * @time 2019-04-26
 */
public class TimerShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(TimerShowCase.class);

    @Override
    public void saySomething() {
        logger.info("jdk老版Timer对异常的处理, 抛异常会失败, 不再执行任何任务");
    }

    @Override
    public void showSomething() {
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
