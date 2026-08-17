module org.mongodb.driver.reactivestreams {
	// Driver-core and bson types dominate the exported API (MongoClientSettings, Document,
	// codecs), and Publisher is the return type of virtually every method here.
	requires transitive org.mongodb.driver.core;
	requires transitive org.mongodb.bson;
	requires transitive org.reactivestreams;

	// The driver is implemented on Reactor internally (com.mongodb.reactivestreams.client.internal),
	// so this is a hard runtime edge, not an optional one.
	requires reactor.core;

	exports com.mongodb.reactivestreams.client;
	exports com.mongodb.reactivestreams.client.gridfs;
	exports com.mongodb.reactivestreams.client.vault;

	// Exported for the same reason as driver-core's internals: these were freely readable
	// while the jar was an automatic module.
	exports com.mongodb.reactivestreams.client.internal;
	exports com.mongodb.reactivestreams.client.internal.crypt;
	exports com.mongodb.reactivestreams.client.internal.gridfs;
	exports com.mongodb.reactivestreams.client.internal.vault;
}

