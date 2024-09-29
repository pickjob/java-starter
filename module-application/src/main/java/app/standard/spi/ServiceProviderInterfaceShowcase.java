package app.standard.spi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spi_interface.LogInterface;

import java.util.ServiceLoader;

/**
 * ServiceProviderInterface: 核心概念
 *      Service: 服务接口
 *          public interface ServiceInterface {
 *              // ...
 *          }
 *      Provider: 服务实现
 *          module-info:
 *              provides service.interface with service.implement;
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
 * @date: 2024-09-08
 */
public class ServiceProviderInterfaceShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        ServiceLoader<LogInterface> services = ServiceLoader.load(LogInterface.class);
        services.forEach(log -> log.info("hello world"));
    }
}