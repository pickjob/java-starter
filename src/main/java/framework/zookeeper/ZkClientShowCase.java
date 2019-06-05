package framework.zookeeper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkClientShowCase {
    private static final Logger logger = LogManager.getLogger(ZkClientShowCase.class);
    private static final String LOCK_PATH = "/lock";

    public static void main(String[] args) {
        try {
            CountDownLatch connected = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("localhost", 2181,
                    (e) -> {
                        logger.info("Receive watched event：{}", e);
                        if (Watcher.Event.KeeperState.SyncConnected == e.getState()) {
                            connected.countDown();
                        }
                    }
            );
            // zookeeper是异步的
            connected.await();

            retrieveNodes("/", zooKeeper);
            distributeLock(zooKeeper);

            zooKeeper.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void retrieveNodes(String nodePath, ZooKeeper zooKeeper) throws Exception {
        byte[] nodeData = zooKeeper.getData(nodePath, null, null);
        String nodeValue = byteToHexString(nodeData);
        String nodeString = new String(nodeData, "utf-8");
        logger.info("node: {}, nodeValue: {}, nodeString: {}", nodePath, nodeValue, nodeString);
        List<String> paths = zooKeeper.getChildren(nodePath, null);
        for (String p : paths) {
            retrieveNodes( "/".equals(nodePath) ? nodePath + p : nodePath + "/" + p, zooKeeper);
        }
    }

    // 分布锁
    private static void distributeLock(ZooKeeper zooKeeper) throws Exception {
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

    private static String byteToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("[");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++ ) {
            list.add((int)bytes[i]);
        }
        list.forEach(b -> {
            stringBuilder.append(Integer.toHexString(b)).append(", ");
        });
        if (stringBuilder.length() > 1 ) stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
