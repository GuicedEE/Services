module com.hazelcast.hibernate {
	requires transitive java.logging;
	requires com.hazelcast.all;

	requires transitive cache.api;
	requires java.transaction.xa;
	requires java.management;

	requires transitive java.xml;
	requires static org.hibernate.orm.core;
	
	requires jdk.unsupported;

	exports com.hazelcast.hibernate to org.hibernate.orm.core;

	provides com.hazelcast.nio.serialization.SerializerHook with com.hazelcast.hibernate.serialization.Hibernate5CacheEntrySerializerHook
			;
	provides com.hazelcast.internal.serialization.DataSerializerHook with com.hazelcast.hibernate.serialization.HibernateDataSerializerHook
			;

	opens com.hazelcast.hibernate.serialization;

}
