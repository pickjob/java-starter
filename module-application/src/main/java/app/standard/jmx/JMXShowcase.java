package app.standard.jmx;

import jmx_implement.DynamicNameConfiguration;
import jmx_implement.NameConfiguration;
import jmx_implement.NotificationNameConfiguration;
import jmx_interface.NameConfigurationMXBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.*;
import javax.management.remote.*;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

/**
 * JMX(Java Management Extensions):
 *      角色:
 *          MXBeans
 *              Standard MXBeans
 *              Dynamic MXBeans
 *              Notification MXBeans
 *          MBeansServer: 注册/查询MBean、调用MBean、添加/移除监听器
 *          Remote Management
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public class JMXShowcase {
    private static final Logger logger = LogManager.getLogger();
    private static final int REGISTRY_PORT = 1099;
    private static final CountDownLatch serverReady = new CountDownLatch(1);
    private static final String SERVICE_URL = String.format("service:jmx:rmi://localhost:1098/jndi/rmi://localhost:%s/hello", REGISTRY_PORT);

    // Object Name
    private static final ObjectName standardObjectName;
    private static final ObjectName dynamicObjectName;
    private static final ObjectName notificationObjectName;

    static {
        try {
            standardObjectName = new ObjectName("domain: type=StandardMXBean, name=NameConfiguration");
            dynamicObjectName = new ObjectName("domain: type=DynamicMXBean, name=DynamicNameConfiguration");
            notificationObjectName = new ObjectName("domain: type=NotificationMXBean, name=NotificationNameConfiguration");
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        // 启动服务端
        new Thread(JMXShowcase::serverRunnable).start();
        // 启动客户端
        new Thread(JMXShowcase::clientRunnable).start();
    }

    private static void serverRunnable() {
        try {
            Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            JMXConnectorServer cs = JMXConnectorServerFactory
                    .newJMXConnectorServer(new JMXServiceURL(SERVICE_URL), null, ManagementFactory.getPlatformMBeanServer());
            MBeanServer mbs = cs.getMBeanServer();

            // Standard MXBean
            mbs.registerMBean(new NameConfiguration(), standardObjectName);

            // Dynamic MBean
            mbs.registerMBean(new DynamicNameConfiguration(), dynamicObjectName);

            // Notification MXBea
            NotificationNameConfiguration notificationNameConfiguration = new NotificationNameConfiguration();
            mbs.registerMBean(notificationNameConfiguration, notificationObjectName);

            cs.start();
            logger.info("JMX server is running ...");
            serverReady.countDown();
            Thread.sleep(1000 * 10);
            notificationNameConfiguration.setName("Foo");
            Thread.sleep(1000 * 5);
            notificationNameConfiguration.setName("Bar");
            Thread.sleep(1000 * 5);
            cs.stop();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
        } catch (Exception e) {
            logger.error(e);
        }
        try (JMXConnector connector =  JMXConnectorFactory.connect(new JMXServiceURL(SERVICE_URL), null)) {

            MBeanServerConnection mbsc = connector.getMBeanServerConnection();

            // Standard MXBean
            NameConfigurationMXBean standardMXBeanProxy = JMX.newMBeanProxy(mbsc, standardObjectName, NameConfigurationMXBean.class);
            standardMXBeanProxy.setName("standard");

            // Dynamic MBean
            DynamicMBean dynamicMBean = JMX.newMBeanProxy(mbsc, dynamicObjectName, DynamicMBean.class);
            dynamicMBean.setAttribute(new Attribute("name", "hello"));

            // Notification MBean
            mbsc.addNotificationListener(notificationObjectName, new ClientListener(), null, null);

            Thread.sleep(1000 * 5);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static class ClientListener implements NotificationListener {

        @Override
        public void handleNotification(Notification notification, Object handback) {
            logger.info(notification);
            if (notification instanceof AttributeChangeNotification acn) {
                logger.info("attributeName: {}, attributeType: {}", acn.getAttributeName(), acn.getAttributeType());
            }
        }
    }
}