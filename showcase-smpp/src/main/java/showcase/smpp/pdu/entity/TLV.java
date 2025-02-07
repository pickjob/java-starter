package showcase.smpp.pdu.entity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class TLV {
    private Short tag;
    private Short length;
    private List<Byte> values;

    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public List<Byte> getValues() {
        return values;
    }

    public void setValues(List<Byte> values) {
        this.values = values;
    }

    public static List<TLV> readTlvs(ByteBuf buf) {
        List tlvs = null;
        if (buf.readableBytes() > 0) tlvs = new ArrayList<>();
        while (buf.readableBytes() > 0) {
            TLV tlv = new TLV();
            tlv.setTag(buf.readShort());
            tlv.setLength(buf.readShort());
            byte[] bytes = new byte[tlv.getLength()];
            buf.readBytes(bytes);
            List<Byte> list = new ArrayList<>();
            for (int i = 0;i < bytes.length; i++) {
                list.add(bytes[i]);
            }
            tlv.setValues(list);
            tlvs.add(tlv);
        }
        return tlvs;
    }
}
