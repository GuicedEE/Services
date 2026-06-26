module com.guicedee.modules.services.openapi {

	requires org.slf4j;

	requires transitive tools.jackson.databind;
	requires io.github.classgraph;

	requires jakarta.ws.rs;
	requires jakarta.xml.bind;

	// Optional bean-validation integration: swagger-core's ModelResolver reflectively reads
	// jakarta.validation constraint annotations (e.g. @NotNull) off scanned resource DTOs while
	// building the OpenAPI model. The dependency is optional at compile/run time, but because
	// jakarta.validation is present in the resolved module graph of any app that uses it, this
	// static requires still yields the runtime readability edge ModelResolver needs.
	requires static jakarta.validation;

	requires org.apache.commons.lang3;
	
	requires static tools.jackson.jakarta.rs.json;
	//requires tools.jackson.dataformat.yaml;
	requires static tools.jackson.module.jakarta.xmlbind;

	requires tools.jackson.jakarta.rs.base;
	exports tools.jackson.jakarta.rs.yaml;
	opens tools.jackson.jakarta.rs.yaml;

	exports io.swagger.v3.jaxrs2.integration;
	exports io.swagger.v3.oas.integration;
	exports io.swagger.v3.oas.models;
	exports io.swagger.v3.jaxrs2.integration.resources;

	exports io.swagger.v3.oas.annotations;
	exports io.swagger.v3.oas.annotations.media;
	exports io.swagger.v3.oas.annotations.responses;
	exports io.swagger.v3.oas.annotations.info;
	exports io.swagger.v3.oas.annotations.tags;
	exports io.swagger.v3.oas.annotations.extensions;
	exports io.swagger.v3.oas.annotations.headers;
	exports io.swagger.v3.oas.annotations.links;
	exports io.swagger.v3.oas.annotations.servers;
	exports io.swagger.v3.oas.annotations.security;
	exports io.swagger.v3.oas.models.tags;

	exports io.swagger.v3.jaxrs2.ext;
	exports io.swagger.v3.core.converter;
	exports io.swagger.v3.oas.integration.api;

	opens io.swagger.v3.oas.models.examples to tools.jackson.databind;

	exports io.swagger.v3.oas.annotations.parameters;

	exports io.swagger.v3.oas.models.info;
	exports io.swagger.v3.oas.models.servers;
	exports io.swagger.v3.oas.annotations.enums;
	exports io.swagger.v3.oas.models.security;

	exports io.swagger.v3.oas.models.parameters;
	exports io.swagger.v3.jaxrs2;
	exports io.swagger.v3.core.util;
	exports io.swagger.v3.core.jackson;
	opens io.swagger.v3.jaxrs2.integration.resources to com.google.guice, org.apache.cxf;
	opens io.swagger.v3.jaxrs2 to com.google.guice, org.apache.cxf;

	opens io.swagger.v3.oas.integration to tools.jackson.databind;
	opens io.swagger.v3.core.jackson to tools.jackson.databind;
	opens io.swagger.v3.core.jackson.mixin to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.media to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.responses to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.info to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.tags to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.extensions to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.headers to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.links to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.servers to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.security to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.parameters to tools.jackson.databind;
	opens io.swagger.v3.oas.annotations.enums to tools.jackson.databind;

	opens io.swagger.v3.oas.models to tools.jackson.databind;
	opens io.swagger.v3.oas.models.parameters to tools.jackson.databind;
	opens io.swagger.v3.oas.models.media to tools.jackson.databind;
	opens io.swagger.v3.oas.models.responses to tools.jackson.databind;
	opens io.swagger.v3.oas.models.tags to tools.jackson.databind;
	opens io.swagger.v3.oas.models.info to tools.jackson.databind;
	opens io.swagger.v3.oas.models.headers to tools.jackson.databind;
	opens io.swagger.v3.oas.models.callbacks to tools.jackson.databind;
	opens io.swagger.v3.oas.models.links to tools.jackson.databind;
	opens io.swagger.v3.oas.models.security to tools.jackson.databind;
	opens io.swagger.v3.oas.models.servers to tools.jackson.databind;

	uses io.swagger.v3.jaxrs2.ext.OpenAPIExtension;
	uses io.swagger.v3.core.converter.ModelConverter;
	uses io.swagger.v3.oas.integration.api.OpenAPIConfigBuilder;
	
	provides jakarta.ws.rs.ext.MessageBodyReader with
			tools.jackson.jakarta.rs.yaml.JacksonYAMLProvider;
	provides jakarta.ws.rs.ext.MessageBodyWriter with
			tools.jackson.jakarta.rs.yaml.JacksonYAMLProvider;
}
