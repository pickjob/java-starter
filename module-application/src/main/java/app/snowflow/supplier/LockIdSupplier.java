package app.snowflow.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁实现序列生成
 *
 * @author: pickjob@126.com
 * @date: 2024-09-25
 */
public class LockIdSupplier extends AbstractIdSupplier {
    private static final Logger logger = LogManager.getLogger();
    private Lock lock = new ReentrantLock();

    public LockIdSupplier(long machineId, long method, long version) {
        super(machineId, method, version);
    }

    @Override
    void calcSeq() {
        lock.lock();
        try {
            if (time == lastTime) {
                seq++;
            } else {
                lastTime = time;
                seq = 0;
            }
        } finally {
            lock.unlock();
        }
    }
}