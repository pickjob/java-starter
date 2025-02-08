package app.features.jdk21;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * SwitchPattern
 *
 * @author: pickjob@126.com
 * @date: 2024-09-03
 **/
public class SwitchPatternShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info(formatterPatternSwitch(10));
        logger.info(formatterPatternSwitch("Hello World"));
    }

    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> obj.toString();
        };
    }
}