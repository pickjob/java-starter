/**
 *
 * @author pickjob@126.com
 * @date 2024-09-02
 **/
module java.starter.main {
    //// Export ////
    exports app.framework.mybatis.domain;

    // SPI
    uses spi_interface.LogInterface;

    //// User Module ////
    requires java.starter.common_interface;
    requires java.starter.common_implement;

    //// Java module ////
    // http
    requires java.net.http;
    // sql
    requires java.sql;
    // rmi
    requires java.rmi;
    // jmx
    requires java.management;

    //// Framework module ////
    // Apache Log4j2
    requires com.lmax.disruptor;
    requires org.tukaani.xz;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.slf4j.impl;
    // Apache Commons
    requires org.apache.commons.io;
    requires org.apache.commons.compress;
    requires org.apache.commons.codec;
    // JWT
    requires org.jose4j;
    // netty
    requires io.netty.common;
    requires io.netty.buffer;
    requires io.netty.transport;
    requires io.netty.handler;
    requires io.netty.codec.http;
    // hikariCP
    requires com.zaxxer.hikari;
    // mybatis
    requires org.mybatis;
}