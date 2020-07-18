module com.google.guice {
	requires com.google.common;
	requires javax.inject;
	requires transitive java.logging;
	requires static org.apache.commons.lang3;

	requires aopalliance;
	requires java.xml;

	requires static org.objectweb.asm;
}
