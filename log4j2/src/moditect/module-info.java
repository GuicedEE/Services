module org.apache.logging.log4j.core {
	//annotation processing
	requires java.compiler;
	requires java.desktop;
	requires java.management;
	requires static java.sql;
	requires java.naming;
	requires java.rmi;
	requires jdk.unsupported;
	requires java.scripting;
	
	requires static com.fasterxml.jackson.core;
	requires static com.fasterxml.jackson.databind;
	requires static jakarta.mail;
	requires static com.fasterxml.jackson.dataformat.xml;

	requires static jakarta.activation;
	requires static org.apache.commons.compress;
	requires static org.apache.commons.csv;
	requires static org.codehaus.stax2;
//	requires static org.slf4j;

	exports org.apache.logging.log4j.core;
	exports org.apache.logging.log4j.core.async;
	exports org.apache.logging.log4j.core.appender;
	exports org.apache.logging.log4j.core.config;
	exports org.apache.logging.log4j.core.config.json;
	exports org.apache.logging.log4j.core.config.plugins;
	exports org.apache.logging.log4j.core.config.plugins.convert;
	exports org.apache.logging.log4j.core.config.plugins.processor;
	exports org.apache.logging.log4j.core.config.plugins.util;
	exports org.apache.logging.log4j.core.config.plugins.validation;
	exports org.apache.logging.log4j.core.config.plugins.validation.constraints;
	exports org.apache.logging.log4j.core.config.plugins.validation.validators;
	exports org.apache.logging.log4j.core.config.plugins.visitors;
	exports org.apache.logging.log4j.core.config.properties;
	exports org.apache.logging.log4j.core.config.status;
	exports org.apache.logging.log4j.core.config.xml;
	exports org.apache.logging.log4j.core.config.yaml;
	exports org.apache.logging.log4j.core.filter;
	exports org.apache.logging.log4j.core.impl;
	exports org.apache.logging.log4j.core.jackson;
	exports org.apache.logging.log4j.core.layout;
	exports org.apache.logging.log4j.core.layout.internal;
	exports org.apache.logging.log4j.core.lookup;
	exports org.apache.logging.log4j.core.message;
	exports org.apache.logging.log4j.core.net;
	exports org.apache.logging.log4j.core.net.ssl;
	exports org.apache.logging.log4j.core.parser;
	exports org.apache.logging.log4j.core.pattern;
	exports org.apache.logging.log4j.core.script;
	exports org.apache.logging.log4j.core.selector;
	exports org.apache.logging.log4j.core.time;
	exports org.apache.logging.log4j.core.tools;
	exports org.apache.logging.log4j.core.util;
	exports org.apache.logging.log4j.spi;
	exports org.apache.logging.log4j.util;
	exports org.apache.logging.log4j.message;
	exports org.apache.logging.log4j.status;
	
	exports org.apache.logging.log4j.core.config.builder.api;
	exports org.apache.logging.log4j.core.config.builder.impl;

	exports org.apache.logging.log4j;
	
//	exports org.apache.logging.log4j.jul;
//	exports org.apache.logging.log4j.jpl;
	
	//provides java.util.logging.LogManager with org.apache.logging.log4j.LogManager;
	//provides java.lang.System.LoggerFinder with org.apache.logging.log4j.jpl.Log4jSystemLoggerFinder;
	
	provides javax.annotation.processing.Processor with org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor;
	provides org.apache.logging.log4j.spi.Provider with org.apache.logging.log4j.core.impl.Log4jProvider;
	
	
	provides org.apache.logging.log4j.core.util.ContextDataProvider with org.apache.logging.log4j.core.impl.ThreadContextDataProvider;
	provides org.apache.logging.log4j.message.ThreadDumpMessage.ThreadInfoFactory with org.apache.logging.log4j.core.message.ExtendedThreadInfoFactory;
	provides org.apache.logging.log4j.util.PropertySource with org.apache.logging.log4j.util.EnvironmentPropertySource,org.apache.logging.log4j.util.SystemPropertiesPropertySource;

	uses org.apache.logging.log4j.core.util.WatchEventService;
	uses org.apache.logging.log4j.util.PropertySource;
	uses org.apache.logging.log4j.message.ThreadDumpMessage.ThreadInfoFactory;
	uses org.apache.logging.log4j.core.util.ContextDataProvider;
	
	uses org.apache.logging.log4j.spi.Provider;
}
