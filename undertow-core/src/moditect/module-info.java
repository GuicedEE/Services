module undertow.core {

	requires org.jboss.logging;

	requires java.security.jgss;

	requires static alpn.api;
	requires static io.undertow.parser.generator;
	requires jdk.unsupported;
	requires java.naming;
	requires static java.sql;
	requires java.compiler;

	exports io.undertow;
	exports io.undertow.util;

	exports org.xnio;
	exports org.xnio.nio;
	exports org.xnio.management;
	exports org.xnio.channels;
	//exports org.xnio.fc;
	exports org.xnio.conduits;
	exports org.xnio.ssl;
	exports org.xnio.http;

	exports org.wildfly.common.context;

	requires java.management;
	requires java.security.sasl;

	opens org.xnio._private to org.jboss.logging;

	uses org.xnio.XnioProvider;

	provides org.xnio.XnioProvider with org.xnio.nio.NioXnioProvider;

	exports io.undertow.security.api;
	exports io.undertow.security.handlers;
	exports io.undertow.security.idm;
	exports io.undertow.security.impl;
	exports io.undertow.attribute;
	exports io.undertow.channels;
	exports io.undertow.client;
	exports io.undertow.client.ajp;
	exports io.undertow.client.http;
	exports io.undertow.client.http2;
	exports io.undertow.conduits;
	exports io.undertow.connector;
	exports io.undertow.io;
	exports io.undertow.predicate;
	exports io.undertow.protocols.ajp;

	exports io.undertow.protocols.alpn;
	exports io.undertow.protocols.http2;
	exports io.undertow.protocols.ssl;

	exports io.undertow.server;
	exports io.undertow.server.handlers;
	exports io.undertow.server.handlers.resource;
	exports io.undertow.server.handlers.proxy;
	exports io.undertow.server.handlers.proxy.mod_cluster;
	exports io.undertow.server.handlers.cache;
	exports io.undertow.server.handlers.accesslog;
	exports io.undertow.server.handlers.builder;
	exports io.undertow.server.handlers.encoding;
	exports io.undertow.server.handlers.error;
	exports io.undertow.server.handlers.form;
	exports io.undertow.server.handlers.sse;
	exports io.undertow.server.protocol;
	exports io.undertow.server.protocol.proxy;
	exports io.undertow.server.protocol.ajp;
	exports io.undertow.server.protocol.framed;
	exports io.undertow.server.protocol.http;
	exports io.undertow.server.protocol.http2;
	exports io.undertow.server.session;

	exports io.undertow.websockets.spi;
	exports io.undertow.websockets.client;
	exports io.undertow.websockets.core;
	exports io.undertow.websockets.core.function;
	exports io.undertow.websockets.core.protocol;
	exports io.undertow.websockets;
	exports io.undertow.websockets.core.protocol.version08;
	exports io.undertow.websockets.core.protocol.version13;
	exports io.undertow.websockets.core.protocol.version07;
	exports io.undertow.websockets.extensions;


	uses io.undertow.attribute.ExchangeAttributeBuilder;
	uses io.undertow.predicate.PredicateBuilder;
	uses io.undertow.server.handlers.builder.HandlerBuilder;


	opens io.undertow to org.jboss.logging;

}
