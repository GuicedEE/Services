# Okio JPMS wrapper

Prepared for the upcoming **2.6.0** release. See the [release notes](../../../RELEASE-2.6.0.md) for validation and publication status.

`com.guicedee.modules.services:okio` retains module name `okio`, exports the
upstream packages and requires external `kotlin.stdlib`. Upstream `okio-jvm`
is automatic; this descriptor allows Moneta -> OkHttp -> Okio to link.
Versions are managed by Versioner and StandaloneBOM. No relocation is applied.
