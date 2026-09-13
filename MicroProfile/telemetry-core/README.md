# Telemetry Core

The shaded OpenTelemetry module uses Jackson 3 (`tools.jackson.core` and
`tools.jackson.databind`). The incubator configuration API's `ObjectMapper`
reference is relocated to Jackson 3; its `convertValue(Object, Class)` call is
verified against the managed Jackson version. Jackson 2 core and databind are
not required. Jackson annotations retain their upstream `com.fasterxml.jackson`
namespace.

OpenTelemetry 1.64.0 writes OTLP JSON with its own buffered encoder. Older
telemetry artifacts whose `JsonSerializer` references Jackson 2 must be replaced
with the rebuilt module; changing only the application's module descriptor does
not update that embedded bytecode.

Run `mvn verify` from this directory. The integration tests load the packaged
shade as a named JPMS module without Jackson 2 core/databind, check its bytecode,
exercise configuration conversion with Jackson 3, and serialize a log record on
the batch processor's worker thread.
