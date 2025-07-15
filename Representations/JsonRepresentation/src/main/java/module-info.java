module com.guicedee.jsonrepresentation {
	exports com.guicedee.services.jsonrepresentation;
	exports com.guicedee.services.jsonrepresentation.json;
	
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.core;

	requires com.guicedee.client;
	requires org.apache.commons.lang3;
	
	requires static lombok;
	requires java.logging;
	requires com.fasterxml.jackson.datatype.jsr310;

	requires static jakarta.inject;
	
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.services.jsonrepresentation.implementations.ObjectMapperBinder;
}