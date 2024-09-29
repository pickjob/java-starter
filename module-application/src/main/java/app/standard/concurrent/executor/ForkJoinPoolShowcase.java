package app.standard.concurrent.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool: 拆分任务线程池
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class ForkJoinPoolShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        FibonacciRecursiveTask fibonacci = new FibonacciRecursiveTask(10);
        pool.submit(fibonacci);
        logger.info("RecursiveTask返回结果任务: {}", fibonacci.join());
        pool.shutdown();
    }
}

class FibonacciRecursiveTask extends RecursiveTask<Integer> {
    final int n;

    FibonacciRecursiveTask(int n) {
        this.n = n;
    }

    @Override
    public Integer compute() {
        if (n <= 1) {
            return n;
        }
        FibonacciRecursiveTask f1 = new FibonacciRecursiveTask(n - 1);
        FibonacciRecursiveTask f2 = new FibonacciRecursiveTask(n - 2);
        f1.fork();
        f2.fork();
        return f2.join() + f1.join();
    }
}