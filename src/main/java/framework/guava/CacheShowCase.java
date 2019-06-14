package framework.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

/**
 * @author pickjob@126.com
 * @time 2019-06-13
 */
public class CacheShowCase implements IShowCase {
    private static final Logger logger= LogManager.getLogger(CacheShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Guava框架Cache展示");
    }

    @Override
    public void showSomething() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(15)
                .expireAfterAccess(Duration.ofSeconds(10))
                .removalListener(notification -> {
                    logger.info("Cache remove key: {}, value: {}", notification.getKey(), notification.getValue());
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "value of " + key;
                    }
                });
        try {
            logger.info(cache.get("你好啊"));
            for (int i = 0; i < 20; i++) {
                Thread.sleep(1000);
                cache.get("idx" + i);
            }
            logger.info("Cache size: {}", cache.size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
