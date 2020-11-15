module jakarta.enterprise.cdi {
	requires transitive jakarta.enterprise.interceptor;

	requires transitive jakarta.el;

	requires transitive com.guicedee.guicedinjection;
	requires transitive com.google.guice.extensions.servlet;

	requires jakarta.annotation;

	exports jakarta.decorator;
	exports jakarta.enterprise.context;
	exports jakarta.enterprise.context.control;
	exports jakarta.enterprise.context.spi;

	exports jakarta.enterprise.event;
	exports jakarta.enterprise.inject;
	exports jakarta.enterprise.inject.literal;
	exports jakarta.enterprise.inject.se;
	exports jakarta.enterprise.inject.spi;
	exports jakarta.enterprise.util;

	exports com.guicedee.cdi.services;

	uses jakarta.enterprise.inject.spi.Extension;
	uses jakarta.enterprise.inject.se.SeContainerInitializer;
	uses jakarta.enterprise.inject.spi.CDIProvider;

	provides com.guicedee.guicedinjection.interfaces.IGuiceConfigurator with com.guicedee.cdi.implementations.GuicedCDIConfigurator;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.cdi.implementations.GuicedCDIModule;

}
