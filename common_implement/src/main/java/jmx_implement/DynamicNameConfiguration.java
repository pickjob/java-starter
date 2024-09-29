package jmx_implement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;

/**
 * JMX Dynamic MXBean Implement
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public class DynamicNameConfiguration implements DynamicMBean {
    private static final Logger logger = LogManager.getLogger();

    private String name;

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        logger.info("DynamicMXBean get attribute: {}", name);
        if("name".equals(attribute)) {
            return name;
        }
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        logger.info("DynamicMXBean set attribute: {}", name);
        if("name".equals(attribute.getName())) {
            this.name = String.valueOf(attribute.getValue());
        }
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return new MBeanInfo(
                this.getClass().getName(),
                "Name Configuration Manager Bean",
                null,
                null,  // constructors
                null,
                null); // notifications;
    }
}