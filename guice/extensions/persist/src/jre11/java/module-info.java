module com.google.guice.extensions.persist {
	exports com.google.inject.persist;

	requires com.google.guice;

	requires com.google.common;

	requires static java.servlet;
	requires static aopalliance;

	requires javax.inject;

	opens com.google.inject.persist to com.google.guice;
	opens com.google.inject.persist.finder to com.google.guice;
	opens com.google.inject.persist.jpa to com.google.guice;

	//Test Dependencies
	requires java.sql;
	requires static java.logging;
	requires java.naming;

	requires java.xml.bind;
	requires java.persistence;
}
