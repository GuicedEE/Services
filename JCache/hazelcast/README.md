# Hazelcast and Jackson 3

`hazelcast-all` keeps Hazelcast 5.7's public API and JPMS module name, `com.hazelcast.all`.
Its JSON paths use the application's BOM-managed Jackson 3 core/databind (currently 3.2.3).
The lightweight Jackson Jr objects and annotation-support helpers are shaded privately at
that same managed version. Jackson's shared `com.fasterxml.jackson.annotation` artifact is
still required by Jackson 3; it is not a Jackson 2 core/databind runtime.

The upstream Hazelcast JAR embeds Jackson 2.21.2 and Jackson 3.1.2. The wrapper removes
those implementations, their service descriptors, and their Maven version metadata.
Hazelcast's Jackson 3 references are relocated to the shared `tools.jackson` modules.
The inspected upstream Hazelcast bytecode has no Jackson 2 callers outside Jackson itself.
Do not replace this with metadata-only exclusions or a blanket Jackson 2-to-3 relocation:
the APIs are not generally binary compatible.

`mvn -B -ntp verify -Dmaven.javadoc.skip=true -Dgpg.skip=true` in this directory exercises
the packaged named module, with no upstream Hazelcast JAR and no Jackson 2 runtime on its
module path. The integration tests check classes, bytecode, embedded version metadata,
Jet JSON round-trips, and JSON-value replication between two cluster members.
Run the GuicedEE Hazelcast consumer tests after installing the rebuilt wrapper locally.

## Cloud logging

GuicedEE Client and Inject use Log4j JSON Template Layout with its `JsonLayout.json`
compatibility template. This removes the legacy JsonLayout dependency on Jackson 2.
Output is compact newline-delimited JSON; exceptions follow the template's flat stack-trace
representation. The website backend and its jlink module no longer request Jackson 2 databind.
The template layout is a named JPMS module, included through the client's module descriptor.

## Remaining migration inventory (2026-10-16)

These changes remove the two embedded Jackson copies responsible for the reported Hazelcast
scan findings. A fresh platform build and image scan are still required to confirm closure.
Existing published 2.4.0 artifacts and deployed images are not changed by a local rebuild;
applications must adopt the coordinated 2.4.1 release and rebuild their images.

A full DevSuite reactor runtime dependency audit also found Jackson 2 paths outside Hazelcast:

- Azure Identity / Azure Core, including the optional ActivityMaster Core dependency.
- Cassandra drivers exposed by the Vert.x Cassandra wrapper and optional Mutiny/persistence paths.
- The optional CXF REST OpenAPI Swagger integration.
- The optional CloudEvents Jackson adapter.
- Testcontainers, whose private shaded Jackson 2 implementation is test tooling; its Cassandra
  adapter also resolves Jackson 2. Application usages inspected declare Testcontainers as test scope.

Those adapters require API-compatible migrations and their own runtime tests. They have not
been made Jackson 3 compatible by exclusions. Therefore this change does not certify every
optional DevSuite service or the test tooling as Jackson 2 free. See the reproducible reactor
inventory in `logs/jackson2-runtime-dependencies.log` in the DevSuite workspace.
