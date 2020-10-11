package framework.zookeeper.curator;

import app.common.IShowCase;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * @author pickjob@126.com
 * @time 2019-06-04
 */
public class CuratorShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(CuratorShowCase.class);
    private static final String LOCK_PATH = "/lock";

    @Override
    public void showSomething() {
        logger.info("展示Curator示例");
        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.newClient("wsl2", new ExponentialBackoffRetry(1000, 3));
            client.start();
            // CRUD
            curatorCRUD(client);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }

    public void curatorCRUD(CuratorFramework client) throws Exception {
        String timestamp = "" + System.currentTimeMillis();
        String path = "/" + timestamp;
        String charset = "utf-8";
        Stat stat = new Stat();
        client.create().forPath(path, timestamp.getBytes(charset));
        client.getData().storingStatIn(stat).forPath(path);
        logger.info("before: {}, stat: {}", new String(client.getData().forPath(path), charset), stat.toString());
        client.setData().forPath(path, "hello".getBytes(charset));
        client.getData().storingStatIn(stat).forPath(path);
        logger.info("after: {}, state: {}", new String(client.getData().forPath(path), charset), stat);
        client.delete().forPath(path);
    }

    private void distributedLock(CuratorFramework client) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, LOCK_PATH);
        if (lock.acquire(1000, TimeUnit.NANOSECONDS)) {
            try {
                // do some work inside of the critical section here
            } finally {
                lock.release();
            }
        }
    }
}
