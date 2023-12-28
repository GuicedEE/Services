module com.guicedee.client {
    requires transitive com.google.guice;
    requires transitive io.github.classgraph;
    requires transitive com.fasterxml.jackson.databind;
    requires transitive jakarta.validation;

    exports com.guicedee.guicedinjection.interfaces;
    exports com.guicedee.guicedinjection.interfaces.annotations;
}