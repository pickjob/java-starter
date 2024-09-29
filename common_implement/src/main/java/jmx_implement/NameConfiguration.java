package jmx_implement;

import jmx_interface.NameConfigurationMXBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JMX Standard MXBean Implement
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public class NameConfiguration implements NameConfigurationMXBean {
    private static final Logger logger = LogManager.getLogger();

    private String name;

    @Override
    public void setName(String name) {
        logger.info("StandardMXBean set name: {}", name);
        this.name = name;
    }

    @Override
    public String getName() {
        logger.info("StandardMXBean get name: {}", name);
        return name;
    }
}