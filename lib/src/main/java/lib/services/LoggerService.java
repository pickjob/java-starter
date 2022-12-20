package lib.services;

import common.services.SPIInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: pickjob@126.com
 * @date: 2022-12-12
 */
public class LoggerService implements SPIInterface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void service() {
        logger.info("service: {} ", "Hello SPI");
    }
}
