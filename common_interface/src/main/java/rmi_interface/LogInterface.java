package rmi_interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI Interface
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public interface LogInterface extends Remote {
    void info(String message) throws RemoteException;
}