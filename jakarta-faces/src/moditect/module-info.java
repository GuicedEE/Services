open module jakarta.faces {

	exports jakarta.faces.model;
	exports jakarta.faces.annotation;
	exports jakarta.faces.bean;
	exports jakarta.faces.application;
	exports jakarta.faces.component;
	exports jakarta.faces.component.behavior;
	exports jakarta.faces.component.html;
	exports jakarta.faces.component.search;
	exports jakarta.faces.component.visit;
	exports jakarta.faces.context;
	exports jakarta.faces.convert;
	exports jakarta.faces.el;
	exports jakarta.faces.event;
	exports jakarta.faces.flow;
	exports jakarta.faces.lifecycle;

	exports jakarta.faces.push;
	exports jakarta.faces.render;
	exports jakarta.faces.validator;
	exports jakarta.faces.view;
	exports jakarta.faces.view.facelets;
	exports jakarta.faces.webapp;
	exports jakarta.faces;

	requires transitive com.guicedee.guicedinjection;

	exports com.sun.faces.config;
	requires transitive jakarta.servlet.jsp.jstl;
	requires transitive java.xml.bind;

	requires java.sql;
	requires java.naming;
	requires java.desktop;

	requires jakarta.enterprise.cdi;

	requires static java.persistence;
	requires static jakarta.ejb;

	requires java.annotation;
	requires jakarta.json;
	requires jakarta.websocket.api;

	provides jakarta.enterprise.inject.spi.Extension with com.sun.faces.application.view.ViewScopeExtension, com.sun.faces.flow.FlowCDIExtension, com.sun.faces.flow.FlowDiscoveryCDIExtension, com.sun.faces.cdi.CdiExtension;
	provides jakarta.servlet.ServletContainerInitializer with com.sun.faces.config.FacesInitializer;
	provides com.guicedee.guicedinjection.interfaces.IPathContentsScanner with com.sun.faces.config.configprovider.FacesLocationsScanner;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.faces.implementations.GuicedFacesModule;


	uses com.sun.faces.util.cdi11.CDIUtil;

	uses com.sun.faces.spi.FacesConfigResourceProvider;
	uses com.sun.faces.spi.AnnotationProvider;
	uses com.sun.faces.spi.ConfigurationResourceProvider;
	uses com.sun.faces.spi.DiscoverableInjectionProvider;
	uses com.sun.faces.spi.FaceletConfigResourceProvider;
	uses com.sun.faces.spi.InjectionProvider;
	uses com.sun.faces.spi.SerializationProvider;
	uses jakarta.faces.application.ApplicationConfigurationPopulator;

}
