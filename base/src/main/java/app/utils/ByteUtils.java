package app.utils;

public class ByteUtils {
    public static String byteToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (byte b : bytes) {
            stringBuilder.append(Integer.toHexString(b)).append(", ");
        }
        if (stringBuilder.length() > 1 ) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
