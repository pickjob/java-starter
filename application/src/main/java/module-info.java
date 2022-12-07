module java.starter {
    // JAXP
    requires java.xml;
    // jdk11 HttpClient
    requires java.net.http;
    // jdk17 Foreign Function And Memory
    requires jdk.incubator.foreign;
    // logging
    requires org.apache.logging.log4j;
    // archive & compress
    requires org.tukaani.xz;
    requires org.apache.commons.compress;
    // guava
    requires com.google.common;
}
