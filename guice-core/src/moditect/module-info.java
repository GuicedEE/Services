module com.google.guice {
	exports com.google.inject;
	exports com.google.inject.util;
	exports com.google.inject.matcher;
	exports com.google.inject.name;
	exports com.google.inject.binder;
	exports com.google.inject.spi;
	exports com.google.inject.multibindings;

	exports com.google.inject.internal;
	exports com.google.inject.internal.util;

	requires transitive java.logging;

	requires transitive com.google.common;
	requires transitive jakarta.inject;
	requires transitive aopalliance;

	requires jakarta.annotation;
}
