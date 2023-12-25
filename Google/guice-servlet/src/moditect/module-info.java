module com.google.guice.extensions.servlet {
	exports com.google.inject.servlet;

	//Servlet 4.0.3
	requires transitive jakarta.servlet;

	requires transitive com.google.guice;

	opens com.google.inject.servlet to com.google.guice;
}
