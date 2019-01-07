module pickjob.java.starter {
    // java
    requires java.sql;
    requires java.rmi;
    requires java.management;
    // logging
    requires org.apache.logging.log4j;
    // apache lang3
    requires org.apache.commons.lang3;
    // validation
    requires java.validation;
    // jackson
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    // netty
    requires io.netty.all;
    // zookeeper
    requires zookeeper;
    // rocketmq
    requires rocketmq.common;
    requires rocketmq.remoting;
    requires rocketmq.client;

    // exports
    exports basic.rmi.service to java.rmi;
    exports basic.jmx.standard;
    exports smpp.util to com.fasterxml.jackson.databind;
}