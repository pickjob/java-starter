package spring.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author pickjob@126.com
 * @time 2019-05-15
 */
@Component
public class SimpleShowCaseBean {
    private static final Logger logger = LogManager.getLogger(SimpleShowCaseBean.class);

    public String saySomething() {
        return "hello world";
    }
}
