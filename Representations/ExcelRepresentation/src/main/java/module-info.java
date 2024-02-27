module com.guicedee.excelrepresentation {
	exports com.guicedee.services.excelrepresentation;
	
	requires com.guicedee.jsonrepresentation;
	
	requires org.apache.poi.ooxml;
	requires org.apache.poi.poi;
	requires org.json;
	requires com.fasterxml.jackson.databind;
	
	requires static lombok;
	requires java.logging;
}