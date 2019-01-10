package basic.coucurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorShowCase {
    private static final Logger logger = LogManager.getLogger(ScheduledThreadPoolExecutorShowCase.class);

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            logger.info("hello1");
            // 抛错后不再执行
            throw new RuntimeException("一个错误");
        }, 0, 5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
            logger.info("hello2");
            // 抛错后不再执行
            throw new RuntimeException("一个错误");
        }, 3, 5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
            logger.info("hello3");
            // 抛错后不再执行
//            throw new RuntimeException("一个错误");
        }, 5, 5, TimeUnit.SECONDS);
        while (!executorService.isShutdown()) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        executorService.shutdown();
    }
}
