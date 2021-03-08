module com.google.guice.extensions.persist {
	exports com.google.inject.persist;

	requires transitive com.google.guice;
	requires transitive jakarta.persistence;

	requires static jakarta.servlet;
	requires static hibernate.jpa;

	opens com.google.inject.persist to com.google.guice;
	opens com.google.inject.persist.finder to com.google.guice;
	opens com.google.inject.persist.jpa to com.google.guice;

	//Test Dependencies
	requires static java.sql;
	requires java.naming;
}
