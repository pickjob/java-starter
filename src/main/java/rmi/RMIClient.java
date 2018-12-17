package rmi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CommonKey;

import java.rmi.registry.LocateRegistry;

public class RMIClient {
	private static final Logger logger = LogManager.getLogger(RMIClient.class);

	public static void main(String[] args) throws Throwable {
//		Hello hello = (Hello) Naming.lookup("rmi://localhost:1099/hello");
		Hello hello = (Hello) LocateRegistry.getRegistry("localhost", CommonKey.RMI_REGISTRY_PORT).lookup("hello");
		String response = hello.sayHello("clientName");
		logger.info(response);
	}

}
