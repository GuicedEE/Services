module jakarta.persistence {
	requires transitive java.logging;
	requires transitive java.sql;

	requires java.instrument;

	exports jakarta.persistence;
	exports jakarta.persistence.criteria;
	exports jakarta.persistence.metamodel;
	exports jakarta.persistence.spi;

	requires jakarta.activation;

	uses jakarta.persistence.spi.PersistenceUnitInfo;
	uses jakarta.persistence.spi.ClassTransformer;
	uses jakarta.persistence.spi.PersistenceProvider;
	uses jakarta.persistence.spi.PersistenceProviderResolver;
}
