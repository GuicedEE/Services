module cache.api {
	requires java.management;

	exports jakarta.cache;
	exports jakarta.cache.annotation;
	exports jakarta.cache.configuration;
	exports jakarta.cache.event;
	exports jakarta.cache.expiry;
	exports jakarta.cache.integration;
	exports jakarta.cache.management;
	exports jakarta.cache.processor;
	exports jakarta.cache.spi;

	uses jakarta.cache.spi.CachingProvider;

}
