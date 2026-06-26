module com.codahale.metrics {
	requires org.slf4j;
	requires tools.jackson.databind;
	
	exports com.codahale.metrics;
	exports com.codahale.metrics.graphite;


	exports org.eclipse.microprofile.metrics.annotation;
	opens org.eclipse.microprofile.metrics.annotation to tools.jackson.databind,com.guicedee.guicedinjection;
	opens org.eclipse.microprofile.metrics to tools.jackson.databind,com.guicedee.guicedinjection;
	exports org.eclipse.microprofile.metrics;



/*	exports io.smallrye.metrics.base;
	exports io.smallrye.metrics.elementdesc.adapter;
	exports io.smallrye.metrics.elementdesc.adapter.cdi;
	exports io.smallrye.metrics.exporters;
	exports io.smallrye.metrics.jaxrs;
	exports io.smallrye.metrics.legacyapi;
	exports io.smallrye.metrics.legacyapi.interceptors;
	exports io.smallrye.metrics.micrometer;
	exports io.smallrye.metrics.setup;*/

	opens com.codahale.metrics to tools.jackson.databind,com.guicedee.guicedinjection;
	opens com.codahale.metrics.graphite to tools.jackson.databind,com.guicedee.guicedinjection;


/*

	opens io.smallrye.metrics.base to tools.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.elementdesc to tools.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.exporters to tools.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.jaxrs to tools.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.legacyapi to tools.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.micrometer to tools.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.setup to tools.jackson.databind,com.guicedee.guicedinjection;

*/

	//provides jakarta.enterprise.inject.spi.Extension with com.codahale.metrics.MetricsExtension;
}