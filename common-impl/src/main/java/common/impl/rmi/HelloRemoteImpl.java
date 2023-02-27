package common.impl.rmi;


import common.rmi.RemoteInterface;

import java.rmi.RemoteException;

public class HelloRemoteImpl implements RemoteInterface {
    @Override
    public String greeting(String name) throws RemoteException {
        return "Hello: " + name;
    }
}
