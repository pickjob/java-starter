package framework.zookeeper.curator.election;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * @author pickjob@126.com
 * @time 2019-06-04
 */
public class LeaderSelectorShowCase {
    private static final Logger logger = LogManager.getLogger(LeaderSelectorShowCase.class);

    public static void main(String[] args) {
        String timestamp = "" + System.currentTimeMillis();
        String path = "/leader/" + timestamp;
        CuratorFramework client = null;
        LeaderSelector leaderSelector = null;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();
            LeaderSelectorListener listener = new LeaderSelectorListener() {
                @Override
                public void takeLeadership(CuratorFramework client) throws Exception {
                    logger.info("Get leadership");
                    countDownLatch.countDown();
                }

                @Override
                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                    logger.info("State changed");
                }
            };
            leaderSelector = new LeaderSelector(client, path, listener);
            leaderSelector.start();
            countDownLatch.await();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            CloseableUtils.closeQuietly(leaderSelector);
            CloseableUtils.closeQuietly(client);
        }
    }
}
