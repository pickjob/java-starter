package smpp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmm");

    public static String receiptTime() {
        String result = null;
        result = sdf.format(new Date());
        return result;
    }
}
