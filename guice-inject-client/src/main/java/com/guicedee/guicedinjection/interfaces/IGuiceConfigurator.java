package com.guicedee.guicedinjection.interfaces;

/**
 * Service Locator Interface for granular configuration of the GuiceContext and Injector
 */
@FunctionalInterface
public interface IGuiceConfigurator {
    /**
     * Configuers the guice instance
     *
     * @param config The configuration object coming in
     * @return The required guice configuration
     */
    IGuiceConfig<?> configure(IGuiceConfig<?> config);


}
