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
    // guava
    requires com.google.common;
    // jackson
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    // jwt
    requires java.jwt;
    // netty
    requires io.netty.all;
    // mysql
    requires mysql.connector.java;
    // hikari
    requires com.zaxxer.hikari;
    // mybaits
    requires org.mybatis;
    // dubbo
    requires dubbo;
    // lettuce
    requires lettuce.core;
    // zkClient
    requires zookeeper;
    // curator
    requires curator.client;
    requires curator.framework;
    requires curator.recipes;
    requires curator.x.discovery;
    // rocketmq
    requires rocketmq.common;
    requires rocketmq.remoting;
    requires rocketmq.client;
    // activemq
    requires activemq.client;
    // javafx
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.web;
    requires javafx.media;
    // jee
    requires java.validation;
    requires javax.jms.api;
    requires javax.resource.api;
    requires javax.servlet.api;
    // spring
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.context.support;
    requires spring.web;
    requires spring.jdbc;
    requires spring.tx;
    requires org.aspectj.weaver;
    requires quartz;

    // exports
    opens basic.rmi.service to java.rmi;
    opens basic.jmx.standard;
    opens framework.validator.annotation to org.hibernate.validator;
    opens framework.validator.domain to org.hibernate.validator;
    opens framework.dubbo.common to dubbo;
    opens smpp.util to com.fasterxml.jackson.databind;
    opens fx.hello.coding to javafx.graphics;

    opens framework.mybatis to org.mybatis;

    opens fx.hello.fxml to javafx.graphics
            , javafx.fxml
            ;
    opens fx.layout to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.control to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.animation to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.charts to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.entity to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
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