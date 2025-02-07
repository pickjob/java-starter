package showcase.smpp.util;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class StringCodeUtil {
    private static final Logger logger = LogManager.getLogger();

    public static byte[] encodingCString(String str) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(str)) str = "";
        byte[] s = str.getBytes("ASCII");
        byte[] d = new byte[s.length + 1];
        System.arraycopy(s, 0, d, 0, s.length);
        return d;
    }

    public static String decodingCString(List<Byte> list) throws UnsupportedEncodingException {
        byte[] bytes = new byte[list.size()];
        for (int i = 0;i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return new String(bytes, "ASCII");
    }

    public static String decodingCString(ByteBuf buf) throws UnsupportedEncodingException {
        byte[] bytes = new byte[512];
        int index = 0;
        byte b = 0;
        while ((b = buf.readByte()) != 0) {
            if (index == bytes.length) {
                byte[] tmp = new byte[512 + bytes.length];
                System.arraycopy(bytes, 0, tmp, 0, bytes.length);
                bytes = tmp;
            }
            bytes[index++] = b;
        }
        if (index == 0) return "";
        byte[] t = new byte[index];
        System.arraycopy(bytes, 0, t, 0, index);
        String str = new String(t, "ASCII");
        return str;
    }
}
