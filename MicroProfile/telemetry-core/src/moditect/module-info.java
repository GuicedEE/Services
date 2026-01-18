module com.guicedee.services.opentelemetry {
	requires org.slf4j;
	requires org.apache.logging.log4j;
	requires org.apache.logging.log4j.core;
	requires java.logging;

	requires io.vertx.core;
	requires com.google.common;

	requires java.net.http;
	requires com.fasterxml.jackson.core;


	uses io.opentelemetry.context.ContextStorageProvider;
	uses io.opentelemetry.exporter.internal.http.HttpSenderProvider;

	uses io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.ConfigurablePropagatorProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.ResourceProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.logs.ConfigurableLogRecordExporterProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.metrics.ConfigurableMetricExporterProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSpanExporterProvider;
	uses io.opentelemetry.sdk.autoconfigure.spi.internal.ComponentProvider;

	provides io.opentelemetry.context.ContextStorageProvider with
			io.vertx.tracing.opentelemetry.VertxContextStorageProvider;
	provides io.vertx.core.spi.VertxServiceProvider with
			io.vertx.tracing.opentelemetry.OpenTelemetryTracingFactory;

	provides io.opentelemetry.api.trace.TracerProvider with io.opentelemetry.sdk.trace.SdkTracerProvider;
	provides io.opentelemetry.api.metrics.MeterProvider with io.opentelemetry.sdk.metrics.SdkMeterProvider;
	provides io.opentelemetry.api.logs.LoggerProvider with io.opentelemetry.sdk.logs.SdkLoggerProvider;

	provides io.opentelemetry.exporter.internal.http.HttpSenderProvider with
			io.opentelemetry.exporter.sender.jdk.internal.JdkHttpSenderProvider;

	exports io.opentelemetry.api;
	exports io.opentelemetry.api.baggage;
	exports io.opentelemetry.api.baggage.propagation;
	exports io.opentelemetry.api.common;
	exports io.opentelemetry.api.incubator.config;
	exports io.opentelemetry.api.incubator.logs;
	exports io.opentelemetry.api.incubator.metrics;
	exports io.opentelemetry.api.incubator.propagation;
	exports io.opentelemetry.api.incubator.trace;
	exports io.opentelemetry.api.internal;
	exports io.opentelemetry.api.logs;
	exports io.opentelemetry.api.metrics;
	exports io.opentelemetry.api.trace;
	exports io.opentelemetry.api.trace.propagation;
	exports io.opentelemetry.context;
	exports io.opentelemetry.context.propagation;
	exports io.opentelemetry.exporter.internal;
	exports io.opentelemetry.exporter.internal.compression;
	exports io.opentelemetry.exporter.internal.http;
	exports io.opentelemetry.exporter.internal.marshal;
	exports io.opentelemetry.exporter.internal.metrics;
	exports io.opentelemetry.exporter.internal.otlp;
	exports io.opentelemetry.exporter.internal.otlp.logs;
	exports io.opentelemetry.exporter.internal.otlp.metrics;
	exports io.opentelemetry.exporter.internal.otlp.traces;
	exports io.opentelemetry.exporter.otlp.trace;
	exports io.opentelemetry.exporter.sender.jdk.internal;
	//exports io.opentelemetry.exporter.sender.okhttp.internal;

	exports io.opentelemetry.instrumentation.api;
	exports io.opentelemetry.instrumentation.api.instrumenter;
	exports io.opentelemetry.instrumentation.api.semconv;
	exports io.opentelemetry.instrumentation.api.semconv.http;
	exports io.opentelemetry.instrumentation.api.semconv.http.internal;
	exports io.opentelemetry.instrumentation.api.semconv.network;
	exports io.opentelemetry.instrumentation.api.semconv.network.internal;
	exports io.opentelemetry.instrumentation.api.semconv.url;
	exports io.opentelemetry.instrumentation.api.semconv.url.internal;
	exports io.opentelemetry.instrumentation.api.semconv.util;
	exports io.opentelemetry.instrumentation.api.util;
	exports io.opentelemetry.instrumentation.log4j.appender.v2_17;
	exports io.opentelemetry.instrumentation.log4j.appender.v2_17.internal;
	exports io.opentelemetry.instrumentation.log4j.contextdata.v2_17;
	exports io.opentelemetry.instrumentation.log4j.contextdata.v2_17.internal;

	exports io.opentelemetry.sdk;
	exports io.opentelemetry.sdk.autoconfigure;
	exports io.opentelemetry.sdk.autoconfigure.internal;
	exports io.opentelemetry.sdk.autoconfigure.spi;
	exports io.opentelemetry.sdk.autoconfigure.spi.internal;
	exports io.opentelemetry.sdk.autoconfigure.spi.logs;
	exports io.opentelemetry.sdk.autoconfigure.spi.metrics;
	exports io.opentelemetry.sdk.autoconfigure.spi.traces;
	exports io.opentelemetry.sdk.common;
	exports io.opentelemetry.sdk.common.export;
	exports io.opentelemetry.sdk.common.internal;
	exports io.opentelemetry.sdk.internal;
	exports io.opentelemetry.sdk.logs;
	exports io.opentelemetry.sdk.logs.data;
	exports io.opentelemetry.sdk.logs.data.internal;
	exports io.opentelemetry.sdk.logs.export;
	exports io.opentelemetry.sdk.logs.internal;
	exports io.opentelemetry.sdk.metrics;
	exports io.opentelemetry.sdk.metrics.data;
	exports io.opentelemetry.sdk.metrics.export;
	exports io.opentelemetry.sdk.metrics.internal;
	exports io.opentelemetry.sdk.metrics.internal.aggregator;
	exports io.opentelemetry.sdk.metrics.internal.concurrent;
	exports io.opentelemetry.sdk.metrics.internal.data;
	exports io.opentelemetry.sdk.metrics.internal.debug;
	exports io.opentelemetry.sdk.metrics.internal.descriptor;
	exports io.opentelemetry.sdk.metrics.internal.exemplar;
	exports io.opentelemetry.sdk.metrics.internal.export;
	exports io.opentelemetry.sdk.metrics.internal.state;
	exports io.opentelemetry.sdk.metrics.internal.view;
	exports io.opentelemetry.sdk.resources;
	exports io.opentelemetry.sdk.testing.exporter;
	exports io.opentelemetry.sdk.testing.logs;
	exports io.opentelemetry.sdk.testing.logs.internal;
	exports io.opentelemetry.sdk.testing.metrics;
	exports io.opentelemetry.sdk.testing.time;
	exports io.opentelemetry.sdk.testing.trace;
	exports io.opentelemetry.sdk.trace;
	exports io.opentelemetry.sdk.trace.data;
	exports io.opentelemetry.sdk.trace.export;
	exports io.opentelemetry.sdk.trace.internal;
	exports io.opentelemetry.sdk.trace.samplers;

	exports io.vertx.tracing.opentelemetry;

	opens io.opentelemetry.api to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.baggage to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.common to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator.common to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator.config to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator.internal to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator.metrics to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator.propagation to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.incubator.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.metrics to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.api.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.context to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.instrumentation.log4j.appender.v2_17 to com.fasterxml.jackson.databind, com.guicedee.guicedinjection,org.apache.logging.log4j.core;
	opens io.opentelemetry.instrumentation.log4j.appender.v2_17.internal to com.fasterxml.jackson.databind, com.guicedee.guicedinjection,org.apache.logging.log4j.core;

	opens io.opentelemetry.exporter.otlp.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.exporter.otlp.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.exporter.otlp.metrics to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.exporter.otlp.http.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	exports io.opentelemetry.exporter.otlp.http.trace;
	exports io.opentelemetry.exporter.otlp.http.logs;
	opens io.opentelemetry.exporter.otlp.http.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.exporter.otlp.http.metrics to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;

	opens io.opentelemetry.sdk.autoconfigure to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.logs to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.logs.export to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.metrics to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.resources to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.trace to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.trace.export to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.trace.data to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;
	opens io.opentelemetry.sdk.logs.data to com.fasterxml.jackson.databind, com.guicedee.guicedinjection;




}
