import com.guicedee.guicedinjection.interfaces.*;

module com.guicedee.client {
    requires transitive com.google.guice;
    requires transitive io.github.classgraph;
    requires transitive com.fasterxml.jackson.databind;
    requires transitive jakarta.validation;

	exports com.guicedee.client;
	
    exports com.guicedee.guicedinjection.properties;
    exports com.guicedee.guicedinjection.pairing;
    exports com.guicedee.guicedinjection.interfaces;
    exports com.guicedee.guicedinjection.interfaces.annotations;
    
    exports com.guicedee.guicedservlets.websockets.services;
    exports com.guicedee.guicedservlets.websockets.options;
    
    exports com.guicedee.guicedservlets.undertow.services;
    
    exports com.guicedee.guicedservlets.servlets.services;
    exports com.guicedee.guicedservlets.servlets.services.scopes;
    
    exports com.guicedee.guicedservlets.rest.annotations;
    opens com.guicedee.guicedservlets.rest.annotations to com.google.guice, org.apache.cxf;
    
    requires static com.google.guice.extensions.servlet;
    requires static lombok;
    requires static undertow.core;
    requires static undertow.servlet;
    requires static undertow.websockets.jsr;
    requires static jakarta.websocket;
    requires static jakarta.servlet;
    
	uses IGuiceProvider;
	uses IJobServiceProvider;
    
    opens com.guicedee.guicedservlets.websockets.options to com.fasterxml.jackson.databind;
    opens com.guicedee.guicedservlets.undertow.services to com.google.guice;
    opens com.guicedee.guicedservlets.websockets.services to com.google.guice;
    opens com.guicedee.guicedservlets.servlets.services to com.google.guice;
	
	opens com.guicedee.guicedinjection.properties to com.fasterxml.jackson.databind;
	opens com.guicedee.guicedinjection.pairing to com.fasterxml.jackson.databind;
	
}