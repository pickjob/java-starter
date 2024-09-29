package app.features.jdk21;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * StringTemplate
 *
 * @author pickjob@126.com
 * @date 2024-09-03
 **/
public class StringTemplateShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String name = "World";
        String message = STR."Hello \{name}.";
        System.out.println(message);
    }
}