module org.hibernate.orm.jcache {
	requires transitive org.hibernate.orm.core;

	requires transitive cache.api;

	opens org.hibernate.cache.jcache.internal to org.hibernate.orm.core;

	provides org.hibernate.boot.registry.selector.StrategyRegistrationProvider with org.hibernate.cache.jcache.internal.StrategyRegistrationProviderImpl;
}
