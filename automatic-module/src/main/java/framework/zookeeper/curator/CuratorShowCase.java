package framework.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.data.Stat;

/**
 * @author pickjob@126.com
 * @time 2019-06-04
 */
public class CuratorShowCase {
    private static final Logger logger = LogManager.getLogger(CuratorShowCase.class);

    public static void main(String[] args) {
        String timestamp = "" + System.currentTimeMillis();
        String path = "/" + timestamp;
        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();
            String charset = "utf-8";
            Stat stat = new Stat();
            // CRUD
            client.create().forPath("/" + timestamp, timestamp.getBytes(charset));
            client.getData().storingStatIn(stat).forPath(path);
            logger.info("before: {}, stat: {}", new String(client.getData().forPath(path), charset), stat.toString());
            client.setData().forPath(path, "hello".getBytes(charset));
            client.getData().storingStatIn(stat).forPath(path);
            logger.info("after: {}, state: {}", new String(client.getData().forPath(path), charset), stat);
            client.delete().forPath(path);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }
}
