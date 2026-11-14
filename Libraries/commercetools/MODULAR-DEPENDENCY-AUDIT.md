# Commercetools shade audit

Verified against the resolved dependency tree and JAR contents.

| Library | Current status | Modularization finding |
|---|---|---|
| Bouncy Castle | External | Provider, PKIX, TLS and utility modules; native names preserved |
| Javassist | External | Existing GuicedEE modular wrapper |
| Zstd JNI | External | New descriptor-correcting wrapper; upstream 1.5.7-20 fails jlink |
| Netty and native epoll/kqueue transports | External | Explicit modules; linked SPI/scanner capability checks passed |
| SLF4J | External | Uses the application Log4j SLF4J provider |
| Jackson core/databind/annotations | External | Jackson 3 core/databind only; shared Jackson annotations module retained |
| Commons IO/Lang, Failsafe, Java Money/Moneta | External | Explicit modules; Moneta also requires external OkHttp and modular Okio wrapper |
| AsyncHttpClient, Completable Futures, Reflections, netty-reactive-streams | Bundled | Resolved upstream JARs are automatic modules; cannot simply externalize for jlink |
| Reactive Streams | External | Existing GuicedEE modular Reactive Streams wrapper |
| Brotli, LZ4, Conscrypt | Adapters only | Implementations are absent; no bundled implementation to remove |
| Snappy | Netty codec | This is Netty's own Java codec, not bundled snappy-java |

The explicit-module candidates above have now been extracted. Remaining automatic
SDK support libraries stay grouped; no Jackson 2 core/databind is introduced.

## Extraction validation

- Commercetools `install` passed with Javadoc enabled.
- Both dedicated benchmark jlink modules built successfully on Oracle JDK.
- Linked SPI and scanner full capability checks passed, including OAuth, REST,
  GraphQL and Firestore: `benchmarks/results/20261114-195109/summary.json`.
- Java Money constructed EUR 12.34; SLF4J resolved Log4jLoggerFactory.
- Binary inspection found no classes from the extracted libraries in the shade.
- The resolved graph contains Jackson 3 core/databind and shared annotations;
  no Jackson 2 core/databind. OkHttp/Okio/Kotlin are external dependencies used
  to satisfy Moneta's module graph.
