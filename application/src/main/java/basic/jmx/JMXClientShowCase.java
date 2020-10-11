package basic.jmx;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.*;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClientShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(JMXClientShowCase.class);
    private static final String SERVICE_URl = "service:jmx:rmi://localhost:1098/jndi/rmi://localhost:1099/hello";

    @Override
    public void showSomething() {
        try {
            MBeanServerConnection mbsc = JMXConnectorFactory
                    .connect(new JMXServiceURL(SERVICE_URl), null)
                    .getMBeanServerConnection();

            // Standard MBean
            ObjectName standardHelloObjectName = new ObjectName("jmx.standard:type=mbean, name=StandardHello");
            StandardHelloMBean proxy1 = JMX.newMBeanProxy(mbsc, standardHelloObjectName, StandardHelloMBean.class);
            proxy1.setName("standard");
            // Notification MBean
            ObjectName notificationHelloObjectName = new ObjectName("jmx.notify:type=mbean, name=NotificationHello");
            mbsc.addNotificationListener(notificationHelloObjectName, new ClientListener(), null, null);
            // Dynamic MBean
            ObjectName dynamicHelloObjectName = new ObjectName("jmx.dynamic:type=mbean, name=DynamicHello");
            DynamicMBean proxy3 = JMX.newMBeanProxy(mbsc, dynamicHelloObjectName, DynamicMBean.class);
            proxy3.setAttribute(new Attribute("name", "hello"));

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static class ClientListener implements NotificationListener {

        @Override
        public void handleNotification(Notification notification, Object handback) {
            logger.info(notification);
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn = (AttributeChangeNotification) notification;
                logger.info("attributeName: {}, attributeType: {}", acn.getAttributeName(), acn.getAttributeType());
            }
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
//
//    @Override
//    public int order() {
//        return 1;
//    }
}
