package app.features.jdk17;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Switch Expression
 *
 * @author: pickjob@126.com
 * @date: 2024-09-02
 */
public class PatternMatching4SwitchShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Switch case int: {}", formatterPatternSwitch(10));
        logger.info("Switch case string: {}", formatterPatternSwitch("Hello World"));
        logger.info("Switch case null: {}", formatterPatternSwitch(null));
    }

    static String formatterPatternSwitch(Object o) {
        return switch (o) {
            case null      -> "null pointer";
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> o.toString();
        };
    }
}