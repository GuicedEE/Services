# Nimbus OAuth2/OIDC JPMS service

`com.guicedee.modules.services:oauth2-oidc-sdk:2.2.3` supplies the explicit
`oauth2.oidc.sdk` module for jlink. Import `com.guicedee:guicedee-bom:2.2.3`
in Maven dependency management, then use:

```xml
<dependency>
    <groupId>com.guicedee.modules.services</groupId>
    <artifactId>oauth2-oidc-sdk</artifactId>
</dependency>
```

```java
module my.app {
    requires oauth2.oidc.sdk;
    requires com.nimbusds.jose.jwt;
}
```

The JOSE dependency is transitive, so its explicit `requires` is optional. It is
shown for applications that directly use the JOSE/JWT API.

| Artifact | Version | Packaging |
| --- | --- | --- |
| com.nimbusds:oauth2-oidc-sdk | 11.38.2 | Bundled; original module name retained |
| com.nimbusds:content-type | 2.3 | Bundled |
| com.nimbusds:lang-tag | 1.7 | Bundled |
| net.minidev:json-smart | 2.6.0 | Bundled |
| net.minidev:accessors-smart | 2.6.0 | Bundled |
| com.github.stephenc.jcip:jcip-annotations | 1.0-1 | Bundled, relocated to an unexported annotation package |
| com.nimbusds:nimbus-jose-jwt | 10.9.1 | External native JPMS module, com.nimbusds.jose.jwt |
| org.ow2.asm:asm | BOM-managed (9.10.1 at introduction) | External native JPMS module, org.objectweb.asm |

JOSE/JWT already ships a Java 9 module descriptor. Reusing it preserves its
crypto-provider module requirements and avoids maintaining a duplicate shade.
The SDK and bundled libraries export their public packages, including JSON and
language/content types used in Nimbus APIs. JCIP annotations remain private.
No input JAR contains a service-provider registration needing a `provides` clause.

Replace the upstream SDK dependency; do not put its original JAR or any of the
bundled dependency JARs alongside this service. Exclude those artifacts if another
dependency brings them in. Published service dependencies contain only JOSE/JWT,
ASM, and the parent's provided Lombok dependency; bundled artifacts do not leak
into consumers' module paths.

## Security integration

Core OAuth2/OIDC and JDK-backed JOSE/DPoP operations are supported. The shade does
not install a GuicedEE authentication provider or a request handler. The application
must validate token signatures, trusted issuer, audience and lifetime before
authorizing requests. Nimbus's DPoP verifier checks the proof, HTTP method/URI,
access-token hash and key binding. Supply replay protection shared across instances
and reject requests if trust configuration or replay storage is unavailable.

See the [upstream DPoP guide](https://connect2id.com/products/nimbus-oauth-openid-connect-sdk/examples/oauth/dpop).
Optional upstream SAML/OpenSAML, servlet, Tink, Bouncy Castle and other optional
integrations are not bundled or enabled by this service. Using SDK adapters for
those integrations requires their dependencies and corresponding JPMS read edges.

## Build and validate

Use Maven 4 and JDK 25+. Install the changed Versioner, StandaloneBOM and GuicedEE
BOM first, in that order (`mvn -B -N install` in each module). From this directory:

```powershell
mvn -B install -DskipTests -Dmaven.javadoc.skip=true
./verify-jlink.ps1 -JdkHome $env:JAVA_HOME
```

The smoke consumer imports the GuicedEE BOM and declares only this service. Its
runtime graph must contain exactly the service, JOSE/JWT and ASM. The script links
and runs a fresh runtime image, exercising OIDC metadata, JSON bean serialization
(Accessors Smart/ASM), RSA JWT signing and ES256 DPoP verification. Wrong HTTP
method, token and key binding must be rejected. It does not require Keycloak or a
database and does not test application replay storage.

DevSuite's `services` profile includes this module. A standalone GitHub service
repository and its reusable Projects Builder workflow can be created when this
service is published; this change does not create a remote repository or release.
