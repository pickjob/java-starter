package showcase.smpp.util;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public enum CharsetEnum {
    DEFAULT("GSM7", (byte)0),
    Latin1("ISO-8859-1", (byte)3),
    UCS2("UnicodeBigUnmarked", (byte)8);

    public byte[] encodingString(String str) {
        if (StringUtils.isBlank(str)) str = "";
        byte[] s = null;
        try {
            s = str.getBytes(charset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return s;
    }

    public String decodingString(ByteBuf buf, int length) {
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        String str = null;
        try {
            str = new String(bytes, charset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    public CharsetEnum chooseCharsetEnum(byte dcs) {
        CharsetEnum cs = holder.get(dcs);
        if (cs == null) return Latin1;
        return cs;
    }

    public byte getDataCodingSchema() {
        return dataCodingSchema;
    }

    public void setDataCodingSchema(byte dataCodingSchema) {
        this.dataCodingSchema = dataCodingSchema;
    }

    private CharsetEnum(String charset, byte code) {
        this.charset = charset;
        this.dataCodingSchema = code;
    }

    private String charset;
    private byte dataCodingSchema;
    private static final Map<Byte, CharsetEnum> holder = new HashMap<>();
    static {
        holder.put((byte)0, DEFAULT);
        holder.put((byte)3, Latin1);
        holder.put((byte)8, UCS2);
    }
    private static final Logger logger = LogManager.getLogger(CharsetEnum.class);
}
