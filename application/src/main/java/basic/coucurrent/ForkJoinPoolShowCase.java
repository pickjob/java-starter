package basic.coucurrent;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author pickjob@126.com
 * @time 2019-12-14
 */
public class ForkJoinPoolShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ForkJoinPoolShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示ForkJoinPool各种任务示例");
    }

    @Override
    public void showSomething() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        FibonacciRecursiveTask fibonacci = new FibonacciRecursiveTask(10);
        pool.submit(fibonacci);
        try {
            logger.info("RecursiveTask返回结果任务: {}", fibonacci.join());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
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
        if (n <= 1)
            return n;
        FibonacciRecursiveTask f1 = new FibonacciRecursiveTask(n - 1);
        f1.fork();
        FibonacciRecursiveTask f2 = new FibonacciRecursiveTask(n - 2);
        return f2.compute() + f1.join();
    }
}