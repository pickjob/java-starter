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
    requires javax.resource.api;
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
    // jwt
    requires java.jwt;
    // prometheus
    requires simpleclient;
    requires simpleclient.httpserver;
    // javafx
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.web;
    requires javafx.media;
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
    opens smpp.util to com.fasterxml.jackson.databind;

    opens fx.hello.coding to javafx.graphics;
    opens fx.hello.fxml to javafx.graphics
            , javafx.fxml
            ;
    opens fx.ui to javafx.base
            , javafx.graphics
            ;
    opens fx.modena to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.ensemble.controls to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.ensemble.charts to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.ensemble.animation to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.ensemble.layout to javafx.base
            , javafx.graphics
            , javafx.fxml
            ;
    opens fx.ensemble.scenegraph to javafx.base
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