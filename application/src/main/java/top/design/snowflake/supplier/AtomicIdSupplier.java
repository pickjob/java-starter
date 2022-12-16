package top.design.snowflake.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author pickjob@126.com
 * @date 2022-12-16
 */
public class AtomicIdSupplier extends AbstractIdSupplier {
    private static final Logger logger = LogManager.getLogger(AtomicIdSupplier.class);
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
