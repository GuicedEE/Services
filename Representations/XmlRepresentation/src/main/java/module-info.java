module com.guicedee.xmlrepresentation {
	exports com.guicedee.services.xmlrepresentation;
	
	requires transitive org.glassfish.jaxb.runtime;
	
	requires static lombok;
	requires java.logging;
	requires java.xml;
	requires jakarta.xml.bind;
	requires jakarta.validation;
}