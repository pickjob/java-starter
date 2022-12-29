package stdlib.jmx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.DynamicMBean;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

/**
 * JMX(Java Management Extensions):
 *      角色:
 *          MBeans
 *              Standard MBeans
 *              Dynamic MBeans
 *              Open MBeans
 *              Model MBeans
 *              MXBeans
 *          MBeansServer: 注册/查询MBean、调用MBean、添加/移除监听器
 *          Remote Management
 *
 *
 * @author pickjob@126.com
 * @date 2022-12-21
 */
public class JMXShowCase {
    private static final Logger logger = LogManager.getLogger();
    private static final int REGISTRY_PORT = 1099;
    private static final CountDownLatch serverReady = new CountDownLatch(1);
    private static final String SERVICE_URL = String.format("service:jmx:rmi://localhost:1098/jndi/rmi://localhost:%s/hello", REGISTRY_PORT);

    public static void main(String[] args) {
        // 启动服务端
        new Thread(JMXShowCase::serverRunnable).start();
        // 启动客户端
        new Thread(JMXShowCase::clientRunnable).start();
    }

    private static void serverRunnable() {
        try {
            Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            JMXConnectorServer cs = JMXConnectorServerFactory
                    .newJMXConnectorServer(new JMXServiceURL(SERVICE_URL), null, ManagementFactory.getPlatformMBeanServer());
            MBeanServer mbs = cs.getMBeanServer();

            // Standard MBean
            ObjectName standardHelloObjectName = new ObjectName("jmx.standard:type=mbean, name=StandardHello");
            mbs.registerMBean(new StandardHello(), standardHelloObjectName);
            // Notification MBean
            ObjectName notificationHelloObjectName = new ObjectName("jmx.notify:type=mbean, name=NotificationHello");
            mbs.registerMBean(new NotificationHello(), notificationHelloObjectName);
            // Dynamic MBean
            ObjectName dynamicHelloObjectName = new ObjectName("jmx.dynamic:type=mbean, name=DynamicHello");
            mbs.registerMBean(new DynamicHello(), dynamicHelloObjectName);
            cs.start();
            logger.info("JMX server is running ...");
            serverReady.countDown();
            cs.stop();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            MBeanServerConnection mbsc = JMXConnectorFactory
                    .connect(new JMXServiceURL(SERVICE_URL), null)
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
}
