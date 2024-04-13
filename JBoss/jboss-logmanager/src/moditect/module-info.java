module org.jboss.logging {
	
	exports org.jboss.logging.processor.apt;
	exports org.jboss.logging.processor.apt.report;
	exports org.jboss.logging.processor.model;
	
	requires transitive java.logging;
	requires java.xml;
	requires java.desktop;
	//requires jakarta.json;
	//for jdeparser in jboss logging
	requires jdk.unsupported;
	
	requires static org.apache.logging.log4j.core;
	
	requires java.compiler;
	
	exports org.jboss.logmanager;
	//exports org.jboss.logmanager.config;
	exports org.jboss.logmanager.errormanager;
	exports org.jboss.logmanager.filters;
	exports org.jboss.logmanager.formatters;
	exports org.jboss.logmanager.handlers;
	exports org.jboss.logging;
	exports org.jboss.logging.annotations;
	
	//provides java.util.logging.LogManager with org.jboss.logmanager.LogManager;
	provides javax.annotation.processing.Processor with org.jboss.logging.processor.apt.LoggingToolsProcessor;
	
	uses org.jboss.logging.LoggerProvider;
}
