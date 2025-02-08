package app.snowflow.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子操作实现序列生成
 *
 * @author: pickjob@126.com
 * @date: 2024-09-25
 */
public class AtomicIdSupplier extends AbstractIdSupplier {
    private static final Logger logger = LogManager.getLogger();
    private AtomicLong seqAtomic = new AtomicLong(0);
    
    public AtomicIdSupplier(long machineId, long method, long version) {
        super(machineId, method, version);
    }

    @Override
    void calcSeq() {
        if (time == lastTime) {
            seq = seqAtomic.incrementAndGet();
        } else {
            lastTime = time;
            seqAtomic = new AtomicLong(0);
            seq = 0;
        }
    }
}