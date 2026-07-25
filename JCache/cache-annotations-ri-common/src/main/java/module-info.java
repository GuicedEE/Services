module cache.annotations.ri.common {
	requires transitive cache.api;
	requires transitive io.smallrye.mutiny;

	requires transitive java.logging;
	exports org.jsr107.ri.annotations;

}
