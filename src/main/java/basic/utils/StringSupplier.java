package basic.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author pickjob@126.com
 * @time 2019-04-24
 */
public class StringSupplier implements Supplier<String> {
    private static Logger logger = LogManager.getLogger(StringSupplier.class);
    private static final char[] chars = new char['z' - 'a' + 'Z' - 'A'  + 2];
    private static final int DEFAULT_LENGTH = 5;
    private static Random random = new Random(System.currentTimeMillis());

    static {
        for (int i = 0; i < 'z' - 'a' + 'Z' - 'A'  + 2; i++) {
            if (i < 'z' - 'a' + 1) chars[i] = (char)('a' + i);
            else chars[i] = (char)('A' + i - 'z' + 'a' - 1);
        }
        logger.info(Arrays.toString(chars));
    }

    @Override
    public String get() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            int idx = random.nextInt(chars.length);
            if (idx < 0 ) idx = -idx;
            sb.append(chars[idx]);
        }
        return sb.toString();
    }
}
