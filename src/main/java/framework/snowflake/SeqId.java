package framework.snowflake;

import java.io.Serializable;

/**
 *
 *    | 字段 |   版本    | 生成方式 | 秒级时间 | 序列号 | 机器ID |
 *    | 位数 |   63-62  | 60-61   | 30-59  | 10-29 |  0-9  |
 *
 */
public class SeqId implements Serializable {
    private static final long serialVersionUID = -1l;
    // 机器ID   10bit
    private long machine;
    // 序列号    20bit
    private long seq;
    // 时间秒偏移 30bit
    private long time;
    // 获取方式   2bit  0-嵌入发布模式 1-中心服务器发布模式 2,3-保留未用
    private long genMethod;
    // 版本      2bit
    private long version;

    public long getMachine() {
        return machine;
    }

    public void setMachine(long machine) {
        this.machine = machine;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getGenMethod() {
        return genMethod;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long generate() {
        long ret = 0;
        ret |= this.getMachine();
        ret |= this.getSeq() << 10;
        ret |= this.getTime() << (10 + 20);
        ret |= this.getGenMethod() << (10 + 20 + 30);
        ret |= this.getVersion() << (10 + 20 + 30 + 2);
        return ret;
    }

    @Override
    public String toString() {
        return "SeqId{" +
                "machine=" + machine +
                ", seq=" + seq +
                ", time=" + time +
                ", genMethod=" + genMethod +
                ", version=" + version +
                '}';
    }
}
