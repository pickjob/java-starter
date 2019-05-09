module pickjob.java.starter {
    // java
    requires java.sql;
    requires java.rmi;
    requires java.management;
    requires java.naming;
    requires java.net.http;
    // logging
    requires org.apache.logging.log4j;
    // apache lang3
    requires org.apache.commons.lang3;
    // mysql
    requires mysql.connector.java;
    // dataSource
    requires com.zaxxer.hikari;
    // jee
    requires java.validation;
    requires javax.jms.api;
    requires javax.servlet.api;
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
    // spring
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires spring.jdbc;
    requires spring.tx;
    requires org.aspectj.weaver;


    // exports
    opens basic.rmi.service to java.rmi;
    opens basic.jmx.standard;
    opens framework.validator.annotation to org.hibernate.validator;
    opens framework.validator.domain to org.hibernate.validator;
    opens smpp.util to com.fasterxml.jackson.databind;

    opens spring.configuration to spring.core
            , spring.beans
            , spring.context
            , spring.aop
            ;
    opens spring.bean to spring.core
            , spring.beans
            , spring.aop
            ;
    opens spring.bpp to spring.core
            , spring.beans
            , spring.aop
            ;
    opens spring.aop to spring.core
            , spring.beans
            , spring.aop
            ;

}