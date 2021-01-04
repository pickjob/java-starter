package spring;


import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.bean.SimpleShowCaseBean;
import spring.configuration.BasicConfiguration;

/**
 * @author pickjob@126.com
 * @time 2019-04-16
 **/
public class SpringShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(SpringShowCase.class);

    @Override
    public void showSomething() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfiguration.class);
        SimpleShowCaseBean simpleBean = context.getBean(SimpleShowCaseBean.class);
        logger.info("SimpleBeanSay: {}", simpleBean.saySomething());
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
