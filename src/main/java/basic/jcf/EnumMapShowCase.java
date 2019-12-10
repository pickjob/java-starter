package basic.jcf;

import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

/**
 * @author pickjob@126.com
 * @time 2019-12-10
 */
public class EnumMapShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(EnumMapShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示指定key为enum类型Map");
    }

    @Override
    public void showSomething() {
        EnumMap<Keys, String> enumMap = new EnumMap<Keys, String>(Keys.class);
        enumMap.put(Keys.ID, "ID");
        enumMap.put(Keys.NAME, "NAME");
        logger.info("{}", enumMap);
    }
}

enum Keys {
    ID,
    NAME
}