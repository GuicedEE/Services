module io.vertx.clustermanager.hazelcast {
	requires transitive io.vertx.core;
	requires transitive com.hazelcast.all;

	requires io.vertx.core.logging;
	requires static io.netty.common;

	exports io.vertx.spi.cluster.hazelcast;
	exports io.vertx.spi.cluster.hazelcast.spi;

	uses io.vertx.spi.cluster.hazelcast.spi.HazelcastObjectProvider;

	provides io.vertx.core.spi.VertxServiceProvider with io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

	opens io.vertx.spi.cluster.hazelcast to com.hazelcast.all, io.vertx.core;
	opens io.vertx.spi.cluster.hazelcast.impl to com.hazelcast.all, io.vertx.core;
}

