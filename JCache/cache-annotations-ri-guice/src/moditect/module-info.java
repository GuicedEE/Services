module cache.annotations.ri.guice {
	requires java.logging;

	requires transitive cache.annotations.ri.common;
	
	requires com.guicedee.client;

	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with org.jsr107.ri.annotations.guice.module.CacheAnnotationsModule;
	
	exports org.jsr107.ri.annotations.guice.module;

	opens org.jsr107.ri.annotations.guice.module to com.google.guice;
	opens org.jsr107.ri.annotations.guice to com.google.guice;
	exports org.jsr107.ri.annotations.guice;
}