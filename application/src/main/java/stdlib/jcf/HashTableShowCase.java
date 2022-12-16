package stdlib.jcf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author pickjob@126.com
 * @date 2022-12-16
 */
public class HashTableShowCase {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
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
}
