module pickjob.java.starter {
    requires java.sql;
    requires java.management;
    requires java.rmi;

    requires org.apache.logging.log4j;
    requires org.apache.commons.lang3;

    requires java.validation;

    requires io.netty.all;
    requires zookeeper;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;

    exports smpp.util to com.fasterxml.jackson.databind;
}