package framework.dubbo.common;

/**
 * @author pickjob@126.com
 * @time 2019-05-31
 */
public class ISayHelloImpl implements ISayHello {

    @Override
    public String sayHello(String name) {
        return String.format("Hello %s today is a good day!", name);
    }
}
