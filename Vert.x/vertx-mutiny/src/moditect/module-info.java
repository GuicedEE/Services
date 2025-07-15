module io.vertx.mutiny {
    requires transitive io.vertx.core;

    requires io.smallrye.common.ref;
    requires io.smallrye.mutiny;

    exports io.smallrye.context;
    exports io.smallrye.context.impl;
    exports io.smallrye.context.impl.cdi;
    exports io.smallrye.context.impl.wrappers;



    requires jakarta.cdi;

    requires static io.vertx.web;

    requires static io.vertx.codegen.api;
    //requires io.smallrye.mutiny.vertx.core;
    //requires vertx.mutiny.generator;
    //requires io.smallrye.mutiny.vertx.runtime;

    exports io.vertx.mutiny.core;
    exports io.vertx.mutiny.core.http;
    //exports io.vertx.mutiny.core.eventbus;
/*    exports io.vertx.mutiny.core.file;

    exports io.vertx.mutiny.core.net;
    exports io.vertx.mutiny.core.shareddata;
    exports io.vertx.mutiny.core.spi;
    exports io.vertx.mutiny.core.streams;
    exports io.vertx.mutiny.core.workerpool;
    exports io.vertx.mutiny.ext.auth;
    exports io.vertx.mutiny.ext.auth.authentication;
    exports io.vertx.mutiny.ext.auth.authorization;
    exports io.vertx.mutiny.ext.auth.oauth2;
    exports io.vertx.mutiny.ext.auth.shiro;
    exports io.vertx.mutiny.ext.mongo;
    exports io.vertx.mutiny.ext.sql;
    exports io.vertx.mutiny.ext.stomp;*/

    exports io.vertx.mutiny.ext.web;
    exports io.vertx.mutiny.ext.web.client;
    exports io.vertx.mutiny.ext.web.codec;
    exports io.vertx.mutiny.ext.web.handler;
    exports io.vertx.mutiny.ext.web.multipart;
    exports io.vertx.mutiny.ext.web.sstore;



    exports io.smallrye.mutiny.vertx;
    exports io.smallrye.mutiny.vertx.codegen;
    exports io.smallrye.mutiny.vertx.codegen.lang;
    exports io.smallrye.mutiny.vertx.codegen.methods;
    exports io.smallrye.mutiny.vertx.core;
    exports io.smallrye.mutiny.vertx.impl;

    opens io.smallrye.mutiny.vertx to io.vertx.core;
    opens io.smallrye.mutiny.vertx.codegen to io.vertx.core;
    opens io.smallrye.mutiny.vertx.codegen.lang to io.vertx.core;
    opens io.smallrye.mutiny.vertx.codegen.methods to io.vertx.core;
    opens io.smallrye.mutiny.vertx.core to io.vertx.core;
    opens io.smallrye.mutiny.vertx.impl to io.vertx.core;


/*

    exports io.smallrye.mutiny;
    exports io.smallrye.mutiny.converters;
    exports io.smallrye.mutiny.converters.multi;
    exports io.smallrye.mutiny.converters.uni;
    exports io.smallrye.mutiny.converters;
    exports io.smallrye.mutiny.groups;
    exports io.smallrye.mutiny.helpers;
    exports io.smallrye.mutiny.helpers.spies;
    exports io.smallrye.mutiny.helpers.queues;
    exports io.smallrye.mutiny.helpers.test;
    exports io.smallrye.mutiny.infrastructure;
    exports io.smallrye.mutiny.operators;
    exports io.smallrye.mutiny.operators.multi;
    exports io.smallrye.mutiny.operators.uni;
    exports io.smallrye.mutiny.subscription;
    exports io.smallrye.mutiny.tuples;
    exports io.smallrye.mutiny.unchecked;


    opens io.smallrye.mutiny to io.vertx.core;
    opens io.smallrye.mutiny.converters to io.vertx.core;
    opens io.smallrye.mutiny.converters.multi to io.vertx.core;
    opens io.smallrye.mutiny.converters.uni to io.vertx.core;
    opens io.smallrye.mutiny.converters to io.vertx.core;
    opens io.smallrye.mutiny.groups to io.vertx.core;
    opens io.smallrye.mutiny.helpers to io.vertx.core;
    opens io.smallrye.mutiny.helpers.spies to io.vertx.core;
    opens io.smallrye.mutiny.helpers.queues to io.vertx.core;
    opens io.smallrye.mutiny.helpers.test to io.vertx.core;
    opens io.smallrye.mutiny.infrastructure to io.vertx.core;
    opens io.smallrye.mutiny.operators to io.vertx.core;
    opens io.smallrye.mutiny.operators.multi to io.vertx.core;
    opens io.smallrye.mutiny.operators.uni to io.vertx.core;
    opens io.smallrye.mutiny.subscription to io.vertx.core;
    opens io.smallrye.mutiny.tuples to io.vertx.core;
    opens io.smallrye.mutiny.unchecked to io.vertx.core;
    opens io.smallrye.mutiny.vertx to io.vertx.core;
    opens io.smallrye.mutiny.vertx.codegen to io.vertx.core;
    opens io.smallrye.mutiny.vertx.codegen.lang to io.vertx.core;
    opens io.smallrye.mutiny.vertx.codegen.methods to io.vertx.core;
    opens io.smallrye.mutiny.vertx.core to io.vertx.core;
    opens io.smallrye.mutiny.vertx.impl to io.vertx.core;
*/



}