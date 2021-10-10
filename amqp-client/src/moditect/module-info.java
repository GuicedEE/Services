module com.rabbitmq.client {
	requires static com.codahale.metrics;
	
	
	requires static java.sql;
	requires static java.naming;
	requires static java.desktop;
	
	requires static java.security.sasl;
	
	requires org.slf4j;
	requires com.fasterxml.jackson.databind;
	
	exports com.rabbitmq.client;
	exports com.rabbitmq.client.impl;
	exports com.rabbitmq.client.impl.nio;
	exports com.rabbitmq.client.impl.recovery;
	
	//exports com.rabbitmq.client.tools.json;
//	exports com.rabbitmq.client.tools.jsonrpc;
	
	//exports com.rabbitmq.client.utility;
}