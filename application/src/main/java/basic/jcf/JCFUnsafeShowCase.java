package basic.jcf;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author pickjob@126.com
 * @time 2019-04-21
 */
public class JCFUnsafeShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger();
    private static final Integer MAX = 100000;

    @Override
    public void showSomething() {
        logger.info("示例JCF线程不安全类,多线程部分操作并不会抛异常,但是会丢数据");
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        Map<Integer, Integer> hashMap = new HashMap<>();
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < MAX; i++) {
            final int idx = i;
            executorService.execute(() -> {
                arrayList.add(idx);
                linkedList.add(idx);
                hashMap.put(idx, idx);
                copyOnWriteArrayList.add(idx);
            });
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("总数: {}", MAX);
        logger.info("arrayList总数: {}", arrayList.size());
        logger.info("linkedList总数: {}", linkedList.size());
        logger.info("hashMap总数: {}", hashMap.size());
        logger.info("copyOnWriteArrayList总数: {}", copyOnWriteArrayList.size());
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
