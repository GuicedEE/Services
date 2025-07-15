import com.google.inject.gee.*;

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
	exports com.google.inject.gee;

	requires java.logging;

	requires transitive com.google.common;

	requires static jakarta.inject;
	requires static jakarta.annotation;
	
	requires static jdk.unsupported;
	requires org.objectweb.asm;

	requires transitive aopalliance;

    requires static com.google.errorprone.annotations;
    requires static org.jetbrains.annotations;

    uses InjectionPointProvider;
	uses BindScopeProvider;
    uses ScopeAnnotationProvider;
    uses BindingAnnotationProvider;
	uses NamedAnnotationProvider;
    uses InjectorAnnotationsProvider;
}
