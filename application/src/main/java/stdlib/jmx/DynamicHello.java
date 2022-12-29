package stdlib.jmx;

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

public class DynamicHello implements DynamicMBean {
    private static final Logger logger = LogManager.getLogger(DynamicHello.class);

    private String name;

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        logger.info("get name");
        if("name".equals(attribute)) return name;
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        logger.info("set name from {} to {}", name, attribute.getValue());
        if("name".equals(attribute.getName())) this.name = String.valueOf(attribute.getValue());
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
                "Property Manager MBean",
                null,
                null,  // constructors
                null,
                null); // notifications;
    }
}
