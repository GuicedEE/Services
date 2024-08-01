package com.guicedee.guicedinjection.interfaces;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public interface Log4JConfigurator
{
    ConfigurationBuilder<BuiltConfiguration> configure(ConfigurationBuilder<BuiltConfiguration> builder,RootLoggerComponentBuilder rootLoggerComponentBuilder);
}
