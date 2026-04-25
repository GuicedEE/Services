module io.vertx.client.kafka {
	requires transitive io.vertx.core;

	requires io.vertx.core.logging;

	requires transitive org.apache.kafka.client;

	exports io.vertx.kafka.admin;
	exports io.vertx.kafka.client.common;
	exports io.vertx.kafka.client.consumer;
	exports io.vertx.kafka.client.producer;
	exports io.vertx.kafka.client.serialization;

	opens io.vertx.kafka.admin to io.vertx.core;
	opens io.vertx.kafka.admin.impl to io.vertx.core;
	opens io.vertx.kafka.client.common to io.vertx.core;
	opens io.vertx.kafka.client.common.impl to io.vertx.core;
	opens io.vertx.kafka.client.common.tracing to io.vertx.core;
	opens io.vertx.kafka.client.consumer to io.vertx.core;
	opens io.vertx.kafka.client.consumer.impl to io.vertx.core;
	opens io.vertx.kafka.client.producer to io.vertx.core;
	opens io.vertx.kafka.client.producer.impl to io.vertx.core;
	opens io.vertx.kafka.client.serialization to io.vertx.core;
}

