package app.spring.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
@Component
public class AwareShowCaseBean implements BeanNameAware
            , BeanClassLoaderAware
            , BeanFactoryAware
            , EnvironmentAware
            , EmbeddedValueResolverAware
            , ResourceLoaderAware
            , ApplicationEventPublisherAware
            , MessageSourceAware
            , ApplicationContextAware
            , LoadTimeWeaverAware
            , NotificationPublisherAware
            , ImportAware {
    private static final Logger logger = LogManager.getLogger();
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
}