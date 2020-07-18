module jakarta.enterprise.cdi {
	requires transitive jakarta.enterprise.interceptor;

	requires transitive javax.el;

	requires transitive com.guicedee.guicedinjection;
	requires transitive com.google.guice.extensions.servlet;

	requires java.annotation;

	exports javax.decorator;
	exports javax.enterprise.context;
	exports javax.enterprise.context.control;
	exports javax.enterprise.context.spi;

	exports javax.enterprise.event;
	exports javax.enterprise.inject;
	exports javax.enterprise.inject.literal;
	exports javax.enterprise.inject.se;
	exports javax.enterprise.inject.spi;
	exports javax.enterprise.util;

	exports com.guicedee.cdi.services;

	uses javax.enterprise.inject.spi.Extension;
	uses javax.enterprise.inject.se.SeContainerInitializer;
	uses javax.enterprise.inject.spi.CDIProvider;

	provides com.guicedee.guicedinjection.interfaces.IGuiceConfigurator with com.guicedee.cdi.implementations.GuicedCDIConfigurator;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.cdi.implementations.GuicedCDIModule;

}
