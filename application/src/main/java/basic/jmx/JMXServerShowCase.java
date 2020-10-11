package basic.jmx;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

public class JMXServerShowCase implements IShowCase {
	private static final Logger logger = LogManager.getLogger(JMXServerShowCase.class);
    private static final String SERVICE_URL = "service:jmx:rmi://localhost:1098/jndi/rmi://localhost:1099/hello";

	@Override
	public void showSomething() {
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			new Thread(() -> {
				try {
					Registry registry = LocateRegistry.createRegistry(1099);
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
					countDownLatch.countDown();
					Thread.sleep(10000);
					cs.stop();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}).start();
			countDownLatch.await();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

//	@Override
//	public boolean isShow() {
//		return true;
//	}
//
//	@Override
//	public int order() {
//		return 0;
//	}
}
