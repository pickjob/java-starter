package basic.rmi.service;

import java.rmi.RemoteException;

public class HelloImpl implements Hello {

    @Override
    public String sayHello(String name) throws RemoteException {
        return "hello:" + name;
    }

}
