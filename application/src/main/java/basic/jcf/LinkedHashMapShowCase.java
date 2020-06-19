package basic.jcf;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author pickjob@126.com
 * @time 2019-12-10
 */
public class LinkedHashMapShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(LinkedHashMapShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示用LinkedHashMap来用作LRU策略缓存");
    }

    @Override
    public void showSomething() {
        Map cache = new LinkedHashMap() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 3;
            }
        };
        cache.put("keyA", "valA");
        cache.put("keyB", "valB");
        cache.put("keyC", "valC");
        cache.put("keyA", "valA2");
        cache.put("keyD", "valD");
        // HashMap 支持key null val null
        cache.put(null, null);
        logger.info("{}", cache);
    }
}
