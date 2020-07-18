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
}
