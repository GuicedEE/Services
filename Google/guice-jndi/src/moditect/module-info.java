module com.google.guice.extensions.jndi {
	exports com.google.inject.jndi;

	requires com.google.guice;
	requires jakarta.inject;

	requires java.naming;

	opens com.google.inject.jndi to com.google.guice;
}
