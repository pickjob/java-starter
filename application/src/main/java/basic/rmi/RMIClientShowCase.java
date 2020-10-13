package basic.rmi;

import app.common.IShowCase;
import basic.rmi.service.Hello;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.Naming;

public class RMIClientShowCase implements IShowCase {
	private static final Logger logger = LogManager.getLogger(RMIClientShowCase.class);

    @Override
    public void showSomething() {
        try {
            Hello hello = (Hello) Naming.lookup("rmi://localhost:1099/hello");
//        Hello hello = (Hello) LocateRegistry.getRegistry("localhost", 1099).lookup("hello");
            String response = hello.sayHello("clientName");
            logger.info(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
//
//    @Override
//    public int order() {
//        return 1;
//    }
}
