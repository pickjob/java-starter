package basic.common;

/**
 * @author pickjob@126.com
 * @time 2019-04-20
 */
public interface IShowCase {

    void saySomething();

    void showSomething();

    default boolean isShow() {
        return false;
    }
}
