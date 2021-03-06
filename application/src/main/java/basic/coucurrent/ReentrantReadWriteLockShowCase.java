package basic.coucurrent;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author pickjob@126.com
 * @time 2019-12-17
 */
public class ReentrantReadWriteLockShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ReentrantReadWriteLockShowCase.class);

    @Override
    public void showSomething() {
        logger.info("展示读写锁用法, 读锁共享, 写锁排他");
        RWDictionary dictionary = new RWDictionary();
        for (int i = 0; i < 10; i++ ) {
            final int idx = i;
            new Thread(() -> {
                dictionary.put("" + idx, "bbb");
                dictionary.get("aaa");
            }).start();
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
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