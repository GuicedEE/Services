module com.hazelcast.all {
	requires transitive java.logging;
	requires transitive cache.api;
	requires java.transaction.xa;
	requires java.management;

	requires transitive java.xml;
	requires static org.hibernate.orm.core;

	uses com.hazelcast.client.ClientExtension;

	provides com.hazelcast.client.impl.ClientExtension with com.hazelcast.client.impl.clientside.DefaultClientExtension;

	uses com.hazelcast.com.fasterxml.jackson.core.JsonFactory;

	provides com.hazelcast.com.fasterxml.jackson.core.JsonFactory with com.hazelcast.com.fasterxml.jackson.core.JsonFactory;

	uses com.hazelcast.internal.serialization.DataSerializerHook;

	uses com.hazelcast.instance.impl.NodeExtension;
	provides com.hazelcast.instance.impl.NodeExtension with com.hazelcast.instance.impl.DefaultNodeExtension;

	uses com.hazelcast.internal.serialization.PortableHook;

	uses com.hazelcast.nio.serialization.SerializerHook;

	uses com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
	provides com.hazelcast.spi.discovery.DiscoveryStrategyFactory with com.hazelcast.spi.discovery.multicast.MulticastDiscoveryStrategyFactory
			;

	uses com.hazelcast.spi.impl.servicemanager.ServiceDescriptorProvider;
	provides com.hazelcast.spi.impl.servicemanager.ServiceDescriptorProvider with com.hazelcast.cp.internal.RaftServiceDescriptorProvider, com.hazelcast.cp.internal.datastructures.RaftDataServiceDescriptorProvider, com.hazelcast.internal.longregister.LongRegisterServiceDescriptorProvider;

	provides javax.cache.spi.CachingProvider with com.hazelcast.cache.HazelcastCachingProvider;

	exports com.hazelcast.client.config;
	exports com.hazelcast.client.cache.impl;

	exports com.hazelcast.client;
	exports com.hazelcast.collection;

	exports com.hazelcast.config;
	exports com.hazelcast.config.matcher;
	exports com.hazelcast.config.properties;
	exports com.hazelcast.console;
	exports com.hazelcast.core;
	exports com.hazelcast.topic;
	exports com.hazelcast.core.server;
	exports com.hazelcast.cluster;
	exports com.hazelcast.map;

	exports com.hazelcast.internal.serialization;
	exports com.hazelcast.internal.serialization.impl;
	exports com.hazelcast.nio.serialization;
	exports com.hazelcast.logging to com.hazelcast.hibernate;
	exports com.hazelcast.internal.util to com.hazelcast.hibernate;

	exports com.hazelcast.instance;
}
