/* Compile-only boundary. Never deploy this API bundle into Keycloak. */
module org.keycloak.api {
    requires static transitive jakarta.ws.rs;
    /* Jackson 3 - the Jackson 2 references in the upstream Keycloak binaries are relocated
       onto tools.jackson.* by the shade step. Annotations stay on Jackson 2.x per JSTEP-1. */
    requires static transitive com.fasterxml.jackson.annotation;
    requires static transitive tools.jackson.core;
    requires static transitive tools.jackson.databind;

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

    /*
       Qualified opens for every package that carries Jackson annotations or a
       ValueSerializer/ValueDeserializer implementation, so databind can introspect the
       representations and instantiate the @JsonSerialize/@JsonDeserialize handlers without
       this bundle having to export them to the world.

       To regenerate: scan the shaded jar for classes referencing
       com/fasterxml/jackson/annotation/, tools/jackson/databind/annotation/ or
       tools/jackson/databind/Value(Ser|Deser)ializer, and emit the distinct packages.
    */
    opens org.keycloak to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.execactions to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.idpverifyemail to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.inviteorg to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.updateemail to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.verifyemail to tools.jackson.databind;
    opens org.keycloak.authentication.authenticators.broker.util to tools.jackson.databind;
    opens org.keycloak.authentication.forms to tools.jackson.databind;
    opens org.keycloak.authorization.config to tools.jackson.databind;
    opens org.keycloak.authorization.protection.introspect to tools.jackson.databind;
    opens org.keycloak.authorization.protection.resource to tools.jackson.databind;
    opens org.keycloak.broker.oidc to tools.jackson.databind;
    opens org.keycloak.broker.spiffe to tools.jackson.databind;
    opens org.keycloak.credential to tools.jackson.databind;
    opens org.keycloak.forms.login.freemarker to tools.jackson.databind;
    opens org.keycloak.jose.jwe to tools.jackson.databind;
    opens org.keycloak.jose.jwk to tools.jackson.databind;
    opens org.keycloak.jose.jws to tools.jackson.databind;
    opens org.keycloak.json to tools.jackson.databind;
    opens org.keycloak.models to tools.jackson.databind;
    opens org.keycloak.models.credential.dto to tools.jackson.databind;
    opens org.keycloak.models.light to tools.jackson.databind;
    opens org.keycloak.partialimport to tools.jackson.databind;
    opens org.keycloak.protocol to tools.jackson.databind;
    opens org.keycloak.protocol.oauth2.cimd.clientpolicy.condition to tools.jackson.databind;
    opens org.keycloak.protocol.oauth2.cimd.clientpolicy.executor to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.issuance.credentialoffer to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.issuance.requiredactions to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.model to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.model.vcdm to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.encode to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.grants.ciba.channel to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.grants.ciba.clientpolicy.executor to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.grants.ciba.endpoints to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.par to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.representations to tools.jackson.databind;
    opens org.keycloak.representations to tools.jackson.databind;
    opens org.keycloak.representations.account to tools.jackson.databind;
    opens org.keycloak.representations.adapters.action to tools.jackson.databind;
    opens org.keycloak.representations.adapters.config to tools.jackson.databind;
    opens org.keycloak.representations.docker to tools.jackson.databind;
    opens org.keycloak.representations.dpop to tools.jackson.databind;
    opens org.keycloak.representations.idm to tools.jackson.databind;
    opens org.keycloak.representations.idm.authorization to tools.jackson.databind;
    opens org.keycloak.representations.oidc to tools.jackson.databind;
    opens org.keycloak.representations.provider to tools.jackson.databind;
    opens org.keycloak.representations.userprofile.config to tools.jackson.databind;
    opens org.keycloak.representations.workflows to tools.jackson.databind;
    opens org.keycloak.sdjwt.consumer to tools.jackson.databind;
    opens org.keycloak.services.clientpolicy.condition to tools.jackson.databind;
    opens org.keycloak.services.clientpolicy.executor to tools.jackson.databind;
    opens org.keycloak.services.clientregistration to tools.jackson.databind;
    opens org.keycloak.services.managers to tools.jackson.databind;
    opens org.keycloak.services.resources.account to tools.jackson.databind;
    opens org.keycloak.services.resources.admin to tools.jackson.databind;
    opens org.keycloak.services.util to tools.jackson.databind;
    opens org.keycloak.util to tools.jackson.databind;
}
