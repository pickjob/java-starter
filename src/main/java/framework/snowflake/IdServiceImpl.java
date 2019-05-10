package framework.snowflake;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IdServiceImpl implements IdService {
    private static final Logger logger = LogManager.getLogger(IdServiceImpl.class);

    private long machineId;
    private long seq;
    private long genMethod;
    private long verion;
    private long baseTime = 1546272000000l; // 2019-1-1
    private long lastTime = 0;

    public IdServiceImpl() {
        this.machineId = 0;
        this.seq = 0;
        this.genMethod = 0;
        this.verion = 0;
    }

    @Override
    public long generateSeqId() {
        SeqId id = new SeqId();
        id.setMachine(machineId);
        id.setGenMethod(genMethod);
        id.setVersion(verion);
        populateTime(id);
        return id.generate();
    }

    private synchronized void populateTime(SeqId seqId) {
        long now = System.currentTimeMillis();
        long time = (now - baseTime) / 1000;
        seqId.setTime(time);
        if (time == lastTime) {
            seq++;
        } else {
            lastTime = time;
            seq = 0;
        }
        seqId.setSeq(seq);
    }
}
