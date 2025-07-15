module org.hibernate.orm.core {
	requires transitive java.sql;
	//requires com.guicedee.guicedinjection;
	
	requires java.naming;
	
	requires transitive jakarta.xml.bind;
	requires transitive jakarta.transaction;
	requires transitive jakarta.persistence;
	requires transitive jakarta.validation;
	//requires transitive org.hibernate.commons.annotations;
	requires transitive com.fasterxml.jackson.databind;

	
	requires org.jboss.logging;
	requires jandex;
	requires com.fasterxml.classmate;
//	requires org.apache.commons.compress;
//	requires javassist;
	requires static antlr;
	
	requires java.desktop;
	requires net.bytebuddy;

//	requires java.management;
//	requires dom4j;

	//requires java.compiler;

//	requires static jakarta.enterprise.cdi;
	//requires transitive jakarta.validation;
	requires transitive jakarta.inject;

//	requires jdk.unsupported;

//	requires java.instrument;

//	requires static ant;
//	requires jakarta.security.jacc.api;

	uses org.hibernate.boot.registry.selector.StrategyRegistrationProvider;
	uses org.hibernate.boot.registry.selector.spi.StrategyCreator;
	uses org.hibernate.boot.registry.selector.spi.StrategySelector;
	uses org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformProvider;

	//6.2
	//uses org.hibernate.boot.spi.MetadataBuilderContributor;
	//uses org.hibernate.boot.spi.AdditionalMappingContributor;
	//uses org.hibernate.query.criteria.spi.CriteriaBuilderExtension;

	exports org.hibernate.query.criteria;

	//6.4
	uses org.hibernate.bytecode.spi.BytecodeProvider;
	
	uses org.hibernate.boot.model.TypeContributor;
	uses org.hibernate.boot.model.IdGeneratorStrategyInterpreter;

	opens org.hibernate.internal to org.jboss.logging;
	opens org.hibernate.internal.log to org.jboss.logging;
	opens org.hibernate.resource.beans.internal to org.jboss.logging;
	opens org.hibernate.internal.util.xml to org.jboss.logging;
	opens org.hibernate.engine.jdbc.spi to org.jboss.logging;
	opens org.hibernate.cache.spi to org.jboss.logging;
	opens org.hibernate.bytecode to org.jboss.logging;

	//exports com.guicedee.services.hibernate to com.guicedee.guicedpersistence;
	
	exports org.hibernate.metamodel.mapping to org.jboss.logging,org.hibernate.reactive;
	exports org.hibernate.sql.ast.tree to org.jboss.logging;
	opens org.hibernate.sql.ast.tree to org.jboss.logging,org.hibernate.reactive;
	exports org.hibernate.sql.exec to org.jboss.logging,org.hibernate.reactive;
	opens org.hibernate.sql.exec to org.jboss.logging;
	exports org.hibernate.sql.results to org.jboss.logging,org.hibernate.reactive;
	opens org.hibernate.sql.results to org.jboss.logging;

	exports org.hibernate.bytecode.enhance.internal.tracker;

	exports org.hibernate.engine.jdbc.env.internal to org.jboss.logging,org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.batch to org.jboss.logging;
	
	exports org.hibernate.service.spi;
	exports org.hibernate.engine.jdbc.connections.internal;
	exports org.hibernate.internal.log;
	exports org.hibernate.internal.util.config;
	
	
	exports org.hibernate;
	//exports org.hibernate.action.internal;
	exports org.hibernate.action.spi;
	exports org.hibernate.query;
	//exports org.hibernate.internal;
	//exports org.hibernate.internal.build;
	//exports org.hibernate.internal.log;
	//exports org.hibernate.internal.util;
	//exports org.hibernate.internal.util.beans;
	//exports org.hibernate.internal.util.collections;
	//exports org.hibernate.internal.util.compare;
	//exports org.hibernate.internal.util.config;
	//exports org.hibernate.internal.util.io;
	//exports org.hibernate.internal.util.jndi;
	//exports org.hibernate.internal.util.type;
	//exports org.hibernate.internal.util.xml;
	exports org.hibernate.annotations;
	exports org.hibernate.boot;
	exports org.hibernate.resource.transaction.spi;

	//opens org.hibernate.jpa.boot.internal to com.fasterxml.jackson.databind;

	exports org.hibernate.bytecode.enhance.internal.bytebuddy;
	
	exports org.hibernate.jpa.boot.internal;
	//exports org.hibernate.jpa.boot.spi;
	//exports org.hibernate.boot.internal;
	//exports org.hibernate.boot.archive.internal;
	exports org.hibernate.boot.archive.scan.spi;
	//exports org.hibernate.boot.archive.scan.internal;
	exports org.hibernate.boot.archive.spi;
	//exports org.hibernate.boot.cfgxml.internal;
	exports org.hibernate.boot.cfgxml.spi;
	exports org.hibernate.boot.jaxb;
	//exports org.hibernate.boot.jaxb.internal;
	//exports org.hibernate.boot.jaxb.hbm.internal;
	exports org.hibernate.boot.jaxb.hbm.spi;
	exports org.hibernate.boot.jaxb.spi;
	exports org.hibernate.boot.model;
	//exports org.hibernate.boot.model.convert.internal;
	exports org.hibernate.boot.model.convert.spi;
	exports org.hibernate.boot.model.naming;
	//exports org.hibernate.boot.model.process.internal;
	exports org.hibernate.boot.model.process.spi;
	exports org.hibernate.boot.model.relational;
	//exports org.hibernate.boot.model.source.internal;
	//exports org.hibernate.boot.model.source.internal.annotations;
	exports org.hibernate.boot.model.source.internal.hbm;
	exports org.hibernate.boot.model.source.spi;

	exports org.hibernate.boot.registry;
	//exports org.hibernate.boot.registry.internal;
	//exports org.hibernate.boot.registry.classloading.internal;
	exports org.hibernate.boot.registry.classloading.spi;
	exports org.hibernate.boot.registry.selector;
	//exports org.hibernate.boot.registry.selector.internal;
	exports org.hibernate.boot.registry.selector.spi;
	exports org.hibernate.boot.spi;
	exports org.hibernate.boot.xsd;
	exports org.hibernate.bytecode;
	exports org.hibernate.bytecode.spi;
/*	exports org.hibernate.bytecode.internal.bytebuddy;
	exports org.hibernate.bytecode.internal.javassist;*/
	//exports org.hibernate.bytecode.enhance;
	exports org.hibernate.bytecode.enhance.spi;
	exports org.hibernate.bytecode.enhance.spi.interceptor;
	//exports org.hibernate.bytecode.enhance.internal;
/*
	exports org.hibernate.bytecode.enhance.internal.bytebuddy;
	exports org.hibernate.bytecode.enhance.internal.javassist;
	exports org.hibernate.bytecode.enhance.internal.tracker;
*/

	exports org.hibernate.cache;
	exports org.hibernate.cache.spi;
	exports org.hibernate.cache.spi.access;
	exports org.hibernate.cache.spi.entry;
	exports org.hibernate.cache.spi.support;
	//exports org.hibernate.cache.internal;
	//exports org.hibernate.cache.cfg;
	exports org.hibernate.cache.cfg.spi;
	//exports org.hibernate.cache.cfg.internal;

	exports org.hibernate.cfg;
	//exports org.hibernate.cfg.annotations;
	//exports org.hibernate.cfg.annotations.reflection;
	//exports org.hibernate.cfg.beanvalidation;
	//exports org.hibernate.classic;

	exports org.hibernate.collection.spi;
	//exports org.hibernate.collection.internal;
	exports org.hibernate.context;
	exports org.hibernate.context.spi;
	//exports org.hibernate.context.internal;

	//exports org.hibernate.criterion;
	exports org.hibernate.dialect;
	exports org.hibernate.dialect.function;
	exports org.hibernate.dialect.hint;
	exports org.hibernate.dialect.identity;
	exports org.hibernate.dialect.lock;
	exports org.hibernate.dialect.pagination;
	exports org.hibernate.dialect.unique;

//	exports org.hibernate.ejb;
	exports org.hibernate.engine;
	exports org.hibernate.engine.spi;
	//exports org.hibernate.engine.internal;
	exports org.hibernate.engine.config.spi;
	//exports org.hibernate.engine.config.internal;
	exports org.hibernate.engine.jdbc;
	exports org.hibernate.engine.jdbc.spi;
	//exports org.hibernate.engine.jdbc.internal;
	exports org.hibernate.engine.jdbc.batch.spi;
	//	exports org.hibernate.engine.jdbc.batch.internal;
	exports org.hibernate.engine.jdbc.connections.spi;
	//exports org.hibernate.engine.jdbc.connections.internal;
	exports org.hibernate.engine.jdbc.cursor.spi;
	//	exports org.hibernate.engine.jdbc.cursor.internal;
	exports org.hibernate.engine.jdbc.dialect.spi;
	//	exports org.hibernate.engine.jdbc.dialect.internal;
	exports org.hibernate.engine.jdbc.env.spi;
	//	exports org.hibernate.engine.jdbc.env.internal;
	exports org.hibernate.engine.jndi.spi;
	//	exports org.hibernate.engine.jndi.internal;
	//	exports org.hibernate.engine.loading.internal;
	exports org.hibernate.engine.profile;
	exports org.hibernate.engine.query.spi;
	//	exports org.hibernate.engine.query.internal;
	exports org.hibernate.engine.transaction.spi;
	//	exports org.hibernate.engine.transaction.internal.jta;
	exports org.hibernate.engine.transaction.jta.platform.spi;
	exports org.hibernate.engine.transaction.jta.platform.internal;
	exports org.hibernate.event.spi;
	//	exports org.hibernate.event.internal;
	exports org.hibernate.event.service.spi;
	//	exports org.hibernate.event.service.internal;
	exports org.hibernate.exception;
	exports org.hibernate.exception.spi;
	//	exports org.hibernate.exception.internal;
	exports org.hibernate.graph.spi;
	//exports org.hibernate.hql.spi;
	//	exports org.hibernate.hql.internal;

	//exports org.hibernate.hql.internal.classic;
	//exports org.hibernate.hql.internal.ast.exec;
	//exports org.hibernate.hql.internal.ast.tree;
	//exports org.hibernate.hql.internal.ast.util;
	exports org.hibernate.id;
	exports org.hibernate.id.enhanced;
	//exports org.hibernate.id.factory;
	//exports org.hibernate.id.factory.spi;
	//exports org.hibernate.id.factory.internal;
	exports org.hibernate.id.insert;
	exports org.hibernate.id.uuid;
	exports org.hibernate.integrator.spi;
	//exports org.hibernate.integrator.internal;
	exports org.hibernate.jdbc;
//	exports org.hibernate.jmx.spi;
	//exports org.hibernate.jmx.internal;
	exports org.hibernate.jpa;
	//exports org.hibernate.jpa.graph.internal;
	exports org.hibernate.jpa.event.spi;
	//exports org.hibernate.jpa.event.internal;
	exports org.hibernate.jpa.spi;
	//exports org.hibernate.jpa.internal;
	//exports org.hibernate.jpa.internal.enhance;
	//exports org.hibernate.jpa.internal.util;
	exports org.hibernate.jpa.boot.spi;
	//	exports org.hibernate.jpa.boot.internal;

	//exports org.hibernate.loader;
	//exports org.hibernate.loader.hql;
	//exports org.hibernate.loader.spi;
	//exports org.hibernate.loader.internal;
	//	exports org.hibernate.loader.collection;
	//exports org.hibernate.loader.collection.plan;
	//exports org.hibernate.loader.criteria;
	//exports org.hibernate.loader.custom;
	//exports org.hibernate.loader.custom.sql;
	//exports org.hibernate.loader.entity;
	//exports org.hibernate.loader.entity.plan;
//	exports org.hibernate.loader.plan.spi;
	//exports org.hibernate.loader.plan.build.spi;
	//exports org.hibernate.loader.plan.build.internal;
	//exports org.hibernate.loader.plan.build.internal.returns;
	//exports org.hibernate.loader.plan.build.internal.spaces;
	//exports org.hibernate.loader.plan.exec.query.spi;
	//exports org.hibernate.loader.plan.exec.query.internal;
	//exports org.hibernate.loader.plan.exec.spi;
	//exports org.hibernate.loader.plan.exec.internal;
	//exports org.hibernate.loader.plan.exec.process.spi;
	//exports org.hibernate.loader.plan.exec.process.internal;

//	exports org.hibernate.lob;
	opens org.hibernate.mapping;
	//exports org.hibernate.metadata;
//	exports org.hibernate.metamodel.spi;
	//exports org.hibernate.metamodel.internal;
	//exports org.hibernate.metamodel.model.convert.spi;
	//exports org.hibernate.metamodel.model.convert.internal;
	exports org.hibernate.metamodel.model.domain;
	//exports org.hibernate.param;
	exports org.hibernate.persister.spi;
	//exports org.hibernate.persister.internal;
	exports org.hibernate.persister.collection;
	exports org.hibernate.persister.entity;
//	exports org.hibernate.persister.walking.spi;
	//exports org.hibernate.persister.walking.internal;

	exports org.hibernate.proxy;
	exports org.hibernate.pretty;
	exports org.hibernate.procedure;
//	exports org.hibernate.procedure.spi;
	//exports org.hibernate.procedure.internal;
//	exports org.hibernate.property.access.spi;
	//exports org.hibernate.property.access.internal;
/*
	exports org.hibernate.proxy.map;
	exports org.hibernate.proxy.pojo;
	exports org.hibernate.proxy.pojo.bytebuddy;
	exports org.hibernate.proxy.pojo.javassist;
	exports org.hibernate.resource.transaction;*/
//	exports org.hibernate.resource.transaction.spi;
	//exports org.hibernate.resource.transaction.internal;
	//exports org.hibernate.resource.transaction.backend.jdbc.spi;
	//exports org.hibernate.resource.transaction.backend.jdbc.internal;
	//exports org.hibernate.resource.transaction.backend.jta.internal.synchronization;
	//	exports org.hibernate.resource.transaction.backend.jta.internal;
	//exports org.hibernate.resource.jdbc.internal;
//	exports org.hibernate.resource.jdbc.spi;
	exports org.hibernate.resource.jdbc;
	exports org.hibernate.result;
	//exports org.hibernate.result.internal;
	//exports org.hibernate.result.spi;
	//exports org.hibernate.secure.internal;
	//exports org.hibernate.secure.spi;
	exports org.hibernate.sql;
	//exports org.hibernate.sql.ordering.antlr;
	exports org.hibernate.stat;
	//	exports org.hibernate.stat.internal;
/*	exports org.hibernate.stat.spi;
	exports org.hibernate.tool.enhance;
	exports org.hibernate.tool.hbm2ddl;
	exports org.hibernate.tool.instrument.javassist;*/
	//exports org.hibernate.tool.schema.internal.exec;
	//exports org.hibernate.tool.schema.internal;
/*	exports org.hibernate.tool.schema;
	exports org.hibernate.tool.schema.spi;
	exports org.hibernate.tool.schema.extract.internal;
	exports org.hibernate.tool.schema.extract.spi;*/
	exports org.hibernate.transform;
/*	exports org.hibernate.tuple;
	exports org.hibernate.tuple.component;
	exports org.hibernate.tuple.entity;*/
	exports org.hibernate.type;
	//	exports org.hibernate.type.internal;
//	exports org.hibernate.type.spi;
	exports org.hibernate.type.descriptor;
	//exports org.hibernate.type.descriptor.spi;
	exports org.hibernate.type.descriptor.converter;
	exports org.hibernate.type.descriptor.java;
//	exports org.hibernate.type.descriptor.java.spi;
	exports org.hibernate.type.descriptor.sql;
	//exports org.hibernate.type.descriptor.sql.spi;
	exports org.hibernate.usertype;
	
	exports org.hibernate.boot.archive.internal;

	opens org.hibernate;
	opens org.hibernate.jpa;
	opens org.hibernate.xsd.cfg;
	opens org.hibernate.xsd.mapping;
	opens org.hibernate.boot.jaxb.configuration.spi;

	//opens org.hibernate.jpa.boot.internal to com.fasterxml.jackson.databind;

//	exports org.hibernate.internal.util.config to com.hazelcast.all, com.hazelcast.hibernate;

	//exports org.hibernate.cache.internal;
	//exports org.hibernate.internal.util;

	//provides com.guicedee.guicedinjection.interfaces.IFileContentsScanner with org.hibernate.boot.archive.internal.PersistenceFileHandler;
	//provides com.guicedee.guicedinjection.interfaces.IGuiceConfigurator with org.hibernate.boot.archive.internal.GuicedConfigurator;
	//provides com.guicedee.guicedinjection.interfaces.IPathContentsRejectListScanner with com.guicedee.services.hibernate.GuiceInjectionMetaInfScannerExclusions;
	//provides com.guicedee.guicedinjection.interfaces.IPathContentsScanner with com.guicedee.services.hibernate.GuiceInjectionMetaInfScanner;

	//6.0.1.Final
	uses org.hibernate.event.spi.EventEngineContributor;
	uses org.hibernate.integrator.spi.Integrator;
	uses org.hibernate.boot.registry.selector.spi.DialectSelector;
	uses org.hibernate.service.spi.ServiceContributor;
	//uses org.hibernate.id.factory.spi.GenerationTypeStrategyRegistration;
	uses org.hibernate.boot.spi.MetadataSourcesContributor;
	uses org.hibernate.boot.spi.MetadataBuilderInitializer;
	uses org.hibernate.boot.spi.MetadataBuilderFactory;
	uses org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
	uses org.hibernate.boot.spi.MetadataContributor;
	uses org.hibernate.boot.spi.AdditionalJaxbMappingProducer;
	uses org.hibernate.boot.spi.SessionFactoryBuilderFactory;
	uses org.hibernate.service.spi.SessionFactoryServiceContributor;
	uses org.hibernate.boot.model.FunctionContributor;
	
	uses org.hibernate.boot.spi.MetadataBuilderContributor;
	uses org.hibernate.boot.spi.AdditionalMappingContributor;
	uses org.hibernate.boot.spi.CriteriaBuilderExtension;
	uses org.hibernate.event.spi.EventManager;
	
	uses org.hibernate.query.criteria.spi.CriteriaBuilderExtension;

	provides jakarta.persistence.spi.PersistenceProvider with org.hibernate.jpa.HibernatePersistenceProvider;
	provides org.hibernate.bytecode.spi.BytecodeProvider with org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;


	opens org.hibernate.cache.spi.entry;
//	opens org.hibernate.query.criteria.internal.path;
	exports org.hibernate.cache.internal;
	exports org.hibernate.internal.util;

	exports org.hibernate.jpa.internal.util to org.hibernate.reactive;
	exports org.hibernate.boot.cfgxml.internal to org.hibernate.reactive;
	exports org.hibernate.bytecode.internal to org.hibernate.reactive;
	exports org.hibernate.engine.config.internal to org.hibernate.reactive;
	exports org.hibernate.property.access.internal to org.hibernate.reactive;
	exports org.hibernate.tool.schema.internal.script to org.hibernate.reactive;
	exports org.hibernate.engine.jndi.internal to org.hibernate.reactive;
	exports org.hibernate.persister.internal to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.dialect.internal to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.batch.internal to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.internal to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.cursor.internal to org.hibernate.reactive;
	exports org.hibernate.service.internal to org.hibernate.reactive;
	exports org.hibernate.resource.transaction.internal to org.hibernate.reactive;
	exports org.hibernate.event.internal to org.hibernate.reactive;

	exports org.hibernate.service to org.hibernate.reactive;
	exports org.hibernate.query.named to org.hibernate.reactive;
	exports org.hibernate.internal to org.hibernate.reactive,org.jboss.logging;
	exports org.hibernate.type.descriptor.jdbc.internal to org.hibernate.reactive;
	exports org.hibernate.action.internal to org.hibernate.reactive;
	exports org.hibernate.boot.internal to org.hibernate.reactive;
	exports org.hibernate.type.descriptor.jdbc.spi to org.hibernate.reactive;
	exports org.hibernate.type.descriptor.jdbc to org.hibernate.reactive;
	exports org.hibernate.type.descriptor.java.spi to org.hibernate.reactive;
	exports org.hibernate.type.descriptor.sql.spi to org.hibernate.reactive;
	exports org.hibernate.type.spi to org.hibernate.reactive;
	exports org.hibernate.loader.ast.spi to org.hibernate.reactive;
	exports org.hibernate.sql.ast.spi to org.hibernate.reactive;
	exports org.hibernate.query.sqm.mutation.spi to org.hibernate.reactive;
	exports org.hibernate.sql.results.jdbc.spi to org.hibernate.reactive;
	exports org.hibernate.resource.beans.spi to org.hibernate.reactive;
	exports org.hibernate.tool.schema.spi to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.mutation.spi to org.hibernate.reactive;


	exports org.hibernate.query.spi to org.hibernate.reactive;
	exports org.hibernate.engine.internal to org.hibernate.reactive;
	opens org.hibernate.engine.internal to org.hibernate.reactive;
	exports org.hibernate.query.sqm.internal to org.hibernate.reactive;

	exports org.hibernate.generator to org.hibernate.reactive;
	exports org.hibernate.sql.model to org.hibernate.reactive;
	exports org.hibernate.metamodel.spi to org.hibernate.reactive;

	exports org.hibernate.metamodel.mapping.internal to org.hibernate.reactive;
	exports org.hibernate.persister.collection.mutation to org.hibernate.reactive;
	exports org.hibernate.persister.entity.mutation to org.hibernate.reactive;
	exports org.hibernate.sql.model.ast.builder to org.hibernate.reactive;
	exports org.hibernate.sql.model.ast to org.hibernate.reactive;
	exports org.hibernate.sql.model.jdbc to org.hibernate.reactive;
	exports org.hibernate.sql.model.internal to org.hibernate.reactive;
	exports org.hibernate.loader.ast.internal to org.hibernate.reactive;
	exports org.hibernate.sql.exec.spi to org.hibernate.reactive;
	exports org.hibernate.sql.ast.tree.expression to org.hibernate.reactive;
	exports org.hibernate.sql.results.graph.entity.internal to org.hibernate.reactive;
	exports org.hibernate.sql.results.graph.embeddable.internal to org.hibernate.reactive;
	exports org.hibernate.sql.results.graph to org.hibernate.reactive;
	exports org.hibernate.sql.results.jdbc.internal to org.hibernate.reactive;
	exports org.hibernate.sql.ast.tree.select to org.hibernate.reactive;
	exports org.hibernate.sql.exec.internal to org.hibernate.reactive;
	exports org.hibernate.sql.results.internal.domain to org.hibernate.reactive;
	exports org.hibernate.sql.results.graph.collection.internal to org.hibernate.reactive;
	exports org.hibernate.query.sqm.tree to org.hibernate.reactive;
	exports org.hibernate.sql.results.internal to org.hibernate.reactive;
	exports org.hibernate.query.sqm.tree.select to org.hibernate.reactive;
	exports org.hibernate.query.hql.internal to org.hibernate.reactive;
	exports org.hibernate.query.sqm.sql to org.hibernate.reactive;
	exports org.hibernate.sql.ast to org.hibernate.reactive;
	exports org.hibernate.spi to org.hibernate.reactive;
	exports org.hibernate.query.sqm.spi to org.hibernate.reactive;
	exports org.hibernate.sql.ast.tree.from to org.hibernate.reactive;
	exports org.hibernate.sql.results.spi to org.hibernate.reactive;
	exports org.hibernate.stat.spi to org.hibernate.reactive;
	exports org.hibernate.resource.jdbc.spi to org.hibernate.reactive;
	exports org.hibernate.tool.schema.internal to org.hibernate.reactive;
	exports org.hibernate.tool.schema.internal.exec to org.hibernate.reactive;
	exports org.hibernate.tool.schema.extract.spi to org.hibernate.reactive;
	exports org.hibernate.tool.schema.extract.internal to org.hibernate.reactive;
	exports org.hibernate.generator.values to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.mutation to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.mutation.internal to org.hibernate.reactive;
	exports org.hibernate.engine.jdbc.mutation.group to org.hibernate.reactive;
	exports org.hibernate.query.sql.internal to org.hibernate.reactive;


	//erg ugly
	opens org.hibernate.tuple;
	opens org.hibernate.tuple.component;
	opens org.hibernate.tuple.entity;

	opens org.hibernate.dialect to org.jboss.logging;
	opens org.hibernate.engine.jdbc.env.internal to org.jboss.logging;
	opens org.hibernate.query to org.jboss.logging;
	opens org.hibernate.engine.jdbc.batch to org.jboss.logging;
	opens org.hibernate.bytecode.enhance.spi.interceptor to org.jboss.logging;
	opens org.hibernate.metamodel.mapping to org.jboss.logging;
	opens org.hibernate.query.hql to org.jboss.logging;

	//hibernate 7
	uses org.hibernate.boot.registry.selector.spi.NamedStrategyContributor;
	uses org.hibernate.resource.beans.container.spi.BeanContainer;
	uses org.hibernate.models.spi.ModelsContextProvider;
	uses org.hibernate.boot.archive.scan.spi.ScannerFactory;

	provides org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformProvider with org.hibernate.service.jta.platform.internal.BitronixJtaPlatform;
	uses org.hibernate.event.monitor.spi.EventMonitor;
}


