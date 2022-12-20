package lib.rmi;


import common.rmi.GreetRemote;

import java.rmi.RemoteException;

public class HelloImpl implements GreetRemote {
    @Override
    public String sayHello(String name) throws RemoteException {
        return "Hello: " + name;
    }
}
