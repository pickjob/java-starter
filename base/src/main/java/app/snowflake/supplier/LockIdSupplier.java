package app.snowflake.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pickjob@126.com
 * @time 2020-07-11
 */
public class LockIdSupplier extends AbstractIdSupplier {
    private static final Logger logger = LogManager.getLogger(LockIdSupplier.class);
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
