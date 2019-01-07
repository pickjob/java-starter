package basic.rmi;

import basic.rmi.service.Hello;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIClientShowCase {
	private static final Logger logger = LogManager.getLogger(RMIClientShowCase.class);

	public static void main(String[] args) throws Throwable {
		Hello hello = (Hello) Naming.lookup("rmi://localhost:1099/hello");
//		Hello hello = (Hello) LocateRegistry.getRegistry("localhost", 1099).lookup("hello");
		String response = hello.sayHello("clientName");
		logger.info(response);
	}

}
