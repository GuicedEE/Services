module io.opentelemetry.api {
	requires org.slf4j;
	requires org.apache.logging.log4j;
	requires org.apache.logging.log4j.core;
	requires java.logging;

	provides io.opentelemetry.api.trace.TracerProvider with io.opentelemetry.sdk.trace.SdkTracerProvider;
	provides io.opentelemetry.api.metrics.MeterProvider with io.opentelemetry.sdk.metrics.SdkMeterProvider;
	provides io.opentelemetry.api.logs.LoggerProvider with io.opentelemetry.sdk.logs.SdkLoggerProvider;

	exports io.opentelemetry.api;
	exports io.opentelemetry.api.baggage;
	exports io.opentelemetry.api.baggage.propagation;
	exports io.opentelemetry.api.common;
	exports io.opentelemetry.api.internal;
	exports io.opentelemetry.api.logs;
	exports io.opentelemetry.api.metrics;
	exports io.opentelemetry.api.trace;
	exports io.opentelemetry.api.trace.propagation;
	exports io.opentelemetry.context;
	exports io.opentelemetry.context.propagation;
	exports io.opentelemetry.instrumentation.log4j.appender.v2_17;
	exports io.opentelemetry.instrumentation.log4j.appender.v2_17.internal;

	exports io.opentelemetry.exporter.otlp.logs;
	exports io.opentelemetry.exporter.otlp.trace;
	exports io.opentelemetry.sdk;
	exports io.opentelemetry.sdk.logs;
	exports io.opentelemetry.sdk.logs.export;
	exports io.opentelemetry.sdk.metrics;
	exports io.opentelemetry.sdk.resources;
	exports io.opentelemetry.sdk.trace;
	exports io.opentelemetry.sdk.trace.export;
	exports io.opentelemetry.sdk.trace.data;
	exports io.opentelemetry.sdk.testing.exporter;
	exports io.opentelemetry.sdk.logs.data;

	opens io.opentelemetry.api to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.baggage to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.common to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.metrics to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.context to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.instrumentation.log4j.appender.v2_17 to com.fasterxml.jackson.databind, com.guicedee.guicedinjection,org.apache.logging.log4j.core;
	opens io.opentelemetry.instrumentation.log4j.appender.v2_17.internal to com.fasterxml.jackson.databind, com.guicedee.guicedinjection,org.apache.logging.log4j.core;

	opens io.opentelemetry.exporter.otlp.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.exporter.otlp.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.logs.export to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.metrics to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.resources to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.trace.export to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;

	uses io.opentelemetry.exporter.internal.grpc.GrpcSenderProvider;
	uses io.opentelemetry.exporter.internal.http.HttpSenderProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.ConfigurablePropagatorProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.ResourceProvider;
	//uses io.opentelemetry.sdk.autoconfigure.spi.sampler.ConfigurableSamplerProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.logs.ConfigurableLogRecordExporterProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.metrics.ConfigurableMetricExporterProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSpanExporterProvider;
	uses io.opentelemetry.context.ContextStorageProvider;
}
