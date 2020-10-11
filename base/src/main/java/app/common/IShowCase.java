package app.common;

/**
 * @author pickjob@126.com
 * @time 2019-04-20
 */
public interface IShowCase {
    void showSomething();

    default boolean isShow() {
        return false;
    }

    default int order() {
        return Integer.MAX_VALUE;
    }
}
