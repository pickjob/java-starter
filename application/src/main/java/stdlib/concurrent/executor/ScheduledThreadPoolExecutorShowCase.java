package stdlib.concurrent.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author pickjob@126.com
 * @time 2022-12-13
 */
public class ScheduledThreadPoolExecutorShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("jdk默认定时器框架(ScheduledThreadPoolExecutor)抛异常任务不再执行定时任务, 其他任务正常执行");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            logger.info("这是ScheduledExecutorService任务1");
        }, 0, 3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
            logger.info("这是ScheduledExecutorService任务2");
            // 抛错后不再执行
            throw new RuntimeException("一个错误");
        }, 10, 5, TimeUnit.SECONDS);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        executorService.shutdown();
    }
}
