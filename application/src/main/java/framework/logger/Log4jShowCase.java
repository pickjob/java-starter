package framework.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Log4j2: 日志框架
 *
 * @author: pickjob@126.com
 * @date: 2022-11-24
 */
public class Log4jShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String greetingTemplate = "Information levle: {}";
        logger.trace(greetingTemplate, "trace");
        logger.debug(greetingTemplate, "debug");
        logger.info(greetingTemplate, "info");
        logger.warn(greetingTemplate, "warn");
        logger.error(greetingTemplate, "error");
    }
}
