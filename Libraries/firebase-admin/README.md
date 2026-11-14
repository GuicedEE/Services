# Firebase Admin grouped module

Prepared for the upcoming **2.6.0** release. See the [release notes](../../../RELEASE-2.6.0.md) for validation and publication status.

`com.guicedee.modules.services:firebase-admin` exposes the named module
`firebase.admin`. Upstream Firebase Admin is managed in StandaloneBOM through
Versioner's `firebase.version`; consumers use the GuicedEE BOM.

The Google SDK dependency closure is bundled so Firestore, Auth and API-common
packages share one module. Supporting libraries are relocated beneath
`com.google.firebase.internal.shaded` to avoid application split packages.
Service descriptors are merged and their providers registered in module-info.
Inherited test libraries and annotation processors are excluded.

Build Versioner, StandaloneBOM and guicedee-bom before installing this module.
Use `-Dmaven.jar.forceCreation=true` for rebuilds. The benchmark common module
requires only `firebase.admin` for its Google APIs. A separate repository and
publication workflow have not been provisioned.

Validated with Firebase Admin 9.12.0: benchmark common compiles on the module
path; a standalone module-path smoke initializes Firebase and Firestore and
constructs a document reference against an emulator configuration. No network
request or complete benchmark run is covered by that smoke.

## Javadoc

`mvn verify` generates Firebase Admin API documentation against the freshly built
shaded binary. Private relocated implementation packages and bundled third-party
SDK source projects are excluded from the documentation scope. Generated shaded
source archives, extracted sources and old API pages are cleared at initialize
to prevent stale relocations or pages from surviving incremental builds.
Javadoc failures remain fatal; no skip flag is required.
