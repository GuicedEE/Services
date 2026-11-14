# Commercetools grouped JPMS module

Prepared for the upcoming **2.6.0** release. See the [release notes](../../../RELEASE-2.6.0.md) for validation and publication status.

Coordinates: `com.guicedee.modules.services:commercetools`.
Module: `commercetools.sdk.java.api` (the upstream API module name).

Bundles the Java API, GraphQL request/response models, RMF base and HTTP client.
Versioner manages the upstream SDK version through StandaloneBOM; consumers use
the GuicedEE BOM. This is the SDK GraphQL client, not a GraphQL server engine.

Supporting libraries are relocated to avoid application split packages.
Javassist is excluded from the bundle and supplied by the existing modular
`com.guicedee.modules.services:javassist` dependency (`requires javassist`).
The async HTTP client retains its package name because its configuration
resources use that namespace. ServiceLoader declarations are explicit.

Build Versioner, StandaloneBOM and GuicedEE BOM before installing the shade.
Use `-Dmaven.jar.forceCreation=true`. Publication and a separate repository
have not been provisioned.

## Javadoc

`mvn verify` documents the Commercetools SDK (including GraphQL models and HTTP
client APIs) and RMF against the current shaded binary. Relocated supporting
libraries are excluded from the documentation scope. Generated source archives,
extracted sources and previous API pages are cleared before shading to prevent
stale relocation output. The extracted documentation sources normalize excluded namespace imports to
match the binary, working around shade source-rewriting differences.
Javadoc errors remain fatal.

Bouncy Castle is external: `bcprov-jdk18on`, `bcpkix-jdk18on` and
`bctls-jdk18on` use StandaloneBOM/Versioner management. `bcutil-jdk18on`
is managed at the same version and resolves transitively. The entire
`org.bouncycastle` namespace is excluded from relocation and shading so
Netty adapters load the signed modular provider JARs unchanged.

The wrapper now externalizes Netty (including native transports), SLF4J,
Jackson 3 core/databind and shared annotations, Commons IO/Lang, Failsafe,
Java Money/Moneta and the modular Reactive Streams wrapper. Moneta's JPMS
requirement on OkHttp is supplied explicitly; Okio uses a small modular wrapper.
SLF4J now resolves the application's logging provider. No Jackson 2 core or
databind is part of this wrapper's resolved dependency graph.
