open module com.hazelcast.all {
	requires transitive java.logging;
	requires transitive cache.api;
	
	requires java.transaction.xa;
	requires java.management;
	
	requires static  jdk.unsupported;
	
	requires static org.apache.logging.log4j.core;
	requires static org.sl4fj;

	requires transitive java.xml;
	requires static org.hibernate.orm.core;
	requires static com.sun.jna;

	uses com.hazelcast.client.impl.ClientExtension;
	

	uses com.hazelcast.client.impl.protocol.MessageTaskFactoryProvider;
	provides com.hazelcast.client.impl.protocol.MessageTaskFactoryProvider with com.hazelcast.jet.impl.client.protocol.task.JetMessageTaskFactoryProvider;
	
	uses com.hazelcast.dataconnection.DataConnectionRegistration;
	provides com.hazelcast.dataconnection.DataConnectionRegistration with com.hazelcast.dataconnection.impl.JdbcDataConnectionRegistration,
											com.hazelcast.dataconnection.impl.HazelcastDataConnectionRegistration;

	provides com.hazelcast.client.impl.ClientExtension with com.hazelcast.client.impl.clientside.DefaultClientExtension;

	
	uses com.hazelcast.internal.serialization.DataSerializerHook;
	provides  com.hazelcast.internal.serialization.DataSerializerHook  with com.hazelcast.internal.cluster.impl.ClusterDataSerializerHook,
			  com.hazelcast.spi.impl.SpiDataSerializerHook,
				com.hazelcast.internal.partition.impl.PartitionDataSerializerHook,
				com.hazelcast.map.impl.MapDataSerializerHook,
				com.hazelcast.collection.impl.queue.QueueDataSerializerHook,
				com.hazelcast.multimap.impl.MultiMapDataSerializerHook,
				com.hazelcast.collection.impl.collection.CollectionDataSerializerHook,
				com.hazelcast.topic.impl.TopicDataSerializerHook,
				com.hazelcast.executor.impl.ExecutorDataSerializerHook,
				com.hazelcast.durableexecutor.impl.DurableExecutorDataSerializerHook,
				com.hazelcast.internal.locksupport.LockDataSerializerHook,
				com.hazelcast.internal.longregister.LongRegisterDataSerializerHook,
				com.hazelcast.transaction.impl.TransactionDataSerializerHook,
				com.hazelcast.replicatedmap.impl.operation.ReplicatedMapDataSerializerHook,
				com.hazelcast.cache.impl.CacheDataSerializerHook,
				com.hazelcast.ringbuffer.impl.RingbufferDataSerializerHook,
				com.hazelcast.wan.impl.WanDataSerializerHook,
				com.hazelcast.query.impl.predicates.PredicateDataSerializerHook,
				com.hazelcast.cardinality.impl.CardinalityEstimatorDataSerializerHook,
				com.hazelcast.client.impl.ClientDataSerializerHook,
				com.hazelcast.internal.management.ManagementDataSerializerHook,
				com.hazelcast.internal.ascii.TextProtocolsDataSerializerHook,
				com.hazelcast.scheduledexecutor.impl.ScheduledExecutorDataSerializerHook,
				com.hazelcast.internal.usercodedeployment.impl.UserCodeDeploymentSerializerHook,
				com.hazelcast.aggregation.impl.AggregatorDataSerializerHook,
				com.hazelcast.projection.impl.ProjectionDataSerializerHook,
				com.hazelcast.internal.config.ConfigDataSerializerHook,
				com.hazelcast.internal.journal.EventJournalDataSerializerHook,
				com.hazelcast.flakeidgen.impl.FlakeIdGeneratorDataSerializerHook,
				com.hazelcast.spi.impl.merge.SplitBrainDataSerializerHook,
				com.hazelcast.internal.crdt.CRDTDataSerializerHook,
				com.hazelcast.cp.event.impl.CpEventDataSerializerHook,
				com.hazelcast.cp.internal.raft.impl.RaftDataSerializerHook,
				com.hazelcast.cp.internal.RaftServiceDataSerializerHook,
				com.hazelcast.cp.internal.session.RaftSessionServiceDataSerializerHook,
				com.hazelcast.cp.internal.datastructures.atomiclong.AtomicLongDataSerializerHook,
				com.hazelcast.cp.internal.datastructures.atomicref.AtomicRefDataSerializerHook,
				com.hazelcast.cp.internal.datastructures.lock.LockDataSerializerHook,
				com.hazelcast.cp.internal.datastructures.semaphore.SemaphoreDataSerializerHook,
				com.hazelcast.cp.internal.datastructures.RaftDataServiceDataSerializerHook,
				com.hazelcast.cp.internal.datastructures.countdownlatch.CountDownLatchDataSerializerHook,
				com.hazelcast.internal.metrics.managementcenter.MetricsDataSerializerHook,
				com.hazelcast.sql.impl.SqlDataSerializerHook,
				com.hazelcast.json.internal.JsonDataSerializerHook,
				com.hazelcast.internal.util.collection.UtilCollectionSerializerHook,
				com.hazelcast.jet.core.JetDataSerializerHook,
				com.hazelcast.jet.core.metrics.MetricsDataSerializerHook,
				com.hazelcast.jet.impl.execution.init.JetInitDataSerializerHook,
				com.hazelcast.jet.impl.metrics.JetMetricsDataSerializerHook,
				com.hazelcast.jet.impl.observer.JetObserverDataSerializerHook,
				com.hazelcast.jet.impl.aggregate.AggregateDataSerializerHook,
				com.hazelcast.jet.config.JetConfigDataSerializerHook,
				com.hazelcast.internal.serialization.impl.compact.schema.SchemaDataSerializerHook,
				com.hazelcast.jet.impl.util.FunctionsSerializerHook
	;


