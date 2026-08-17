module reactor.core {
	// Publisher/Subscriber appear all over Flux/Mono's exported API.
	requires transitive org.reactivestreams;

	// reactor.util.Loggers falls back to java.util.logging when no SLF4J binding is present.
	// As an automatic module this read every module implicitly; named modules do not.
	requires java.logging;
	requires static org.slf4j;

	exports reactor.adapter;
	exports reactor.core;
	exports reactor.core.observability;
	exports reactor.core.publisher;
	exports reactor.core.scheduler;
	exports reactor.util;
	exports reactor.util.annotation;
	exports reactor.util.concurrent;
	exports reactor.util.context;
	exports reactor.util.function;
	exports reactor.util.retry;

	// NOTE: the jar ships META-INF/services for io.micrometer.context.ContextAccessor and
	// reactor.blockhound.integration.BlockHoundIntegration. Neither interface is on the
	// module path here (both are optional integrations), so no `provides` clauses are
	// declared - a `provides` naming an unresolvable service type would not compile.
	// The service files remain inside the jar for classpath use.
}

