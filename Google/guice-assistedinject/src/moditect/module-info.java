module com.google.guice.extensions.assistedinject {
	requires com.google.guice;

	exports com.google.inject.assistedinject;
	opens com.google.inject.assistedinject to com.google.guice;
}
