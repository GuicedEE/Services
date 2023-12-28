module com.guicedee.xmlrepresentation {
	exports com.guicedee.services.xmlrepresentation;
	
	requires static lombok;
	requires java.logging;
	requires java.xml;
	requires jakarta.xml.bind;
	requires jakarta.validation;
}