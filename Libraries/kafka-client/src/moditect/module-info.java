module org.apache.kafka.client {
	exports org.apache.kafka.clients;
	exports org.apache.kafka.clients.admin;
	exports org.apache.kafka.clients.consumer;
	exports org.apache.kafka.clients.producer;
	exports org.apache.kafka.common;
	exports org.apache.kafka.common.acl;
	exports org.apache.kafka.common.config;
	exports org.apache.kafka.common.errors;
	exports org.apache.kafka.common.header;
	exports org.apache.kafka.common.record;
	exports org.apache.kafka.common.header.internals to io.vertx.client.kafka;
	exports org.apache.kafka.common.utils to io.vertx.client.kafka;
	
	exports org.apache.kafka.server.authorizer;
	exports org.apache.kafka.server.policy;
	exports org.apache.kafka.server.quota;
	
	exports  org.apache.kafka.common.serialization;
	
	requires org.slf4j;
	requires java.management;

	provides org.apache.kafka.common.config.provider.ConfigProvider with org.apache.kafka.common.config.provider.FileConfigProvider;
}
