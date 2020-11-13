module org.ehcache  {
	exports org.ehcache;
	exports org.ehcache.config;
	exports org.ehcache.config.builders;
	exports org.ehcache.config.units;
	exports org.ehcache.core;
	exports org.ehcache.core.config;
	exports org.ehcache.core.config.store;
	exports org.ehcache.core.util;
	exports org.ehcache.core.collections;
	exports org.ehcache.core.events;
	exports org.ehcache.core.exceptions;
	exports org.ehcache.core.internal.util;
	exports org.ehcache.core.internal.resilience;
	exports org.ehcache.core.osgi;
	exports org.ehcache.core.resilience;
	exports org.ehcache.core.spi;
	exports org.ehcache.core.statistics;
	exports org.ehcache.core.store;
	exports org.ehcache.event;
	exports org.ehcache.expiry;
	exports org.ehcache.jsr107.config;
	exports org.ehcache.jsr107.internal;
	exports org.ehcache.jsr107.internal.tck;
	exports org.ehcache.spi.copy;
	exports org.ehcache.spi.loaderwriter;
	exports org.ehcache.spi.persistence;
	exports org.ehcache.spi.resilience;
	exports org.ehcache.spi.serialization;
	exports org.ehcache.spi.service;
	exports org.ehcache.xml;

	requires transitive cache.api;
	requires transitive jakarta.xml.bind;
	requires org.slf4j;

	requires jdk.unsupported;

	provides jakarta.cache.spi.CachingProvider with org.ehcache.jsr107.EhcacheCachingProvider;

	uses  org.ehcache.core.spi.service.ServiceFactory;
	provides org.ehcache.core.spi.service.ServiceFactory with org.ehcache.impl.internal.store.heap.OnHeapStoreProviderFactory,
		org.ehcache.impl.internal.store.offheap.OffHeapStoreProviderFactory,
				org.ehcache.impl.internal.store.disk.OffHeapDiskStoreProviderFactory,
				org.ehcache.impl.internal.store.tiering.TieredStoreProviderFactory,
				org.ehcache.impl.internal.store.tiering.CompoundCachingTierProviderFactory,
				org.ehcache.impl.internal.store.loaderwriter.LoaderWriterStoreProviderFactory,
				org.ehcache.impl.internal.TimeSourceServiceFactory,
				org.ehcache.impl.internal.spi.serialization.DefaultSerializationProviderFactory,
				org.ehcache.impl.internal.spi.loaderwriter.DefaultCacheLoaderWriterProviderFactory,
				org.ehcache.impl.internal.spi.event.DefaultCacheEventListenerProviderFactory,
				org.ehcache.impl.internal.executor.DefaultExecutionServiceFactory,
				org.ehcache.impl.internal.persistence.DefaultLocalPersistenceServiceFactory,
				org.ehcache.impl.internal.persistence.DefaultDiskResourceServiceFactory,
				org.ehcache.impl.internal.loaderwriter.writebehind.WriteBehindProviderFactory,
				org.ehcache.impl.internal.events.CacheEventNotificationListenerServiceProviderFactory,
				org.ehcache.impl.internal.spi.copy.DefaultCopyProviderFactory,
				org.ehcache.impl.internal.sizeof.DefaultSizeOfEngineProviderFactory,
				org.ehcache.core.statistics.DefaultStatisticsServiceFactory,
				org.ehcache.impl.internal.spi.resilience.DefaultResilienceStrategyProviderFactory;

	uses org.ehcache.xml.CacheManagerServiceConfigurationParser;
	provides org.ehcache.xml.CacheManagerServiceConfigurationParser with org.ehcache.jsr107.internal.Jsr107ServiceConfigurationParser;

	uses org.ehcache.xml.CacheServiceConfigurationParser;
	provides org.ehcache.xml.CacheServiceConfigurationParser with org.ehcache.jsr107.internal.Jsr107CacheConfigurationParser;

	uses org.ehcache.xml.CacheResourceConfigurationParser;

	opens org.ehcache.xml.model to jakarta.xml.bind;
}

