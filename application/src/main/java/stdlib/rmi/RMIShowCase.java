package stdlib.rmi;


import common.rmi.GreetRemote;
import lib.rmi.HelloImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

/**
 * RMI:
 *      ServiceInterface:
 *          继承 java.rmi.Remote
 *          方法 抛出 java.rmi.RemoteException
 *
 * @author: pickjob@126.com
 * @date: 2022-12-20
 */
public class RMIShowCase {
    private static final Logger logger = LogManager.getLogger();
    private static final String REGISTRY_SERVER = "localhost";
    private static final int REGISTRY_PORT = 1099;
    private static final String EXPORT_OBJ_NAME = "Greet";
    private static final CountDownLatch serverReady = new CountDownLatch(1);

    public static void main(String[] args) {
        // 启动服务端
        new Thread(RMIShowCase::serverRunnable).start();
        // 启动客户端
        new Thread(RMIShowCase::clientRunnable).start();
    }

    private static void serverRunnable() {
        try {
            System.setProperty("java.rmi.server.hostname", "172.31.144.1");
            // Registry实现
            Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            GreetRemote stub = new HelloImpl();
            UnicastRemoteObject.exportObject(stub, REGISTRY_PORT);
            registry.bind(EXPORT_OBJ_NAME, stub);
            // Naming实现
            Naming.bind(String.format("rmi://%s:%s/%s", REGISTRY_SERVER, REGISTRY_PORT, EXPORT_OBJ_NAME), stub);
            logger.info("RMIServer is running ...");
            serverReady.countDown();
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
            // GreetRemote hello = (GreetRemote) LocateRegistry.getRegistry(REGISTRY_SERVER, REGISTRY_PORT).lookup(EXPORT_OBJ_NAME);
            GreetRemote hello = (GreetRemote) Naming.lookup(String.format("rmi://%s:%s/%s", REGISTRY_SERVER, REGISTRY_PORT, EXPORT_OBJ_NAME));
            String response = hello.sayHello("Client.");
            logger.info(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
