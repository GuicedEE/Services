module org.apache.logging.log4j.core {
	//annotation processing
	requires java.compiler;
	requires java.desktop;
	requires static java.scripting;
	requires java.management;
	requires static java.sql;
	requires java.naming;
	requires java.rmi;
	requires jdk.unsupported;
	requires static com.fasterxml.jackson.core;
	requires static com.fasterxml.jackson.databind;
	requires static jakarta.mail;
	requires static com.fasterxml.jackson.dataformat.xml;

	requires static jakarta.activation;
	requires static org.apache.commons.compress;
	requires static org.apache.commons.csv;
	requires static org.codehaus.stax2;

	exports org.apache.logging.log4j.core;
	exports org.apache.logging.log4j.core.appender;
	exports org.apache.logging.log4j.spi;
	exports org.apache.logging.log4j.util;
	exports org.apache.logging.log4j.message;
	exports org.apache.logging.log4j.status;

	exports org.apache.logging.log4j;
	exports org.apache.logging.log4j.jul;

	provides javax.annotation.processing.Processor with org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor;
	provides org.apache.logging.log4j.spi.Provider with org.apache.logging.log4j.core.impl.Log4jProvider;
	provides java.lang.System.LoggerFinder with org.apache.logging.log4j.jpl.Log4jSystemLoggerFinder;

	uses org.apache.logging.log4j.spi.Provider;
}
