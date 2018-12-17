package jmx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CommonKey;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JMXServer {
	private static final Logger logger = LogManager.getLogger(JMXServer.class);
    private static final String SERVICE_URL = "service:jmx:rmi://localhost:1098/jndi/rmi://localhost:1099/hello";

    public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(CommonKey.RMI_REGISTRY_PORT);
			JMXConnectorServer cs = JMXConnectorServerFactory
                    .newJMXConnectorServer(new JMXServiceURL(SERVICE_URL), null, ManagementFactory.getPlatformMBeanServer());
			MBeanServer mbs = cs.getMBeanServer();

            //Standard MBean
			ObjectName helloObjectName1 = new ObjectName("jmx.standard:type=mbean,name=Hello");
			mbs.registerMBean(new jmx.standard.Hello(), helloObjectName1);
			//Notification MBean
            ObjectName helloObjectNam2 = new ObjectName("jmx.notify:type=mbean,name=Hello");
            mbs.registerMBean(new jmx.notify.Hello(), helloObjectNam2);
            //Dynamic MBean
            ObjectName helloObjectNam3 = new ObjectName("jmx.dynamic:type=mbean,name=Hello");
            mbs.registerMBean(new jmx.dynamic.Hello(), helloObjectNam3);

			cs.start();
			logger.info("JMX server is running ...");
			Thread.sleep(Long.MAX_VALUE);
		} catch (Exception e) {
		    e.printStackTrace();
			logger.error(e);
		}
	}
}
