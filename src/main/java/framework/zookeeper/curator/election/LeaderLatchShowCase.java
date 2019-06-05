package framework.zookeeper.curator.election;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.data.Stat;

/**
 * @author pickjob@126.com
 * @time 2019-06-04
 */
public class LeaderLatchShowCase {
    private static final Logger logger = LogManager.getLogger(LeaderLatchShowCase.class);

    public static void main(String[] args) {
        String timestamp = "" + System.currentTimeMillis();
        String path = "/leader/" + timestamp;
        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();
            LeaderLatch leaderLatch = new LeaderLatch(client, path);
            leaderLatch.start();
//            leaderLatch.hasLeadership();
            leaderLatch.await();
            logger.info("Get leadership");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }
}
