package app.spring;

import app.spring.bean.SimpleShowCaseBean;
import app.spring.configuration.BasicConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class SpringShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfiguration.class);
        SimpleShowCaseBean simpleBean = context.getBean(SimpleShowCaseBean.class);
        logger.info("SimpleBeanSay: {}", simpleBean.saySomething());
    }
}