package app.standard.jcf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMapShowcase: LRU(Least Recently Used)策略缓存
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class LinkedHashMapShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("展示用LinkedHashMap来用作LRU策略缓存");
        Map<String, String> cache = new LinkedHashMap<>() {
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