package spi_implement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spi_interface.LogInterface;

/**
 * SPI Implement
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 **/
public class LogInterfaceImplement implements LogInterface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void info(String message) {
        logger.info("SPI Implement: {}", message);
    }
}
