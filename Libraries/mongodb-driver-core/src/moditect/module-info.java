module org.mongodb.driver.core {
	// bson types (Document, BsonValue, Codec...) appear throughout the exported API.
	requires transitive org.mongodb.bson;
	// Non-optional runtime dependency upstream: the driver registers RecordCodecProvider.
	requires org.mongodb.bson.record.codec;

	requires java.logging;
	requires java.management;
	requires java.naming;
	requires java.security.jgss;
	requires java.sql;

	// All optional upstream. The Netty transport is only used when explicitly selected
	// (default is NIO) and SLF4J is probed reflectively, so neither may be a hard edge.
	requires static org.slf4j;
	requires static io.netty.buffer;
	requires static io.netty.transport;
	requires static io.netty.handler;

	exports com.mongodb;
	exports com.mongodb.annotations;
	exports com.mongodb.assertions;
	exports com.mongodb.bulk;
	exports com.mongodb.client.cursor;
	exports com.mongodb.client.gridfs.codecs;
	exports com.mongodb.client.gridfs.model;
	exports com.mongodb.client.model;
	exports com.mongodb.client.model.bulk;
	exports com.mongodb.client.model.changestream;
	exports com.mongodb.client.model.densify;
	exports com.mongodb.client.model.fill;
	exports com.mongodb.client.model.geojson;
	exports com.mongodb.client.model.geojson.codecs;
	exports com.mongodb.client.model.mql;
	exports com.mongodb.client.model.search;
	exports com.mongodb.client.model.vault;
	exports com.mongodb.client.result;
	exports com.mongodb.connection;
	exports com.mongodb.event;
	exports com.mongodb.lang;
	exports com.mongodb.management;
	exports com.mongodb.selector;
	exports com.mongodb.session;
	exports com.mongodb.spi.dns;

	// The com.mongodb.internal.* packages are exported on purpose: the reactivestreams
	// driver is a separate module and calls straight into them. Under an automatic module
	// every package was readable; leaving these encapsulated turns into IllegalAccessError
	// at runtime rather than a compile failure.
	exports com.mongodb.internal;
	exports com.mongodb.internal.async;
	exports com.mongodb.internal.async.function;
	exports com.mongodb.internal.authentication;
	exports com.mongodb.internal.binding;
	exports com.mongodb.internal.build;
	exports com.mongodb.internal.bulk;
	exports com.mongodb.internal.capi;
	exports com.mongodb.internal.client.model;
	exports com.mongodb.internal.client.model.bulk;
	exports com.mongodb.internal.client.model.changestream;
	exports com.mongodb.internal.client.vault;
	exports com.mongodb.internal.connection;
	exports com.mongodb.internal.connection.netty;
	exports com.mongodb.internal.connection.tlschannel;
	exports com.mongodb.internal.connection.tlschannel.async;
	exports com.mongodb.internal.connection.tlschannel.impl;
	exports com.mongodb.internal.connection.tlschannel.util;
	exports com.mongodb.internal.diagnostics.logging;
	exports com.mongodb.internal.dns;
	exports com.mongodb.internal.event;
	exports com.mongodb.internal.function;
	exports com.mongodb.internal.graalvm.substitution;
	exports com.mongodb.internal.inject;
	exports com.mongodb.internal.logging;
	exports com.mongodb.internal.operation;
	exports com.mongodb.internal.operation.retry;
	exports com.mongodb.internal.selector;
	exports com.mongodb.internal.session;
	exports com.mongodb.internal.thread;
	exports com.mongodb.internal.time;
	exports com.mongodb.internal.validator;
}

