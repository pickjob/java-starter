module pickjob.java.starter {
    // java
    requires java.sql;
    requires java.rmi;
    requires java.management;
    requires java.naming;
    // logging
    requires org.apache.logging.log4j;
    // apache lang3
    requires org.apache.commons.lang3;
    // mysql
    requires mysql.connector.java;
    // jee
    requires java.validation;
    requires javax.jms.api;
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
    // activemq
    requires activemq.client;

    // exports
    opens basic.rmi.service to java.rmi;
    opens basic.jmx.standard;
    opens framework.validator.annotation to org.hibernate.validator;
    opens framework.validator.domain to org.hibernate.validator;
    opens smpp.util to com.fasterxml.jackson.databind;
}