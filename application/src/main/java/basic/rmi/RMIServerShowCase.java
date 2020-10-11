package basic.rmi;

import app.common.IShowCase;
import basic.rmi.service.Hello;
import basic.rmi.service.HelloImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

public class RMIServerShowCase implements IShowCase {
	private static final Logger logger = LogManager.getLogger(RMIServerShowCase.class);

	@Override
	public void showSomething() {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		new Thread(() -> {
			try {
//			String host = "localhost";
//			System.setProperty("java.rmi.server.hostname", host);
				Hello stub = new HelloImpl();
				UnicastRemoteObject.exportObject(stub, 1098);
//			Naming.bind("basic.rmi//localhost:" + RMI_REGISY_PORT + "/hello", stub);
				Registry registry = LocateRegistry.createRegistry(1099);
				registry.bind("hello", stub);
				logger.info("RMIServerShowCase is running ...");
				countDownLatch.countDown();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}).start();
		try {
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
