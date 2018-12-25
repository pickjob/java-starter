package smpp.util;

/**
 * @author pickjob@126.com
 * @time 2018-12-12
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
