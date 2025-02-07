package showcase.smpp.util;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public enum EsmClassEnum {
    DeliveryReceipt((byte)0x4),
    ConcatenatedMessages((byte)0x40);


    public byte getEsmClass() {
        return esmClass;
    }

    public void setEsmClass(byte esmClass) {
        this.esmClass = esmClass;
    }

    private EsmClassEnum(byte esmClass) {
        this.esmClass = esmClass;
    }

    private byte esmClass;
}
