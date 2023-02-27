package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    String greeting(String name) throws RemoteException;
}
