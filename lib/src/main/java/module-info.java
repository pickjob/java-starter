module java.starter.lib {
    // SPI Provider
    requires java.starter.common;
    provides common.services.SPIInterface with lib.services.LoggerService;
    // logging
    requires org.apache.logging.log4j;
}
