package jmx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.*;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClient {
    private static final Logger logger = LogManager.getLogger(JMXClient.class);
    private static final String SERVICE_URl = "service:jmx:rmi://localhost:1098/jndi/rmi://localhost:1099/hello";


    public static void main(String[] args) {
        try {
            MBeanServerConnection mbsc = JMXConnectorFactory
                    .connect(new JMXServiceURL(SERVICE_URl), null)
                    .getMBeanServerConnection();

            //Standard MBean
            ObjectName helloObjectName1 = new ObjectName("jmx.standard:type=mbean,name=Hello");
            //Notification MBean
            ObjectName helloObjectNam2 = new ObjectName("jmx.notify:type=mbean,name=Hello");
            //Dynamic MBean
            ObjectName helloObjectNam3 = new ObjectName("jmx.dynamic:type=mbean,name=Hello");

            jmx.standard.HelloMBean proxy1 = JMX.newMBeanProxy(mbsc, helloObjectName1, jmx.standard.HelloMBean.class);
            proxy1.setName("hw");

            DynamicMBean proxy2 = JMX.newMBeanProxy(mbsc, helloObjectNam3, DynamicMBean.class);
            proxy2.setAttribute(new Attribute("name", "hw"));

            mbsc.addNotificationListener(helloObjectNam2, new ClientListener(), null, null);

            while (true) {
                Thread.sleep(100000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
	}

	public static class ClientListener implements NotificationListener {

        public void handleNotification(Notification notification,
                                       Object handback) {
            logger.info(notification);
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn = (AttributeChangeNotification) notification;
                logger.info(acn.getAttributeName());
                logger.info(acn.getAttributeType());
            }
        }
    }
}
