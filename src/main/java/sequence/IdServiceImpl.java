package sequence;

public class IdServiceImpl implements IdService {
    private long machineId;
    private long seq;
    private long genMethod;
    private long verion;
    private long baseTime = 1546272000000; // 2019-1-1
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
        return id.generate();
    }

    private synchronized void populateTime(SeqId seqId) {
        long time = (System.currentTimeMillis() - baseTime) / 1000;
        seqId.setTime(time);
        if (time == lastTime) {
            seq++;
        } else {
            seq = 0;
        }
        seqId.setSeq(seq);
    }
}
