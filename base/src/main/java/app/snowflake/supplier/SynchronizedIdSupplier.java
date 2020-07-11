package app.snowflake.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2020-07-11
 */
public class SynchronizedIdSupplier extends AbstractIdSupplier {
    private static final Logger logger = LogManager.getLogger(SynchronizedIdSupplier.class);

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
