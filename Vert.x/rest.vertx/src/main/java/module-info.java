module com.zandero.rest.vertx {
    requires transitive io.vertx.core;
    requires transitive io.vertx.auth.common;
    requires transitive io.vertx.web;

    requires transitive jakarta.ws.rs;

    requires jakarta.validation;
    requires jakarta.annotation;

    requires org.slf4j;

    requires com.fasterxml.jackson.databind;

    exports com.zandero.rest;
    exports com.zandero.rest.authentication;
    exports com.zandero.rest.authorization;
    exports com.zandero.rest.exception;
    exports com.zandero.rest.context;
    exports com.zandero.rest.injection;
    exports com.zandero.rest.bean;
    exports com.zandero.rest.reader;
    exports com.zandero.rest.writer;
    exports com.zandero.rest.events;

}