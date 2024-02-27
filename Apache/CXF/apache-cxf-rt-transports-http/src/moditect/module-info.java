module org.apache.cxf.rt.transports.http {
	requires org.apache.cxf;
	
	requires java.desktop;
	
	requires jakarta.servlet;
	requires jakarta.xml.bind;
	
	//exports org.apache.cxf.transport.commons_text;
	exports org.apache.cxf.transport.http;
	exports org.apache.cxf.transport.http.auth;
	exports org.apache.cxf.transport.http.policy;
	exports org.apache.cxf.transport.http.policy.impl;
	
	exports org.apache.cxf.transport.https;
	exports org.apache.cxf.transport.https.httpclient;
	
	exports org.apache.cxf.transport.servlet;
	opens org.apache.cxf.transport.servlet;
	exports org.apache.cxf.transport.servlet.servicelist;
	
	exports org.apache.cxf.transports.http.configuration;
	
}

