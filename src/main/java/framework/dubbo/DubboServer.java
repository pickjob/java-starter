package framework.dubbo;

import framework.dubbo.common.ISayHelloImpl;
import framework.dubbo.common.ISayHello;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-05-31
 */
public class DubboServer {
    private static final Logger logger = LogManager.getLogger(DubboServer.class);

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-api-provider");
        applicationConfig.setLogger("log4j2");
        ServiceConfig<ISayHelloImpl> service = new ServiceConfig<>();
        service.setApplication(applicationConfig);
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://172.16.40.115:2181");
        service.setRegistry(registryConfig);
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setTransporter("netty");
        protocolConfig.setSerialization("dubbo");
        service.setInterface(ISayHello.class);
        service.setRef(new ISayHelloImpl());
        service.export();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
