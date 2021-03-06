package basic.jcf;

import app.common.IShowCase;
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
    public void showSomething() {
        logger.info("展示指定key为enum类型Map");
        EnumMap<Keys, String> enumMap = new EnumMap<Keys, String>(Keys.class);
        enumMap.put(Keys.ID, "ID");
        enumMap.put(Keys.NAME, "NAME");
        logger.info("{}", enumMap);
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}

enum Keys {
    ID,
    NAME
}