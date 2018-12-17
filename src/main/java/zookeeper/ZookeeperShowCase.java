package zookeeper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import util.CommonKey;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperShowCase {
    private static final Logger logger = LogManager.getLogger(ZookeeperShowCase.class);
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static final String NODE_PATH = "/hello";
    private static final String LOCK_PATH = "/lock";

    public void run() {
        Thread curThread = Thread.currentThread();
        try {
            ZooKeeper zooKeeper = new ZooKeeper(CommonKey.ZOOKEEPER_SERVER, 3000,
                    (e) -> {
                        logger.info("Receive watched event：{}", e);
                        if (Watcher.Event.KeeperState.SyncConnected == e.getState()) {
                            connectedSemaphore.countDown();
                        }
                    }
            );

            // zookeeper是异步的
            connectedSemaphore.await();

            basicOperation(zooKeeper);
            distributeLock(zooKeeper);

            zooKeeper.close();
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    // 基本操作
    private void basicOperation(ZooKeeper zooKeeper) throws Exception {
        if (zooKeeper.getState().equals(ZooKeeper.States.CONNECTED)) {
            // 是否存在
            Stat stat = zooKeeper.exists(NODE_PATH, false);
            if (stat != null) {
                // 删除
                zooKeeper.delete(NODE_PATH, stat.getVersion());
            }
            // 创建
            zooKeeper.create("/hello", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            Stat s = new Stat();
            // 获取
            byte[] data = zooKeeper.getData(NODE_PATH, true, s);
            // 设置
            zooKeeper.setData(NODE_PATH, "hello".getBytes(), s.getVersion());
        }
    }

    // 分布锁
    private void distributeLock(ZooKeeper zooKeeper) throws Exception {
        // 创建临时序列节点
        String path = zooKeeper.create(LOCK_PATH+"/id_", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        List<String> children = zooKeeper.getChildren(LOCK_PATH, false);
        Collections.sort(children);
        switch (children.indexOf(path)) {
            case -1: // 创建失败
                break;
            case 0: // 已经最小， 获得锁
                break;
            default: // 不最小，监控前面一个，看什么时候删除
                break;
        }
        Stat s = new Stat();
        zooKeeper.getData(path, false, s);
        // 释放， 删除锁
        zooKeeper.delete(path, s.getVersion());
        logger.info(path);
    }
}
