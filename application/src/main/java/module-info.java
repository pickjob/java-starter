module pickjob.java.starter {
    requires pickjob.java.starter.base;
    requires pickjob.java.starter.automatic.module;
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
    // jackson
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    // guava
    requires com.google.common;
    // netty
    requires io.netty.all;
    // validation
    requires java.validation;
    // lettuce
    requires lettuce.core;
    // javafx
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.web;
    requires javafx.media;

    // exports
    opens basic.rmi.service to java.rmi;
    opens basic.jmx.standard;
    // jackson
    opens smpp.util to com.fasterxml.jackson.databind;
    // validator
    exports frame.validator.annotation to org.hibernate.validator;
    opens frame.validator.domain to org.hibernate.validator;

    opens fx.hello to javafx.graphics
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
}