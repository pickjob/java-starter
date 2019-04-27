package spring;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.bean.SimpleBean;
import spring.configuration.BasicConfiguration;

/**
 * @author pickjob@126.com
 * @time 2019-04-16
 **/
public class SpringMain {
    private static final Logger logger = LogManager.getLogger(SpringMain.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfiguration.class);
        SimpleBean simpleBean = context.getBean(SimpleBean.class);
        logger.info("SimpleBeanSay: {}", simpleBean.saySomething());
    }
}
