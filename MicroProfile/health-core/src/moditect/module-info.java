module com.guicedee.services.health {

    requires java.logging;

    exports org.eclipse.microprofile.health;
    exports org.eclipse.microprofile.health.spi;

    uses org.eclipse.microprofile.health.spi.HealthCheckResponseProvider;
}