module org.hibernate.reactive {
    requires transitive org.hibernate.orm.core;
    requires transitive io.vertx.core;
    requires transitive io.vertx.sql.client;

    requires org.jboss.logging;

    requires transitive io.smallrye.mutiny;

    requires net.bytebuddy;
    requires org.apache.logging.log4j.core;

    exports org.hibernate.reactive.mutiny;
    exports org.hibernate.reactive.mutiny.impl;

    //exports org.hibernate.reactive.adapter.impl;
    exports org.hibernate.reactive.boot.spi;
    exports org.hibernate.reactive.common;
    exports org.hibernate.reactive.common.spi;
    exports org.hibernate.reactive.context;
    exports org.hibernate.reactive.context.impl;
    //exports org.hibernate.reactive.dialect;
    exports org.hibernate.reactive.engine;
    exports org.hibernate.reactive.engine.impl;
    exports org.hibernate.reactive.engine.jdbc;
    exports org.hibernate.reactive.engine.spi;
    exports org.hibernate.reactive.event;
    exports org.hibernate.reactive.event.impl;
    exports org.hibernate.reactive.generator.values;
    exports org.hibernate.reactive.id;
    exports org.hibernate.reactive.id.enhanced;
    //exports org.hibernate.reactive.id.factory.spi;
    exports org.hibernate.reactive.id.impl;
    exports org.hibernate.reactive.id.insert;
    exports org.hibernate.reactive.loader.ast.internal;
    exports org.hibernate.reactive.loader.ast.spi;
    //exports org.hibernate.reactive.loader.entity;
    exports org.hibernate.reactive.logging.impl;
    exports org.hibernate.reactive.metamodel.mapping.internal;

    //exports org.hibernate.reactive.persister.collection;
    exports org.hibernate.reactive.persister.collection.impl;
    exports org.hibernate.reactive.persister.collection.mutation;
    //exports org.hibernate.reactive.persister.entity;
    exports org.hibernate.reactive.pool;
    exports org.hibernate.reactive.pool.impl;
    exports org.hibernate.reactive.provider;
    exports org.hibernate.reactive.provider.impl;
    exports org.hibernate.reactive.provider.service;
    exports org.hibernate.reactive.query;
    exports org.hibernate.reactive.query.internal;
    exports org.hibernate.reactive.query.spi;
    //exports org.hibernate.reactive.query.sql;
    exports org.hibernate.reactive.query.sql.internal;
    exports org.hibernate.reactive.query.sql.spi;
    exports org.hibernate.reactive.query.sqm;
    exports org.hibernate.reactive.query.sqm.internal;
    exports org.hibernate.reactive.query.sqm.mutation.internal;
    exports org.hibernate.reactive.query.sqm.mutation.internal.cte;
    exports org.hibernate.reactive.query.sqm.mutation.internal.temptable;
    exports org.hibernate.reactive.query.sqm.mutation.spi;
    exports org.hibernate.reactive.query.sqm.spi;
    exports org.hibernate.reactive.service.internal;
    exports org.hibernate.reactive.session;
    exports org.hibernate.reactive.session.impl;
    exports org.hibernate.reactive.sql.exec.internal;
    exports org.hibernate.reactive.sql.exec.spi;
    exports org.hibernate.reactive.sql.model;
    exports org.hibernate.reactive.sql.results;
    exports org.hibernate.reactive.sql.results.graph;
    exports org.hibernate.reactive.sql.results.internal;
    exports org.hibernate.reactive.sql.results.spi;
    exports org.hibernate.reactive.stage;
    exports org.hibernate.reactive.stage.impl;
    exports org.hibernate.reactive.tuple;
    exports org.hibernate.reactive.type.descriptor.jdbc;
    exports org.hibernate.reactive.util.async.impl;
    exports org.hibernate.reactive.util.impl;
    exports org.hibernate.reactive.vertx;
    exports org.hibernate.reactive.vertx.impl;
    exports org.hibernate.reactive.shaded.hibernate;

    exports org.hibernate.reactive.persister.entity.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;

    //opens org.hibernate.reactive.adapter.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection;

    opens org.hibernate.reactive.mutiny.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;

    opens org.hibernate.reactive.boot.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.common to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.common.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.context to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.context.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    //opens org.hibernate.reactive.dialect to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.engine to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.engine.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.engine.jdbc to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.engine.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.event to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.event.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.generator.values to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.id to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.id.enhanced to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    //opens org.hibernate.reactive.id.factory.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.id.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.id.insert to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.loader.ast.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.loader.ast.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    //opens org.hibernate.reactive.loader.entity to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.logging.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.metamodel.mapping.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;

   // opens org.hibernate.reactive.persister.collection to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.persister.collection.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.persister.collection.mutation to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
   // opens org.hibernate.reactive.persister.entity to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.pool to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.pool.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.provider to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.provider.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.provider.service to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    //opens org.hibernate.reactive.query.sql to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sql.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sql.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sqm to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sqm.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sqm.mutation.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sqm.mutation.internal.cte to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sqm.mutation.internal.temptable to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sqm.mutation.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.query.sqm.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.service.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.session to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.session.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.sql.exec.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.sql.exec.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.sql.model to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.sql.results to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.sql.results.graph to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.sql.results.internal to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.sql.results.spi to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.stage to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.stage.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.tuple to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.type.descriptor.jdbc to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.util.async.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.util.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.vertx to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.vertx.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    opens org.hibernate.reactive.shaded.hibernate to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;

 //   provides io.vertx.core.spi.VertxServiceProvider with org.hibernate.reactive.context.impl.ContextualDataStorage;
    provides jakarta.persistence.spi.PersistenceProvider with org.hibernate.reactive.provider.ReactivePersistenceProvider;
    provides org.hibernate.boot.model.TypeContributor with org.hibernate.reactive.provider.impl.ReactiveTypeContributor;
    provides org.hibernate.engine.jdbc.dialect.spi.DialectResolver with org.hibernate.reactive.engine.jdbc.dialect.internal.ReactiveStandardDialectResolver;
    provides org.hibernate.integrator.spi.Integrator with org.hibernate.reactive.provider.impl.ReactiveIntegrator;
    provides org.hibernate.service.spi.SessionFactoryServiceContributor with org.hibernate.reactive.service.internal.ReactiveSessionFactoryServiceContributor;

    uses io.vertx.sqlclient.spi.Driver;

    //Hibernate Reactive 4.0.0.Beta1
    //opens org.hibernate.reactive.vertx.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;
    //opens org.hibernate.reactive.logging.impl to org.hibernate.orm.core, io.vertx.core, com.guicedee.guicedinjection,io.vertx.codegen.api,net.bytebuddy,io.smallrye.mutiny,org.jboss.logging;

}


//java.lang.IllegalArgumentException: This library does not have private access to interface org.hibernate.reactive.logging.impl.Log