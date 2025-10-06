module io.smallrye.config.core {
	exports io.smallrye.config;
	exports org.eclipse.microprofile.config;
	exports org.eclipse.microprofile.config.spi;
	exports org.eclipse.microprofile.config.inject;
	
	requires io.smallrye.common.classloader;
	requires org.jboss.logging;
	requires io.smallrye.common.constraint;

  exports org.eclipse.microprofile.context;
  exports org.eclipse.microprofile.context.spi;

	exports io.smallrye.config._private to org.jboss.logging;
	opens io.smallrye.config._private to org.jboss.logging;
	
	uses org.eclipse.microprofile.config.spi.ConfigSource;
	uses org.eclipse.microprofile.config.spi.ConfigSourceProvider;
	uses io.smallrye.config.ConfigSourceFactory;
	uses org.eclipse.microprofile.config.spi.ConfigProviderResolver;
	uses io.smallrye.config.ConfigSourceInterceptor;
	uses io.smallrye.config.ConfigSourceInterceptorFactory;
	uses org.eclipse.microprofile.config.spi.Converter;
	uses io.smallrye.config.SmallRyeConfigFactory;
	uses io.smallrye.config.SmallRyeConfigBuilderCustomizer;
	uses io.smallrye.config.SecretKeysHandler;
	uses io.smallrye.config.SecretKeysHandlerFactory;
	uses io.smallrye.config.ConfigValidator;
	
	provides io.smallrye.config.ConfigSourceFactory with io.smallrye.config.PropertiesLocationConfigSourceFactory;
	provides org.eclipse.microprofile.config.spi.ConfigProviderResolver with io.smallrye.config.SmallRyeConfigProviderResolver;
}