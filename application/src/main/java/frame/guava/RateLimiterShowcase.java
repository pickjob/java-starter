package frame.guava;

import com.google.common.util.concurrent.RateLimiter;
import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pickjob@126.com
 * @time 2019-06-14
 */
public class RateLimiterShowcase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(RateLimiterShowcase.class);

    @Override
    public void showSomething() {
        logger.info("Guava框架RateLimiter展示");
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++ ) {
            executor.execute(() -> {
                rateLimiter.acquire();
                String threadName = Thread.currentThread().getName();
                logger.info("Thread {} start", threadName);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
                logger.info("Thread {} end", threadName);
            });
        }
        executor.shutdown();
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
