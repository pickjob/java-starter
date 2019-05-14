package spring.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jca.context.BootstrapContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.scheduling.quartz.SchedulerContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringValueResolver;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import spring.entity.Office;

import javax.resource.spi.BootstrapContext;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author pickjob@126.com
 * @time 2019-04-24
 **/
@Component
public class SimpleBean implements BeanNameAware
            , BeanClassLoaderAware
            , BeanFactoryAware
            , EnvironmentAware
            , EmbeddedValueResolverAware
            , ResourceLoaderAware
            , ApplicationEventPublisherAware
            , MessageSourceAware
            , ApplicationContextAware
            , ServletContextAware
            , BootstrapContextAware
            , LoadTimeWeaverAware
            , NotificationPublisherAware
            , ServletConfigAware
            , SchedulerContextAware
            , ImportAware {
    private static Logger logger = LogManager.getLogger(SimpleBean.class);
    private AtomicInteger index = new AtomicInteger(0);

    @Override
    public void setBeanName(String name) {
        logger.info("BeanNameAware: {}", index.incrementAndGet());
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        logger.info("BeanClassLoaderAware: {}", index.incrementAndGet());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        logger.info("BeanFactoryAware: {}", index.incrementAndGet());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("ApplicationContextAware: {}", index.incrementAndGet());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        logger.info("ApplicationEventPublisherAware: {}", index.incrementAndGet());
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        logger.info("EmbeddedValueResolverAware: {}", index.incrementAndGet());
    }

    @Override
    public void setEnvironment(Environment environment) {
        logger.info("EnvironmentAware: {}", index.incrementAndGet());
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        logger.info("MessageSourceAware: {}", index.incrementAndGet());
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        logger.info("ResourceLoaderAware: {}", index.incrementAndGet());
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        logger.info("ImportAware: {}", index.incrementAndGet());
    }

    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
        logger.info("LoadTimeWeaverAware: {}", index.incrementAndGet());
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        logger.info("NotificationPublisherAware: {}", index.incrementAndGet());
    }

    @Override
    public void setServletConfig(javax.servlet.ServletConfig servletConfig) {
        logger.info("ServletConfigAware: {}", index.incrementAndGet());
    }

    @Override
    public void setServletContext(javax.servlet.ServletContext servletContext) {
        logger.info("ServletContextAware: {}", index.incrementAndGet());
    }

    @Override
    public void setSchedulerContext(SchedulerContext schedulerContext) {
        logger.info("SchedulerContextAware: {}", index.incrementAndGet());
    }

    @Override
    public void setBootstrapContext(BootstrapContext bootstrapContext) {
        logger.info("setBootstrapContext: {}", index.incrementAndGet());
    }

    public String saySomething() {
        logger.info("SimpleBeanSay: Hello.");
        return "Hello World";
    }



}
