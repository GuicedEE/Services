module com.google.guice {
	exports com.google.inject;
	
	requires transitive java.logging;
	
	requires transitive com.google.common;
	requires transitive aopalliance;
	requires transitive jakarta.inject;
	requires transitive jakarta.annotation;
	
	requires jdk.unsupported;
	requires org.objectweb.asm;
}
