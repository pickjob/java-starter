package app.snowflow;

import java.io.Serializable;

/**
 * ID:
 *    | 字段 |   版本    | 生成方式 | 秒级时间 | 序列号 | 机器ID |
 *    | 位数 |   63-62  | 60-61   | 30-59  | 10-29 |  0-9  |
 *
 * @author: pickjob@126.com
 * @date: 2024-09-25
 */
public class Id implements Serializable {
    private long machine;
    public static final long MACHINE_LENGTH = 10;
    private long seq;
    public static final long SEQ_LENGTH = 20;
    private long time;
    public static final long TIME_LENGTH = 30;
    private long method;
    public static final long METHOD_LENGTH = 2;
    private long version;
    public static final long VERSION_LENGTH = 2;

    public static Id from(long longId) {
        Id id = new Id();
        id.setMachine(longId & generateMask(MACHINE_LENGTH));
        id.setSeq((longId >>> MACHINE_LENGTH) & generateMask(SEQ_LENGTH));
        id.setTime((longId >>> (MACHINE_LENGTH + SEQ_LENGTH)) & generateMask(TIME_LENGTH));
        id.setMethod((longId >>> (MACHINE_LENGTH + SEQ_LENGTH + TIME_LENGTH)) & generateMask(METHOD_LENGTH));
        id.setVersion((longId >>> (MACHINE_LENGTH + SEQ_LENGTH + TIME_LENGTH + METHOD_LENGTH)) & generateMask(VERSION_LENGTH));
        return id;
    }

    private static long generateMask(long number) {
        return ~(-1L << number);
    }

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

    public long getMethod() {
        return method;
    }

    public void setMethod(long method) {
        this.method = method;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Id{" +
                "machine=" + machine +
                ", seq=" + seq +
                ", time=" + time +
                ", method=" + method +
                ", version=" + version +
                '}';
    }
    private static final long serialVersionUID = -1l;
}