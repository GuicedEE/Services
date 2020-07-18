module com.hazelcast.hibernate {
	requires transitive java.logging;
	requires transitive cache.api;
	requires java.transaction.xa;
	requires java.management;

	requires transitive java.xml;
}
