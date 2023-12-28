module org.hibernate.orm.c3p0 {
    exports org.hibernate.c3p0.internal;

    requires java.sql;

    requires transitive org.hibernate.orm.core;
    requires org.jboss.logging;

    provides org.hibernate.boot.registry.selector.StrategyRegistrationProvider with org.hibernate.c3p0.internal.StrategyRegistrationProviderImpl;
}