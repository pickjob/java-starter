package app.standard.concurrent.synchron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock: 读写锁, 读共享, 写排他(包括读)
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class ReentrantReadWriteLockShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        RWDictionary dictionary = new RWDictionary();
        for (int i = 0; i < num; i++ ) {
            final int idx = i;
            new Thread(() -> {
                dictionary.put("" + idx, idx);
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
            for (int i = 0; i < num; i++ ) {
                final int idx = i;
                new Thread(() -> {
                    logger.info("{}: {}", idx, dictionary.get("" + idx));
                }).start();
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}

class RWDictionary {
    private final Map<String, Object> m = new TreeMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Object get(String key) {
        r.lock();
        try { return m.get(key); }
        finally { r.unlock(); }
    }

    public Object put(String key, Object value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }
}