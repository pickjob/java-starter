package basic.jmx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StandardHello implements StandardHelloMBean {
    private static final Logger logger = LogManager.getLogger(StandardHello.class);

    private String name;

    @Override
    public String getName() {
        logger.info("get name");
        return name;
    }

    @Override
    public void setName(String name) {
        logger.info("set name {}", name);
        this.name = name;
    }
}
