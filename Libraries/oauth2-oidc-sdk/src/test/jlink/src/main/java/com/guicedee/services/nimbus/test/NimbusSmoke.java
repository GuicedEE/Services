package com.guicedee.services.nimbus.test;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.gen.*;
import com.nimbusds.jwt.*;
import com.nimbusds.oauth2.sdk.dpop.*;
import com.nimbusds.oauth2.sdk.dpop.verifiers.*;
import com.nimbusds.oauth2.sdk.id.JWTID;
import com.nimbusds.oauth2.sdk.token.DPoPAccessToken;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import net.minidev.json.JSONValue;

import java.net.URI;
import java.util.Date;
import java.util.Set;

/** Executed by the linked runtime, exercising real crypto and bundled dependencies. */
public final class NimbusSmoke {
    public static void main(String[] args) throws Exception {
        var metadata = OIDCProviderMetadata.parse("""
                {"issuer":"https://issuer.example","authorization_endpoint":"https://issuer.example/auth",
                 "jwks_uri":"https://issuer.example/jwks","response_types_supported":["code"],
                 "subject_types_supported":["public"],"id_token_signing_alg_values_supported":["RS256"],
                 "claims_locales_supported":["en-US"]}
                """);
        check(metadata.getIssuer().getValue().equals("https://issuer.example"), "OIDC metadata");
        // JSON Smart's bean path invokes Accessors Smart and ASM at runtime.
        check(JSONValue.toJSONString(new Bean()).contains("linked"), "JSON bean serialization");
        var rsa = new RSAKeyGenerator(2048).generate();
        var jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256),
                new JWTClaimsSet.Builder().issuer("https://issuer.example").subject("test").build());
        jwt.sign(new RSASSASigner(rsa));
        check(SignedJWT.parse(jwt.serialize()).verify(new RSASSAVerifier(rsa.toPublicJWK())), "RSA JWT signature");
        var ec = new ECKeyGenerator(Curve.P_256).generate();
        var token = new DPoPAccessToken(jwt.serialize());
        var uri = URI.create("https://api.example/secured");
        var proof = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.ES256)
                .type(new JOSEObjectType("dpop+jwt")).jwk(ec.toPublicJWK()).build(),
                DPoPUtils.createJWTClaimsSet(new JWTID(), "GET", uri, new Date(), token));
        proof.sign(new ECDSASigner(ec));
        var parsed = SignedJWT.parse(proof.serialize());
        var confirmation = JWKThumbprintConfirmation.of(ec);
        var issuer = new DPoPIssuer("https://issuer.example");
        // Replay storage belongs to the application; this smoke test isolates library/module behavior.
        var verifier = new DPoPProtectedResourceRequestVerifier(Set.of(JWSAlgorithm.ES256), 60, 60, null);
        verifier.verify("GET", uri, issuer, parsed, token, confirmation, null, null);
        expectRejected(() -> verifier.verify("POST", uri, issuer, parsed, token, confirmation, null, null));
        expectRejected(() -> verifier.verify("GET", uri, issuer, parsed,
                new DPoPAccessToken("wrong-token"), confirmation, null, null));
        var wrongKey = JWKThumbprintConfirmation.of(new ECKeyGenerator(Curve.P_256).generate());
        expectRejected(() -> verifier.verify("GET", uri, issuer, parsed, token, wrongKey, null, null));
        check(!NimbusSmoke.class.getModule().getDescriptor().isAutomatic(), "named test module");
        check(!DPoPUtils.class.getModule().getDescriptor().isAutomatic(), "named Nimbus module");
        System.out.println("PASS: linked OIDC/JSON/ASM, RSA JWT, ES256 DPoP; rejected method, token and key mismatches");
    }

    public static final class Bean {
        public String status = "linked";
    }

    private static void check(boolean valid, String name) {
        if (!valid) throw new AssertionError(name);
    }

    private static void expectRejected(CheckedAction action) throws Exception {
        try {
            action.run();
        } catch (InvalidDPoPProofException | AccessTokenValidationException expected) {
            return;
        }
        throw new AssertionError("Invalid DPoP request accepted");
    }

    @FunctionalInterface
    private interface CheckedAction { void run() throws Exception; }
}
