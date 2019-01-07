package basic.jmx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.*;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClientShowCase {
    private static final Logger logger = LogManager.getLogger(JMXClientShowCase.class);
    private static final String SERVICE_URl = "service:jmx:rmi://localhost:1098/jndi/rmi://localhost:1099/hello";

    public static void main(String[] args) {
        try {
            MBeanServerConnection mbsc = JMXConnectorFactory
                    .connect(new JMXServiceURL(SERVICE_URl), null)
                    .getMBeanServerConnection();

            //Standard MBean
            ObjectName helloObjectName1 = new ObjectName("jmx.standard:type=mbean, name=Hello");
            basic.jmx.standard.HelloMBean proxy1 = JMX.newMBeanProxy(mbsc, helloObjectName1, basic.jmx.standard.HelloMBean.class);
            proxy1.setName("standard");
            //Notification MBean
            ObjectName helloObjectNam2 = new ObjectName("jmx.notify:type=mbean, name=Hello");
            mbsc.addNotificationListener(helloObjectNam2, new ClientListener(), null, null);
            //Dynamic MBean
            ObjectName helloObjectNam3 = new ObjectName("jmx.dynamic:type=mbean, name=Hello");
            DynamicMBean proxy3 = JMX.newMBeanProxy(mbsc, helloObjectNam3, DynamicMBean.class);
            proxy3.setAttribute(new Attribute("name", "hello"));

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
	}

	public static class ClientListener implements NotificationListener {

        public void handleNotification(Notification notification, Object handback) {
            logger.info(notification);
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn = (AttributeChangeNotification) notification;
                logger.info("attributeName: {}, attributeType: {}", acn.getAttributeName(), acn.getAttributeType());
            }
        }
    }
}
