package app.framework.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RateLimiter: 限流器
 *      构建:
 *          RateLimiter.create(double permitsPerSecond): 1s允许流量
 *      限流;
 *          RateLimiter.acquire()
 *
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class RateLimiterShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
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
}