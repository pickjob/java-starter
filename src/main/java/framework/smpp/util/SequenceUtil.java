package framework.smpp.util;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceUtil {
    private static final AtomicInteger seq = new AtomicInteger();

    public static int getNextSquence() {
        return seq.incrementAndGet();
    }
}
