package app.standard.concurrent.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureTask:
 *      延迟获得结果任务
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class FuterTaskShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<?> task = new FutureTask<>(() -> {
            logger.info("---任务进行中---");
            try {
                Thread.sleep(1000 * 15);
            } catch (Exception e) {
                logger.error(e);
            }
        }, Void.class);
        executor.execute(task);
        try {
            task.get();
            logger.info("---任务结束---");
            executor.shutdown();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}