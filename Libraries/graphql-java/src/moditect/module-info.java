module com.graphqljava {
	requires transitive org.dataloader;
	requires transitive org.reactivestreams;

	requires static org.jspecify;

	// Canonical Guava (com.google.guava:guava). GraphQL-Java's internal references to its
	// embedded copy (graphql.com.google.common.*) are rewritten back to com.google.common.*
	// by the shade, so the module now reads the real Guava module instead of a stale bundled one.
	// transitive: GraphQL-Java exposes a few Guava types on its public API surface.
	requires transitive com.google.common;

	// Required by the relocated ANTLR runtime still bundled under graphql.org.antlr.*
	// (it uses java.util.logging.Logger). As an automatic module graphql-java implicitly read
	// every module; as a named module this must be declared explicitly.
	requires java.logging;

	exports graphql;
	exports graphql.analysis;
	exports graphql.analysis.values;
	exports graphql.collect;
	exports graphql.execution;
	exports graphql.execution.conditional;
	exports graphql.execution.directives;
	exports graphql.execution.incremental;
	exports graphql.execution.instrumentation;
	exports graphql.execution.instrumentation.dataloader;
	exports graphql.execution.instrumentation.fieldvalidation;
	exports graphql.execution.instrumentation.parameters;
	exports graphql.execution.instrumentation.tracing;
	exports graphql.execution.preparsed;
	exports graphql.execution.preparsed.persisted;
	exports graphql.execution.reactive;
	exports graphql.execution.values;
	exports graphql.execution.values.legacycoercing;
	exports graphql.extensions;
	exports graphql.i18n;
	exports graphql.incremental;
	exports graphql.introspection;
	exports graphql.language;
	exports graphql.normalized;
	exports graphql.normalized.incremental;
	exports graphql.normalized.nf;
	exports graphql.parser;
	exports graphql.parser.antlr;
	exports graphql.parser.exceptions;
	exports graphql.relay;
	exports graphql.scalar;
	exports graphql.schema;
	exports graphql.schema.diff;
	exports graphql.schema.diff.reporting;
	exports graphql.schema.diffing;
	exports graphql.schema.diffing.ana;
	exports graphql.schema.fetching;
	exports graphql.schema.idl;
	exports graphql.schema.idl.errors;
	exports graphql.schema.impl;
	exports graphql.schema.transform;
	exports graphql.schema.usage;
	exports graphql.schema.validation;
	exports graphql.schema.visibility;
	exports graphql.schema.visitor;
	exports graphql.util;
	exports graphql.util.querygenerator;
	exports graphql.validation;
}

