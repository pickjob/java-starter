package snowflake.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @date 2022-12-16
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
