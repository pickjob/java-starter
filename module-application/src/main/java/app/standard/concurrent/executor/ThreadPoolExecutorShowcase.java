package app.standard.concurrent.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor: 线程池
 *      Core and maximum pool sizes: 核心、最大线程数量
 *      On-demand construction: 线程创建时机 预先/提交任务后
 *      Creating new threads: 线程创建工厂
 *      Keep-alive times: 存活时长
 *      Queuing:
 *          Direct handoffs: 拒绝
 *          Unbounded queues: 无边界
 *          Bounded queues: 有边界
 *      RejectedExecutionHandler: 拒绝策略
 *          CallerRunsPolicy
 *          AbortPolicy
 *          DiscardPolicy
 *          DiscardOldestPolicy
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class ThreadPoolExecutorShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                10,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1));
        // AbortPolicy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // DiscardOldestPolicy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        try {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}