module com.microsoft.sqlserver.jdbc {
	exports com.microsoft.sqlserver.jdbc;
	exports microsoft.sql;

	requires java.sql;
	requires java.naming;
	requires java.security.jgss;

	requires static azure.client.runtime;
	requires static azure.keyvault;
	requires static azure.keyvault.webkey;
	requires static adal4j;
	requires static client.runtime;
	requires static okhttp3;
	requires static retrofit2;

	provides java.sql.Driver with com.microsoft.sqlserver.jdbc.SQLServerDriver;
}
