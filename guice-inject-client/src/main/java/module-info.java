import com.guicedee.client.implementations.GuicedEEClientModule;
import com.guicedee.client.implementations.GuicedEEClientPostStartup;
import com.guicedee.client.implementations.GuicedEEClientStartup;
import com.guicedee.client.implementations.*;
import com.guicedee.guicedinjection.interfaces.*;

module com.guicedee.client {
    requires transitive com.google.guice;
    requires transitive io.github.classgraph;
    requires transitive com.fasterxml.jackson.databind;

    //requires transitive jakarta.validation;

	exports com.guicedee.client;
    requires transitive io.vertx.core;
	
    exports com.guicedee.guicedinjection.properties;
    exports com.guicedee.guicedinjection.pairing;
    exports com.guicedee.guicedinjection.interfaces;
    exports com.guicedee.guicedinjection.interfaces.annotations;
    
    exports com.guicedee.guicedservlets.websockets.services;
    exports com.guicedee.guicedservlets.websockets.options;

    exports com.guicedee.guicedservlets.servlets.services;
    exports com.guicedee.guicedservlets.servlets.services.scopes;
    
    exports com.guicedee.guicedservlets.rest.annotations;
    opens com.guicedee.guicedservlets.rest.annotations to com.google.guice;

    requires static lombok;

    requires org.apache.commons.lang3;

    //slf4j config
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.jul;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.slf4j2.impl;
    requires java.logging;
    //requires io.vertx;

    requires static jakarta.inject;

    uses IGuiceProvider;
	uses IJobServiceProvider;
    uses com.guicedee.guicedservlets.servlets.services.IOnCallScopeEnter;
    uses com.guicedee.guicedservlets.servlets.services.IOnCallScopeExit;
    uses com.guicedee.guicedservlets.websockets.services.IWebSocketMessageReceiver;

    opens com.guicedee.guicedservlets.websockets.options to com.fasterxml.jackson.databind;
    opens com.guicedee.guicedservlets.websockets.services to com.google.guice;
    opens com.guicedee.guicedservlets.servlets.services to com.google.guice;
	
	opens com.guicedee.guicedinjection.properties to com.fasterxml.jackson.databind;
	opens com.guicedee.guicedinjection.pairing to com.fasterxml.jackson.databind;

    opens com.guicedee.client.implementations to com.google.guice;
    opens com.guicedee.client to com.fasterxml.jackson.databind;

    provides IGuicePreStartup with GuicedEEClientStartup;
    provides IGuicePostStartup with GuicedEEClientPostStartup;
    provides IGuiceModule with GuicedEEClientModule;
}