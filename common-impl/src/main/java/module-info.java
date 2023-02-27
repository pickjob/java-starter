module java.starter.common.impl {
    // RMI
    requires java.rmi;
    // logging
    requires org.apache.logging.log4j;

    // SPI Interface && Implement Provider
    requires java.starter.common;
    provides common.services.SPIInterface with common.impl.services.LoggerService;

    // export
    exports common.impl.rmi;
    exports common.impl.services;
}
