package rmi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CommonKey;

import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
	private static final Logger logger = LogManager.getLogger(RMIServer.class);

	public static void main(String[] args) {
		try {
//		System.setProperty("java.rmi.server.hostname", host);
			Hello stub = new HelloImpl();
			UnicastRemoteObject.exportObject(stub, CommonKey.RMI_DATA_PORT);
//			Naming.bind("rmi//localhost:" + RMI_REGISTRY_PORT + "/hello", stub);
			LocateRegistry.createRegistry(CommonKey.RMI_REGISTRY_PORT).bind("hello", stub);
			logger.info("RMIServer is running ...");
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

}
