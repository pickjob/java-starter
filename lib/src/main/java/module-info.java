module java.starter.lib {
    // SPI Provider
    requires java.starter.common;
    provides common.services.ServiceInterface with lib.services.LoggerService;
    // logging
    requires org.apache.logging.log4j;
}
