module com.google.guice.extensions.grapher {
	requires com.google.guice;

	exports com.google.inject.grapher;

	opens com.google.inject.grapher to com.google.guice;
}
