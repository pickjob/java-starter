package rmi_implement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rmi_interface.LogInterface;

import java.rmi.RemoteException;

/**
 * RMI Implement
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public class LogInterfaceImplement implements LogInterface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void info(String message) throws RemoteException {
        logger.info("RMI Implement: {}", message);
    }
}