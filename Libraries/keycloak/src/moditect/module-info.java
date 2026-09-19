/* Compile-only boundary. Never deploy this API bundle into Keycloak. */
module org.keycloak.api {
    requires static transitive jakarta.ws.rs;
    requires static transitive com.fasterxml.jackson.core;
    exports org.keycloak;
    exports org.keycloak.common;
    exports org.keycloak.models;
    exports org.keycloak.protocol.oidc;
    exports org.keycloak.protocol.oidc.grants;
    exports org.keycloak.provider;
    exports org.keycloak.representations;
    exports org.keycloak.representations.dpop;
    exports org.keycloak.sessions;
    exports org.keycloak.services.util;
    exports org.keycloak.services;
    exports org.keycloak.jose.jws;
    exports org.keycloak.events;
    exports org.keycloak.http;
    exports org.keycloak.services.cors;
    exports org.keycloak.util;
}
