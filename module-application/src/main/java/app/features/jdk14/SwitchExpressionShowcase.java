package app.features.jdk14;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Switch Expression
 *
 * @author: pickjob@126.com
 * @date: 2024-09-02
 */
public class SwitchExpressionShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        int idx = 3;
        String day = switch (idx) {
            case 0 -> "SUNDAY";
            case 1 -> "MONDAY";
            case 2 -> "TUESDAY";
            case 3 -> "WEDNESDAY";
            case 4 -> "THURSDAY";
            case 5 -> "FRIDAY";
            case 6 -> "SATURDAY";
            default -> "UNKNOW";
        };
        logger.info("Today is {}", day);
    }
}