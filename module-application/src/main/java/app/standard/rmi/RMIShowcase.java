package app.standard.rmi;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rmi_implement.LogInterfaceImplement;
import rmi_interface.LogInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

/**
 * RMI(Remote Method Invoke):
 *      角色:
 *          Server: 调用服务端
 *          Client: 调用客户端
 *          UnicastRemoteObject.export(): 暴露远程调用
 *          Rmiregistry: 注册、查找 可用  远程调用
 *          LocateRegistry
 *          Naming: JNDI抽象, 底层使用 LocateRegistry
 *      编码规范:
 *          RPC服务接口:
 *              继承 java.rmi.Remote
 *              方法 抛出 java.rmi.RemoteException
 *      配置属性:
 *          java.rmi.server.hostname
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public class RMIShowcase {
    private static final Logger logger = LogManager.getLogger();
    private static final String REGISTRY_SERVER = "localhost";
    private static final int REGISTRY_PORT = 1099;
    private static final String EXPORT_OBJ_NAME = "Logging";
    private static final CountDownLatch serverReady = new CountDownLatch(1);

    public static void main(String[] args) {
        // 启动服务端
        new Thread(RMIShowcase::serverRunnable).start();
        // 启动客户端
        new Thread(RMIShowcase::clientRunnable).start();
    }

    private static void serverRunnable() {
        try {
            System.setProperty("java.rmi.server.hostname", "DYING");
            // Registry实现
            Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            LogInterface stub = new LogInterfaceImplement();
            UnicastRemoteObject.exportObject(stub, REGISTRY_PORT);
            registry.bind(EXPORT_OBJ_NAME, stub);
            // Naming实现
            // Naming.bind(String.format("rmi://%s:%s/%s", REGISTRY_SERVER, REGISTRY_PORT, EXPORT_OBJ_NAME), stub);
            logger.info("RMIServer is running ...");
            serverReady.countDown();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
            // Registry实现
            LogInterface loggerRemote = (LogInterface) LocateRegistry.getRegistry(REGISTRY_SERVER, REGISTRY_PORT).lookup(EXPORT_OBJ_NAME);
            // Naming实现
            // RemoteInterface loggerRemote = (RemoteInterface) Naming.lookup(String.format("rmi://%s:%s/%s", REGISTRY_SERVER, REGISTRY_PORT, EXPORT_OBJ_NAME));
            loggerRemote.info("Hello World");
        } catch (Exception e) {
            logger.error(e);
        }
    }
}