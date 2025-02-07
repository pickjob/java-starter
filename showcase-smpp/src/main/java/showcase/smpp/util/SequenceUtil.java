package showcase.smpp.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class SequenceUtil {
    private static final AtomicInteger seq = new AtomicInteger();

    public static int getNextSquence() {
        return seq.incrementAndGet();
    }
}
