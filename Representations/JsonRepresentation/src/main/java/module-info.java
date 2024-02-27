module com.guicedee.jsonrepresentation {
	exports com.guicedee.services.jsonrepresentation;
	exports com.guicedee.services.jsonrepresentation.json;
	
	requires com.fasterxml.jackson.databind;
	
	requires com.guicedee.client;
	requires org.apache.commons.lang3;
	
	requires static lombok;
	requires java.logging;
	requires com.fasterxml.jackson.datatype.jsr310;
	
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.services.jsonrepresentation.implementations.ObjectMapperBinder;
}