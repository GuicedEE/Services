module undertow.core {

	requires org.jboss.logging;

	requires java.security.jgss;

	requires static alpn.api;
	requires static io.undertow.parser.generator;
	requires jdk.unsupported;
	requires java.naming;
	requires static java.sql;
	requires java.compiler;
}
