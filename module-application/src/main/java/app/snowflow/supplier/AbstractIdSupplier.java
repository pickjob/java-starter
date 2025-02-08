package app.snowflow.supplier;

import app.snowflow.Id;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * ID生成通用逻辑
 *
 *  @author: pickjob@126.com
 *  @date: 2024-09-25
 */
public abstract class AbstractIdSupplier implements Supplier<Long> {
    private static final Logger logger = LogManager.getLogger();
    private long machineId;
    protected volatile long seq;
    protected volatile long time;
    private long baseTime = 1546272000000L; // 2019-1-1
    private Instant baseInstant = Instant.ofEpochMilli(baseTime);
    protected volatile long lastTime = 0;
    private long method;
    private long version;

    public AbstractIdSupplier(long machineId, long method, long version) {
        this.machineId = machineId;
        this.method = method;
        this.version = version;
    }

    @Override
    public Long get() {
        Instant now = Instant.now();
        Duration duration = Duration.between(baseInstant, now);
        time = duration.toSeconds();
        calcSeq();
        return pack();
    }

    abstract void calcSeq();

    private long pack() {
        long ret = 0;
        ret |= this.machineId;
        ret |= this.seq << Id.MACHINE_LENGTH;
        ret |= this.time << (Id.MACHINE_LENGTH + Id.SEQ_LENGTH);
        ret |= this.method << (Id.MACHINE_LENGTH + Id.SEQ_LENGTH + Id.TIME_LENGTH);
        ret |= this.version << (Id.MACHINE_LENGTH + Id.SEQ_LENGTH + Id.TIME_LENGTH + Id.METHOD_LENGTH);
        return ret;
    }
}