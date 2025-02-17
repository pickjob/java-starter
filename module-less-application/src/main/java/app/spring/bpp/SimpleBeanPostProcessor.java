package app.spring.bpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
@Component
public class SimpleBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessAfterInitialization: {}", beanName);
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessBeforeInitialization: {}", beanName);
        return bean;
    }
}