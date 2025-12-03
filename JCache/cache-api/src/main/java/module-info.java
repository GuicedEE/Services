module cache.api {
	requires java.management;

	exports javax.cache;
	exports javax.cache.annotation;
	exports javax.cache.configuration;
	exports javax.cache.event;
	exports javax.cache.expiry;
	exports javax.cache.integration;
	exports javax.cache.management;
	exports javax.cache.processor;
	exports javax.cache.spi;

	uses javax.cache.spi.CachingProvider;

}
