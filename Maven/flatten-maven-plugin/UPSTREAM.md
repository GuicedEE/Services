# GuicedEE Flatten Maven Plugin

Source: https://github.com/mojohaus/flatten-maven-plugin, tag `1.8.0`, commit `1b211937fc61b40e1c38a59fb882c0910032b089` (Apache-2.0; upstream license and notices retained).

Coordinates: `com.guicedee.maven:flatten-maven-plugin:2.0`.

This build tool intentionally uses the independent upstream MojoHaus parent. It must be installed before the GuicedEE BOM/parent reactor and must not inherit or import those projects.

The patch adapts legacy model validation for models already accepted by Maven 4. The original flatten modes, dependency transformations, profile handling and 4.0.0 consumer output remain upstream code. It does not enable Maven 4 native consumer-POM generation. Native-only model features must be covered before claiming compatibility beyond the tested fixtures; this is not a new Maven model builder.

Build and regression checks: `mvn -f GuicedEE/services/Maven/flatten-maven-plugin/pom.xml install`.

## Verified behavior

The focused compatibility runner (`python GuicedEE/services/Maven/flatten-maven-plugin/verify-compatibility.py`) performs nine builds: upstream 1.8.0 on model 4.0, and the fork on models 4.0 and 4.1, for a library with inherited metadata, active profiles, optional dependencies and exclusions; a BOM with a resolved managed version; and a shaded JAR. It compares the resulting XML structure and installed POMs. Shaded Gson must remain absent. Run this focused suite when changing the plugin; do not repeat it in every consuming project.

`build.ps1` and `build.sh` install this independent module before loading the main reactor. Regular bootstrapping skips tests; plugin development runs the unit and focused compatibility suites. Keep this module outside the main service reactor. A fresh environment must bootstrap it before invoking a shared parent/BOM directly. The `release-central` profile builds signed binary, source and Javadoc artifacts and publishes through the Central Portal using the `central` Maven server and `MAVEN_GPG_PASSPHRASE` environment variable. It retains the independent MojoHaus parent and upstream attribution. Release with:

```powershell
mvn -B -ntp -N -f GuicedEE/services/Maven/flatten-maven-plugin/pom.xml -Prelease-central deploy '-Dcentral.publishing.skip=false' '-Daether.syncContext.named.factory=noop'
```

Check the deployment state and public Maven Central files before reporting publication complete. Versions on Central are immutable.

The version adapter accepts exactly 4.1.0 for legacy validation, preserves the original model and delegates all remaining validation. It does not waive future model versions. Native-only features such as inferred coordinates, new dependency types and BOM packaging need explicit coverage before being supported by this legacy reconstruction path. Existing explicit-coordinate project structures are the validated scope.

Version `2.0` was published to Maven Central on 26 October 2026 (deployment `9b5e453b-0f72-4a50-94b0-6edbd7ae6742`). Public POM, binary, sources and Javadoc SHA-256 hashes were compared with the release artifacts. Repository: https://repo.maven.apache.org/maven2/com/guicedee/maven/flatten-maven-plugin/2.0/
