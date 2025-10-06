module com.ehcache {

  requires transitive cache.api;
  requires transitive org.slf4j;
  requires java.management;
  requires java.xml;
  requires jakarta.xml.bind;
  requires jdk.unsupported;

  exports org.ehcache.jsr107;
  exports org.ehcache.xml;
  exports org.ehcache.jsr107.config;

  provides javax.cache.spi.CachingProvider with org.ehcache.jsr107.EhcacheCachingProvider;
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
                                                           org.ehcache.impl.internal.spi.resilience.DefaultResilienceStrategyProviderFactory,
                                                           org.ehcache.core.internal.statistics.DefaultStatisticsServiceFactory;

  provides org.ehcache.xml.CacheManagerServiceConfigurationParser with org.ehcache.jsr107.internal.Jsr107ServiceConfigurationParser;
  provides org.ehcache.xml.CacheServiceConfigurationParser with org.ehcache.jsr107.internal.Jsr107CacheConfigurationParser;

  opens org.ehcache.xml.model to jakarta.xml.bind;

  uses org.ehcache.core.spi.service.ServiceFactory;
  uses org.ehcache.xml.CacheManagerServiceConfigurationParser;
  uses org.ehcache.xml.CacheServiceConfigurationParser;
  uses org.ehcache.xml.CacheResourceConfigurationParser;
}