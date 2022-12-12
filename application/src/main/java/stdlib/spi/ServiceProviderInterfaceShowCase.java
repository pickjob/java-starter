package stdlib.spi;

import common.services.ServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ServiceLoader;

/**
 * ServiceProviderInterface: 核心概念
 *      Service: 服务接口
 *          public interface ServiceInterface {
 *              // ...
 *          }
 *      Provider: 服务实现
 *          module-info:
 *              provides service.interface;
 *          默认构造函数：
 *              static ServiceInterface provider()
 *              public ServiceInterfaceProvider()
 *          provider-configuration file:
 *              META-INF/services/path.to.ServiceInterface: Provider Class Full Name
 *      ServiceLoader: 服务加载器, 加载服务实现
 *          module-info:
 *              requires module;
 *              use service.interface;
 *
 * @author: pickjob@126.com
 * @date: 2022-12-12
 */
public class ServiceProviderInterfaceShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        ServiceLoader<ServiceInterface> services = ServiceLoader.load(ServiceInterface.class);
        services.forEach(ServiceInterface::service);
    }
}
