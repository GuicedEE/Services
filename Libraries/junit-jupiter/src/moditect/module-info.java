module org.junit.jupiter.api {
	requires static transitive org.apiguardian.api;
	requires transitive org.junit.platform.commons;
	requires transitive org.opentest4j;

	exports org.junit.jupiter.api;
	exports org.junit.jupiter.api.condition;
	exports org.junit.jupiter.api.extension;
	exports org.junit.jupiter.api.extension.support;
	exports org.junit.jupiter.api.function;
	exports org.junit.jupiter.api.io;
	exports org.junit.jupiter.api.parallel;

	opens org.junit.jupiter.api.condition to
			org.junit.platform.commons;
}