	uses com.hazelcast.instance.impl.NodeExtension;
	provides com.hazelcast.instance.impl.NodeExtension with com.hazelcast.instance.impl.DefaultNodeExtension;

	uses com.hazelcast.internal.serialization.PortableHook;

	uses com.hazelcast.internal.serialization.SerializerHook;
	provides com.hazelcast.internal.serialization.SerializerHook with com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LongAccHook,
				com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.DoubleAccHook,
				com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.MutableReferenceHook,
				com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LinTrendAccHook,
				com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LongLongAccHook,
				com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.LongDoubleAccHook,
				com.hazelcast.jet.accumulator.AccumulatorSerializerHooks.PickAnyAccHook,
				com.hazelcast.jet.core.CoreSerializerHooks.WatermarkHook,
				com.hazelcast.jet.core.CoreSerializerHooks.JetEventHook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.WindowResultHook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.KeyedWindowResultHook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple2Hook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple3Hook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple4Hook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.Tuple5Hook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.TagHook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.ItemsByTagHook,
				com.hazelcast.jet.datamodel.DataModelSerializerHooks.TimestampedItemHook,
				com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.SnapshotBarrierHook,
				com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.BroadcastEntryHook,
				com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.BroadcastKeyHook,
				com.hazelcast.jet.impl.execution.ExecutionSerializerHooks.DoneItemHook,
				com.hazelcast.jet.impl.execution.init.CustomClassLoadedObject.Hook,
				com.hazelcast.jet.impl.metrics.JetMetricsDataSerializerHook,
				com.hazelcast.jet.impl.observer.JetObserverDataSerializerHook,
				com.hazelcast.jet.json.JsonSerializerHooks.DeferredMapHook
					;
	
	
	uses com.hazelcast.shaded.com.fasterxml.jackson.core.JsonFactory;
	provides com.hazelcast.shaded.com.fasterxml.jackson.core.JsonFactory with com.hazelcast.shaded.com.fasterxml.jackson.core.JsonFactory;
	
	
	uses com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
	provides com.hazelcast.spi.discovery.DiscoveryStrategyFactory with com.hazelcast.spi.discovery.multicast.MulticastDiscoveryStrategyFactory,
			com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory,
				com.hazelcast.gcp.GcpDiscoveryStrategyFactory,
				com.hazelcast.aws.AwsDiscoveryStrategyFactory,
				com.hazelcast.azure.AzureDiscoveryStrategyFactory
	;
	

	uses com.hazelcast.spi.impl.servicemanager.ServiceDescriptorProvider;
	provides com.hazelcast.spi.impl.servicemanager.ServiceDescriptorProvider with com.hazelcast.cp.internal.RaftServiceDescriptorProvider,
			com.hazelcast.cp.internal.datastructures.RaftDataServiceDescriptorProvider,
			com.hazelcast.internal.longregister.LongRegisterServiceDescriptorProvider;

	provides javax.cache.spi.CachingProvider with com.hazelcast.cache.HazelcastCachingProvider;

	//opens com.hazelcast.nio to com.hazelcast.hibernate;

	exports com.hazelcast.nio;
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

	exports com.hazelcast.jet.config;

	exports com.hazelcast.instance;
}
