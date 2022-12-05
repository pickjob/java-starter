package framework.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * Guava缓存支持:
 *      缓存加载:
 *          CacheBuilder.build(CacheLoader): 根据key创建Value缓存
 *              CacheLoader: load(K key): V
 *          cache.get(K, Callable<V>): 不存在计算返回Value并缓存
 *      缓存操作:
 *          cache.put(key, value)
 *          cache.invalidate(key)
 *          cache.refresh(K)
 *      缓存策略:
 *          CacheBuilder.removalListener(RemovalListener): 移除监听器
 *          Size-base:
 *              CacheBuilder.maximumSize(long)
 *              CacheBuilder.weigher(Weigher)
 *              CacheBuilder.maximumWeight(long)
 *          Timed-base:
 *              CacheBuilder.expireAfterAccess(long, TimeUnit)
 *              CacheBuilder.expireAfterWrite(long, TimeUnit)
 *
 * @author: pickjob@126.com
 * @date: 2022-12-05
 */
public class CacheShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
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
        logger.info(cache.get("你好啊"));
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            cache.get("idx" + i);
        }
        logger.info("Cache size: {}", cache.size());
    }
}
