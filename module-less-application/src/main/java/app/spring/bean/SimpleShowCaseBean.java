package app.spring.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
@Component
public class SimpleShowCaseBean {
    private static final Logger logger = LogManager.getLogger();

    public String saySomething() {
        return "hello world";
    }
}