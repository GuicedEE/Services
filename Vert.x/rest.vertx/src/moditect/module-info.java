module io.vertx.rest {
    requires io.vertx;

    requires jakarta.validation;
    requires jakarta.annotation;
    requires jakarta.ws.rs;
    requires org.slf4j;

    exports com.zandero.rest;
    exports com.zandero.rest.authentication;
    exports com.zandero.rest.authorization;
    exports com.zandero.rest.exception;
    exports com.zandero.rest.context;
    exports com.zandero.rest.injection;
    exports com.zandero.rest.bean;
    exports com.zandero.rest.reader;
    exports com.zandero.rest.writer;

}