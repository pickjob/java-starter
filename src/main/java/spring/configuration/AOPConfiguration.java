package spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author pickjob@126.com
 * @time 2019-05-05
 */
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Configuration
public class AOPConfiguration {
}
