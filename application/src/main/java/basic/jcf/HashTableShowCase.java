package basic.jcf;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author pickjob@126.com
 * @time 2019-04-26
 */
public class HashTableShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(HashTableShowCase.class);

    @Override
    public void showSomething() {
        logger.info("HashTable插入 key为null 或 value为null 会空指针");
        Map hashtable = new Hashtable();
        try {
            hashtable.put(null, "aaa");
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            hashtable.put("aaa", null);
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            hashtable.put("aaa", "aaa");
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
