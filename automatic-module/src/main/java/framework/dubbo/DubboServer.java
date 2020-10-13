package framework.dubbo;

import app.common.IShowCase;
import framework.dubbo.common.ISayHelloImpl;
import framework.dubbo.common.ISayHello;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * @author pickjob@126.com
 * @time 2019-05-31
 */
public class DubboServer implements IShowCase {
    private static final Logger logger = LogManager.getLogger(DubboServer.class);

    @Override
    public void showSomething() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                ServiceConfig<ISayHelloImpl> service = new ServiceConfig<>();
                ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-api");
                applicationConfig.setLogger("log4j2");
                service.setApplication(applicationConfig);
                RegistryConfig registryConfig = new RegistryConfig("zookeeper://wsl2:2181");
                service.setRegistry(registryConfig);
                ProtocolConfig protocolConfig = new ProtocolConfig();
                protocolConfig.setTransporter("netty");
                protocolConfig.setSerialization("dubbo");
                service.setInterface(ISayHello.class);
                service.setRef(new ISayHelloImpl());
                service.export();
                countDownLatch.countDown();
                logger.info("Server is running ...");
                Thread.sleep(6000);
                service.unexport();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
//
//    @Override
//    public int order() {
//        return 0;
//    }
}
