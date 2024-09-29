package app.standard.concurrent.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPoolExecutor: 定时任务线程池
 *      抛错后不再执行, 也没有异常输出
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class ScheduledThreadPoolExecutorShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            logger.info("任务1");
        }, 0, 3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
            logger.info("任务2");
            // 抛错后不再执行
            throw new RuntimeException("一个错误");
        }, 10, 5, TimeUnit.SECONDS);
        Thread.sleep(20000);
        executorService.shutdown();
    }
}