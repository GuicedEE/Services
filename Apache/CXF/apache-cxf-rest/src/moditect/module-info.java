module org.apache.cxf.rest {
	requires org.apache.cxf;
	
	requires org.apache.cxf.rt.transports.http;
	requires org.apache.cxf.rt.security;
	
	requires transitive jakarta.ws.rs;
	
	requires com.fasterxml.jackson.jakarta.rs.json;
	
	exports org.apache.cxf.jaxrs;
	exports org.apache.cxf.jaxrs.interceptor;
	exports org.apache.cxf.jaxrs.lifecycle;
	exports org.apache.cxf.jaxrs.model;
	exports org.apache.cxf.jaxrs.nio;
	exports org.apache.cxf.jaxrs.provider;
	exports org.apache.cxf.jaxrs.security;
	exports org.apache.cxf.jaxrs.servlet;
	exports org.apache.cxf.jaxrs.validation;
	exports org.apache.cxf.jaxrs.utils;
	
	exports org.apache.cxf.jaxrs.servlet.sci;
	
	uses org.apache.cxf.jaxrs.ext.ContextResolver;
	
}

