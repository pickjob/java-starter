package features.jdk16;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: pickjob@126.com
 * @date: 2022-12-01
 */
public class RecordShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Point point = new Point(10, 20);
        logger.info("Point(x: {}, y: {})", point.x, point.y);
    }

    record Point(int x, int y) {}
}
