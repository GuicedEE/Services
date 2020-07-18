module com.h2database.h2 {
	requires java.sql;

	exports org.h2;

	provides java.sql.Driver with org.h2.Driver;
}
