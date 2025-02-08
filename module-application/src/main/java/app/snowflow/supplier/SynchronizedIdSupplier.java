package app.snowflow.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * synchronized实现序列生成
 *
 * @author: pickjob@126.com
 * @date: 2024-09-25
 */
public class SynchronizedIdSupplier extends AbstractIdSupplier {
    private static final Logger logger = LogManager.getLogger();

    public SynchronizedIdSupplier(long machineId, long method, long version) {
        super(machineId, method, version);
    }

    @Override
    synchronized void calcSeq() {
        if (time == lastTime) {
            seq++;
        } else {
            lastTime = time;
            seq = 0;
        }
    }
}