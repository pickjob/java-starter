module java.starter {
    // SPI
    requires java.starter.common;
    requires java.starter.lib;
    uses common.services.SPIInterface;
    // RMI
    requires java.rmi;
    // jmx
    requires java.management;
    // JAXP
    requires java.xml;
    // jdk11 HttpClient
    requires java.net.http;
    // jdk17 Foreign Function And Memory
    requires jdk.incubator.foreign;
    // logging
    requires org.apache.logging.log4j;
    // archive & compress
    requires org.apache.commons.compress;
    requires org.tukaani.xz;
    // guava
    requires com.google.common;
    // netty
    requires io.netty.transport;
    requires io.netty.handler;
    requires io.netty.common;
    requires io.netty.buffer;
    requires io.netty.codec.http;
    requires io.netty.codec;
}
