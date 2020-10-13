package framework.dubbo;

import app.common.IShowCase;
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
public class DubboClient implements IShowCase {
    private static final Logger logger = LogManager.getLogger(DubboClient.class);

    @Override
    public void showSomething() {
        ReferenceConfig<ISayHello> reference = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-api");
        applicationConfig.setLogger("log4j2");
        reference.setApplication(applicationConfig);
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://wsl2:2181");
        reference.setRegistry(registryConfig);
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setTransporter("netty");
        protocolConfig.setSerialization("dubbo");
        reference.setInterface(ISayHello.class);
        ISayHello service = reference.get();
        String message = service.sayHello("dubbo");
        logger.info(message);
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
//
//    @Override
//    public int order() {
//        return 1;
//    }
}
