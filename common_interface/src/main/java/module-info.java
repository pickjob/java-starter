module java.starter.common_interface {
    //// Export ////
    exports spi_interface;
    exports rmi_interface;
    exports jmx_interface;

    requires java.rmi;
}