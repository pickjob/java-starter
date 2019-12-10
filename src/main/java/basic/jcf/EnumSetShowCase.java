package basic.jcf;

import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;

/**
 * @author pickjob@126.com
 * @time 2019-12-10
 */
public class EnumSetShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(EnumSetShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示EnumSet的部分静态方法");
    }

    @Override
    public void showSomething() {
        EnumSet<KEYS> sets = EnumSet.allOf(KEYS.class);
        logger.info("{}", sets);
    }
}

enum KEYS {
    KEY_A,
    KEY_B,
    KEY_C
}