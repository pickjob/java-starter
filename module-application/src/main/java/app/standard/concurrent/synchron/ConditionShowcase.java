package app.standard.concurrent.synchron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition: 用于更细粒度控制线程, 必须在lock.lock 和 lock.unlock间调用
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class ConditionShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>();
        for (int i = 0; i < 10; i++) {
            final int idx = i;
            new Thread(() -> {
                Random random = new Random(System.currentTimeMillis());
                try {
                    logger.info("{} - 放置开始", idx);
                    boundedBuffer.put(Integer.valueOf(idx));
                    logger.info("{} - 放置结束", idx);
                    Thread.sleep(1000 * random.nextInt(10));
                    boundedBuffer.take();
                } catch (Exception e) {
                    logger.error(e);
                }
            }).start();
        }
    }
}

class BoundedBuffer<E> {
    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[5];
    int putptr, takeptr, count;

    public void put(E x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            E x = (E) items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            count--;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}