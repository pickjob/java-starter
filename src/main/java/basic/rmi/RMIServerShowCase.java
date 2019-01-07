package basic.rmi;

import basic.rmi.service.Hello;
import basic.rmi.service.HelloImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerShowCase {
	private static final Logger logger = LogManager.getLogger(RMIServerShowCase.class);

	public static void main(String[] args) {
		try {
//			String host = "localhost";
//			System.setProperty("java.rmi.server.hostname", host);
			Hello stub = new HelloImpl();
			UnicastRemoteObject.exportObject(stub, 1098);
//			Naming.bind("basic.rmi//localhost:" + RMI_REGISTRY_PORT + "/hello", stub);
			LocateRegistry.createRegistry(1099).bind("hello", stub);
			logger.info("RMIServerShowCase is running ...");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
