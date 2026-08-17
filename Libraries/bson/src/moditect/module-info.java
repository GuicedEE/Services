module org.mongodb.bson {
	// Optional upstream: org.bson.diagnostics.Loggers probes for SLF4J reflectively and
	// falls back to java.util.logging, so this must not become a hard runtime edge.
	requires static org.slf4j;
	requires java.logging;

	exports org.bson;
	exports org.bson.annotations;
	exports org.bson.assertions;
	exports org.bson.codecs;
	exports org.bson.codecs.configuration;
	exports org.bson.codecs.jsr310;
	exports org.bson.codecs.pojo;
	exports org.bson.codecs.pojo.annotations;
	exports org.bson.conversions;
	exports org.bson.diagnostics;
	// org.bson.internal / .internal.vector are exported deliberately: mongodb-driver-core
	// reaches into them. As an automatic module every package was readable; once named,
	// anything left unexported becomes an IllegalAccessError at runtime.
	exports org.bson.internal;
	exports org.bson.internal.vector;
	exports org.bson.io;
	exports org.bson.json;
	exports org.bson.types;
}

