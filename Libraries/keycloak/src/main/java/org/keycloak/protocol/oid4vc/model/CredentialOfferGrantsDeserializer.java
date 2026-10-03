package org.keycloak.protocol.oid4vc.model;

import java.util.LinkedHashMap;
import java.util.Map;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.exc.InvalidFormatException;

import static org.keycloak.protocol.oid4vc.model.AuthorizationCodeGrant.AUTH_CODE_GRANT_TYPE;
import static org.keycloak.protocol.oid4vc.model.PreAuthorizedCodeGrant.PRE_AUTH_GRANT_TYPE;

/**
 * Jackson 3 override supplied by the {@code keycloak-api} compile-only bundle.
 * <p>
 * Upstream extends {@code com.fasterxml.jackson.databind.JsonDeserializer} and reaches the mapper
 * through {@code JsonParser.getCodec()}. Jackson 3 removed {@code ObjectCodec}; the surrounding
 * {@link DeserializationContext} is now the entry point for tree reads and tree conversions.
 */
public final class CredentialOfferGrantsDeserializer extends ValueDeserializer<Map<String, CredentialOfferGrant>> {

    @Override
    public Map<String, CredentialOfferGrant> deserialize(JsonParser p, DeserializationContext ctx) {

        Map<String, CredentialOfferGrant> grants = new LinkedHashMap<>();
        JsonNode node = ctx.readTree(p);

        for (String grantType : node.propertyNames()) {
            JsonNode valueNode = node.get(grantType);

            Class<? extends CredentialOfferGrant> target;
            if (AUTH_CODE_GRANT_TYPE.equals(grantType)) {
                target = AuthorizationCodeGrant.class;
            } else if (PRE_AUTH_GRANT_TYPE.equals(grantType)) {
                target = PreAuthorizedCodeGrant.class;
            } else {
                throw new InvalidFormatException(
                        p, "Unknown grant type key: " + grantType, grantType, CredentialOfferGrant.class);
            }

            grants.put(grantType, ctx.readTreeAsValue(valueNode, target));
        }
        return grants;
    }
}

