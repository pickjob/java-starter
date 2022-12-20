package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GreetRemote extends Remote {
    String sayHello(String name) throws RemoteException;
}
