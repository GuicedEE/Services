module java.persistence {
	requires transitive java.logging;
	requires static java.sql;
	requires java.instrument;

	exports javax.persistence;
	exports javax.persistence.criteria;
	exports javax.persistence.metamodel;
	exports javax.persistence.spi;

	requires jakarta.activation;

	uses javax.persistence.spi.PersistenceUnitInfo;
	uses javax.persistence.spi.ClassTransformer;
	uses javax.persistence.spi.PersistenceProvider;
	uses javax.persistence.spi.PersistenceProviderResolver;
}
