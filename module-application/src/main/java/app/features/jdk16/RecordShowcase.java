package app.features.jdk16;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Record
 *
 * @author: pickjob@126.com
 * @date: 2024-09-02
 */
public class RecordShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Point point = new Point(10, 20);
        logger.info("Point(x: {}, y: {})", point.x, point.y);
    }

    record Point(int x, int y) {}
}