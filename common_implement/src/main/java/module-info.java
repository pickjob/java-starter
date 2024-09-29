module java.starter.common_implement {
    //// Export ////
    exports rmi_implement;
    exports jmx_implement;

    // SPI
    provides spi_interface.LogInterface with spi_implement.LogInterfaceImplement;

    //// User module ////
    requires java.starter.common_interface;

    //// Java module ////
    // rmi
    requires java.rmi;
    // jmx
    requires java.management;

    //// Framework module ////
    // Apache Log4j2
    requires org.apache.logging.log4j;
}