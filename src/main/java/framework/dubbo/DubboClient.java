package framework.dubbo;

import framework.dubbo.common.ISayHello;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-05-31
 */
public class DubboClient {
    private static final Logger logger = LogManager.getLogger(DubboClient.class);

    public static void main(String[] args) {
        ReferenceConfig<ISayHello> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://localhost:2181");
        reference.setRegistry(registryConfig);
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setTransporter("netty");
        protocolConfig.setSerialization("dubbo");
        reference.setInterface(ISayHello.class);
        ISayHello service = reference.get();
        String message = service.sayHello("dubbo");
        logger.info(message);
    }
}
