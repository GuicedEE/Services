open module javax.faces {

	exports javax.faces.model;
	exports javax.faces.annotation;
	exports javax.faces.bean;
	exports javax.faces.application;
	exports javax.faces.component;
	exports javax.faces.component.behavior;
	exports javax.faces.component.html;
	exports javax.faces.component.search;
	exports javax.faces.component.visit;
	exports javax.faces.context;
	exports javax.faces.convert;
	exports javax.faces.el;
	exports javax.faces.event;
	exports javax.faces.flow;
	exports javax.faces.lifecycle;

	exports javax.faces.push;
	exports javax.faces.render;
	exports javax.faces.validator;
	exports javax.faces.view;
	exports javax.faces.view.facelets;
	exports javax.faces.webapp;
	exports javax.faces;

	requires transitive com.guicedee.guicedinjection;

	exports com.sun.faces.config;
	requires transitive javax.servlet.jsp.jstl;
	requires transitive java.xml.bind;

	requires java.sql;
	requires java.naming;
	requires java.desktop;

	requires jakarta.enterprise.cdi;
	requires static java.persistence;
	requires static javax.ejb;

	requires java.annotation;
	requires jakarta.json;
	requires javax.websocket.api;

	provides javax.enterprise.inject.spi.Extension with com.sun.faces.application.view.ViewScopeExtension, com.sun.faces.flow.FlowCDIExtension, com.sun.faces.flow.FlowDiscoveryCDIExtension, com.sun.faces.cdi.CdiExtension;
	provides javax.servlet.ServletContainerInitializer with com.sun.faces.config.FacesInitializer;
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
	uses javax.faces.application.ApplicationConfigurationPopulator;



}
