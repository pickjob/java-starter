package stdlib.jcf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

/**
 * @author pickjob@126.com
 * @date 2022-12-16
 */
public class EnumMapShowCase {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("展示指定key为enum类型Map");
        EnumMap<Keys, String> enumMap = new EnumMap<>(Keys.class);
        enumMap.put(Keys.ID, "ID");
        enumMap.put(Keys.NAME, "NAME");
        logger.info("{}", enumMap);
    }
}

enum Keys {
    ID,
    NAME
}
