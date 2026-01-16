module com.codahale.metrics {
	requires org.slf4j;
	requires com.fasterxml.jackson.databind;
	
	exports com.codahale.metrics;
	exports com.codahale.metrics.graphite;


	exports org.eclipse.microprofile.metrics.annotation;
	opens org.eclipse.microprofile.metrics.annotation to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens org.eclipse.microprofile.metrics to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
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

	opens com.codahale.metrics to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens com.codahale.metrics.graphite to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;


/*

	opens io.smallrye.metrics.base to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.elementdesc to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.exporters to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.jaxrs to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.legacyapi to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.micrometer to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;
	opens io.smallrye.metrics.setup to com.fasterxml.jackson.databind,com.guicedee.guicedinjection;

*/

	//provides jakarta.enterprise.inject.spi.Extension with com.codahale.metrics.MetricsExtension;
}