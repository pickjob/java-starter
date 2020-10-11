package framework.zookeeper;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.EphemeralType;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkClientShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ZkClientShowCase.class);
    private static final String ROOT = "/";
    private static final String LOCK_PATH = "/lock";

    @Override
    public void showSomething() {
        logger.info("展示Zookeeper一般框架用法");
        try {
            CountDownLatch connected = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("wsl2:2181", Integer.MAX_VALUE,
                    event -> {
                        logger.info("Receive watched event：{}", event);
                        if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                            connected.countDown();
                        }
                    }
            );
            // 连接底层使用netty方式
            connected.await();
            // 创建各种类型 ZNode
            createNode(zooKeeper);
            // 遍历查询所有节点信息
            retrieveNodes(ROOT, zooKeeper);
            // 实现分布式锁
            distributeLock(zooKeeper);
            zooKeeper.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    private static void createNode(ZooKeeper zooKeeper) throws Exception {
        for (CreateMode createMode : CreateMode.values()) {
            Stat stat = zooKeeper.exists(ROOT + createMode.name(), false);
            if (stat == null) {
                if (createMode.isTTL()) {
                    logger.info("createMode: {}", createMode);
                    zooKeeper.create(ROOT + createMode.name(), createMode.name().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode, null, 60000L);
                } else {
                    zooKeeper.create(ROOT + createMode.name(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
                }
            }
        }
    }

    private static void retrieveNodes(String rootPath, ZooKeeper zooKeeper) throws Exception {
        List<String> children = zooKeeper.getChildren(rootPath, false);
        for (String child : children) {
            String childPath = rootPath.endsWith(ROOT) ? rootPath + child : rootPath + ROOT + child;
            try {
                Stat stat = new Stat();
                byte[] nodeData = zooKeeper.getData(childPath, null, stat);
                EphemeralType type = EphemeralType.get(stat.getEphemeralOwner());
                logger.info("childPath: {}, value: {}, type: {}", childPath, new String(nodeData), type);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            retrieveNodes(childPath, zooKeeper);
        }
    }

    // 分布锁
    private static void distributeLock(ZooKeeper zooKeeper) throws Exception {
        zooKeeper.create(LOCK_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建临时序列节点
        String path = zooKeeper.create(LOCK_PATH + "/id_", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
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
