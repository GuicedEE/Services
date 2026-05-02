module io.vertx.cassandra.client {
	requires transitive io.vertx.core;

	requires io.vertx.core.logging;

	requires transitive com.datastax.oss.driver.core;

	exports io.vertx.cassandra;
	exports io.vertx.cassandra.impl;
	exports io.vertx.cassandra.impl.tracing;

	opens io.vertx.cassandra to io.vertx.core;
	opens io.vertx.cassandra.impl to io.vertx.core;
	opens io.vertx.cassandra.impl.tracing to io.vertx.core;
}


