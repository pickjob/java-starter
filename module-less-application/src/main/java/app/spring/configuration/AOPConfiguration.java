package app.spring.configuration;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
@Configuration
@EnableAspectJAutoProxy(
        proxyTargetClass = true,
        exposeProxy = true
)
public class AOPConfiguration {
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        return defaultAdvisorAutoProxyCreator;
    }
}