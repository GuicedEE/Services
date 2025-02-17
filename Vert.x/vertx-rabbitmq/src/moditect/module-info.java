module io.vertx.rabbitmq {
	requires transitive io.vertx.core;

	requires io.vertx.core.logging;

	requires com.rabbitmq.client;

	exports io.vertx.rabbitmq;

	opens io.vertx.rabbitmq to io.vertx;
	opens io.vertx.rabbitmq.impl to io.vertx;
}