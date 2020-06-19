package framework.zookeeper.curator.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author pickjob@126.com
 * @time 2019-06-05
 */
public class ServiceDiscoveryShowCase {
    private static final Logger logger = LogManager.getLogger(ServiceDiscoveryShowCase.class);

    public static void main(String[] args) {
        String timestamp = "" + System.currentTimeMillis();
        String path = "/discovery/" + timestamp;
        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();
            JsonInstanceSerializer<Map> serializer = new JsonInstanceSerializer<>(Map.class);
            ServiceDiscovery serviceDiscovery = ServiceDiscoveryBuilder.builder(Map.class)
                    .client(client)
                    .basePath(path)
                    .serializer(serializer)
                    .build();
            serviceDiscovery.start();
            String serviceName = "testService";
            ServiceInstance<Map> serviceInstance = ServiceInstance.<Map>builder()
                    .name(serviceName)
                    .port(100)
                    .build();
            ServiceInstance<Map> serviceInstance2 = ServiceInstance.<Map>builder()
                    .name(serviceName)
                    .port(101)
                    .build();
            serviceDiscovery.registerService(serviceInstance);
            serviceDiscovery.registerService(serviceInstance2);
            logger.info(serviceDiscovery.queryForInstances(serviceName));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }
}
