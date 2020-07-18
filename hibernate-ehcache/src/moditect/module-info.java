module org.hibernate.orm.ehcache {

    exports org.hibernate.cache.ehcache;
    exports org.hibernate.cache.ehcache.internal;


    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.jboss.logging;

    requires org.ehcache;

    provides org.hibernate.boot.registry.selector.StrategyRegistrationProvider with org.hibernate.cache.ehcache.internal.StrategyRegistrationProviderImpl;
}