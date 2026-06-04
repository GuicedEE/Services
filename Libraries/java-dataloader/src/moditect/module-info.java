module org.dataloader {
	requires transitive org.reactivestreams;

	requires static org.jspecify;

	exports org.dataloader;
	exports org.dataloader.annotations;
	exports org.dataloader.impl;
	exports org.dataloader.instrumentation;
	exports org.dataloader.reactive;
	exports org.dataloader.registries;
	exports org.dataloader.scheduler;
	exports org.dataloader.stats;
	exports org.dataloader.stats.context;
}

