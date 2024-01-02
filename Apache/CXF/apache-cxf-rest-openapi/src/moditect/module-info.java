module org.apache.cxf.rest.openapi {
	requires org.apache.cxf.rest;
	requires org.apache.cxf;
	
	requires transitive com.guicedee.services.openapi;
	
	exports org.apache.cxf.jaxrs.openapi;
	exports org.apache.cxf.jaxrs.common.openapi;
	exports org.apache.cxf.jaxrs.swagger.ui;
	exports org.apache.cxf.jaxrs.openapi.parse;
	
}

