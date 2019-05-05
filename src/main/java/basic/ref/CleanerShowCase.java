package basic.ref;

import common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.Cleaner;

/**
 * @author pickjob@126.com
 * @time 2019-04-29
 */
public class CleanerShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(CleanerShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Cleaner用来注册当实例phantom reachable操作");
    }

    @Override
    public void showSomething() {
        Cleaner cleaner = Cleaner.create();
        cleaner.register(this, () -> {
            logger.info("CleanerShowCase实例phantom reachable");
        });
    }
}
