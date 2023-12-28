module org.postgresql.jdbc {
	
	exports org.postgresql;
	exports org.postgresql.util to org.hibernate.orm.core;
	
	requires java.sql;
	requires java.naming;
	requires java.management;
	requires java.security.jgss;
	requires java.security.sasl;
	
	provides java.sql.Driver with org.postgresql.Driver;
}
