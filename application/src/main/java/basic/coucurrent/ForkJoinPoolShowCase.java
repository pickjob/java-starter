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
    public void showSomething() {
        logger.info("展示ForkJoinPool各种任务示例");
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

//    @Override
//    public boolean isShow() {
//        return true;
//    }
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