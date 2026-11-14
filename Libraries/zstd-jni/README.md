# Zstd JNI modular wrapper

Prepared for the upcoming **2.6.0** release. See the [release notes](../../../RELEASE-2.6.0.md) for validation and publication status.

Preserves module name `com.github.luben.zstd_jni`, package names and native library
resources. Versioner/StandaloneBOM manages upstream `com.github.luben:zstd-jni`.
The 1.5.7-20 descriptor incorrectly exports a META-INF versioned package and fails
jlink. This wrapper replaces only the descriptor and packaging metadata, retaining
multi-release behavior and byte-identical native resources. Consumers use
`com.guicedee.modules.services:zstd-jni` through the GuicedEE BOM.
