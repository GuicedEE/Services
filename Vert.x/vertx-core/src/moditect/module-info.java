module io.vertx.core {
	requires java.logging;
	requires jdk.unsupported;

	requires static java.naming;

	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.core;

	exports io.vertx.ext.auth.authorization;
	exports io.vertx.ext.auth.authentication;
	//exports io.vertx.core.cli.impl;

	exports io.vertx.core;
	exports io.vertx.core.buffer;
	//exports io.vertx.core.cli;
	//exports io.vertx.core.cli.annotations;
	//exports io.vertx.core.cli.converters;
	exports io.vertx.core.datagram;
	exports io.vertx.core.dns;
	exports io.vertx.core.eventbus;
	exports io.vertx.core.file;
	exports io.vertx.core.http;

	exports io.vertx.core.json;
	exports io.vertx.core.json.jackson;
	exports io.vertx.core.json.pointer;

	exports io.vertx.core.metrics;
	exports io.vertx.core.net;

	exports io.vertx.core.shareddata;
	exports io.vertx.core.spi;
	exports io.vertx.core.spi.cluster;
	exports io.vertx.core.spi.context.storage;
	exports io.vertx.core.spi.file;
	exports io.vertx.core.spi.json;
	//exports io.vertx.core.spi.launcher;
	exports io.vertx.core.spi.metrics;
	exports io.vertx.core.spi.observability;
	//exports io.vertx.core.spi.resolver;
	exports io.vertx.core.spi.tls;
	exports io.vertx.core.spi.tracing;
	exports io.vertx.core.spi.transport;

	exports io.vertx.core.streams;
	exports io.vertx.core.tracing;

	exports io.vertx.ext.auth;

	exports io.vertx.ext.web;
	exports io.vertx.ext.web.codec;
	exports io.vertx.ext.web.codec.spi;
	exports io.vertx.ext.web.common;
	exports io.vertx.ext.web.common.template;
	exports io.vertx.ext.web.handler;
	exports io.vertx.ext.web.handler.sockjs;
	exports io.vertx.ext.web.multipart;
	exports io.vertx.ext.web.sstore;

	exports io.vertx.core.http.impl;

	exports io.vertx.core.streams.impl to io.vertx.rabbitmq;

	//uses io.vertx.core.spi.launcher.CommandFactory;
	uses reactor.blockhound.integration.BlockHoundIntegration;

	uses io.vertx.core.spi.VertxServiceProvider;
	uses io.vertx.core.spi.VerticleFactory;

	provides reactor.blockhound.integration.BlockHoundIntegration with io.netty.util.internal.Hidden.NettyBlockHoundIntegration,
	reactor.blockhound.integration.LoggingIntegration,
		reactor.blockhound.integration.ReactorIntegration,
		reactor.blockhound.integration.RxJava2Integration,
		reactor.blockhound.integration.StandardOutputIntegration;

	provides io.vertx.ext.auth.hashing.HashingAlgorithm with io.vertx.ext.auth.impl.hash.SHA1,
		io.vertx.ext.auth.impl.hash.SHA256,
		io.vertx.ext.auth.impl.hash.SHA512,
		io.vertx.ext.auth.impl.hash.PBKDF2;

	opens io.vertx.core.internal.logging to io.vertx.rabbitmq;

	opens io.vertx.core.impl to io.vertx.rabbitmq;
	exports io.vertx.core.internal to io.vertx.rabbitmq;
}