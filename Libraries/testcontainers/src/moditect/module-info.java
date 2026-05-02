open module org.testcontainers {
	requires java.instrument;
	requires java.management;
	requires java.desktop;

	requires java.sql;

	requires com.sun.jna;

	requires org.slf4j;
	requires java.logging;

	requires org.junit.jupiter.api;
	requires com.fasterxml.jackson.annotation;

	requires static org.apache.commons.compress;
	requires static org.apache.commons.lang3;
	requires static org.apache.commons.io;

	requires junit;
	requires org.reactivestreams;

	exports org.testcontainers.containers;
	exports org.testcontainers.containers.wait.strategy;
	exports org.testcontainers.containers.output;
	exports org.testcontainers.containers.startupcheck;
	exports org.testcontainers.junit.jupiter;
	exports org.testcontainers.utility;
	exports org.testcontainers.images;
	exports org.testcontainers.images.builder;

	provides java.sql.Driver with org.testcontainers.jdbc.ContainerDatabaseDriver;

	provides org.testcontainers.containers.JdbcDatabaseContainerProvider with
		org.testcontainers.containers.PostgreSQLContainerProvider,
		org.testcontainers.containers.PostgisContainerProvider,
		org.testcontainers.containers.TimescaleDBContainerProvider,
		org.testcontainers.containers.PgVectorContainerProvider,
		org.testcontainers.containers.MySQLContainerProvider,
		org.testcontainers.containers.MariaDBContainerProvider,
		org.testcontainers.containers.MSSQLServerContainerProvider,
		org.testcontainers.containers.OracleContainerProvider,
		org.testcontainers.containers.CassandraContainerProvider,
		org.testcontainers.containers.ClickHouseContainerProvider,
		org.testcontainers.containers.CockroachContainerProvider;

	provides org.testcontainers.dockerclient.DockerClientProviderStrategy with org.testcontainers.dockerclient.TestcontainersHostPropertyClientProviderStrategy,
		org.testcontainers.dockerclient.EnvironmentAndSystemPropertyClientProviderStrategy,
		org.testcontainers.dockerclient.UnixSocketClientProviderStrategy,
		org.testcontainers.dockerclient.DockerMachineClientProviderStrategy,
		org.testcontainers.dockerclient.NpipeSocketClientProviderStrategy,
		org.testcontainers.dockerclient.RootlessDockerClientProviderStrategy,
		org.testcontainers.dockerclient.DockerDesktopClientProviderStrategy;

	provides org.testcontainers.r2dbc.R2DBCDatabaseContainerProvider with
		org.testcontainers.containers.PostgreSQLR2DBCDatabaseContainerProvider,
		org.testcontainers.containers.MySQLR2DBCDatabaseContainerProvider,
		org.testcontainers.containers.MariaDBR2DBCDatabaseContainerProvider,
		org.testcontainers.containers.MSSQLServerR2DBCDatabaseContainerProvider;

	provides org.testcontainers.shaded.com.fasterxml.jackson.core.JsonFactory with org.testcontainers.shaded.com.fasterxml.jackson.core.JsonFactory;

	provides org.testcontainers.shaded.com.fasterxml.jackson.core.ObjectCodec with org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

	provides io.r2dbc.spi.ConnectionFactoryProvider with org.testcontainers.r2dbc.Hidden$TestcontainersR2DBCConnectionFactoryProvider;

	uses org.testcontainers.containers.JdbcDatabaseContainerProvider;
	uses org.testcontainers.containers.PgVectorContainerProvider;
	uses org.testcontainers.dockerclient.DockerClientProviderStrategy;
	uses org.testcontainers.r2dbc.R2DBCDatabaseContainerProvider;
	uses org.testcontainers.shaded.com.fasterxml.jackson.core.JsonFactory;
	uses org.testcontainers.shaded.com.fasterxml.jackson.core.ObjectCodec;
	uses org.testcontainers.utility.ImageNameSubstitutor;
	uses org.testcontainers.core.CreateContainerCmdModifier;
}
