module org.hibernate.orm.c3p0 {
    exports org.hibernate.c3p0.internal;
    exports com.mchange.v2.c3p0;

    requires java.sql;
    requires java.naming;
    requires java.desktop;
	
	requires org.slf4j;
	
	requires java.management;

    requires transitive org.hibernate.orm.core;
    requires org.jboss.logging;

    provides org.hibernate.boot.registry.selector.StrategyRegistrationProvider with org.hibernate.c3p0.internal.StrategyRegistrationProviderImpl;
}