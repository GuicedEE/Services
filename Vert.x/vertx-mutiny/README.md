# Vert.x Mutiny — GuicedEE Modular Repackage

[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Vert.x 5](https://img.shields.io/badge/Vert.x-5%2B-green)
![Modular](https://img.shields.io/badge/Modular-Level3-green)
![Maven 4](https://img.shields.io/badge/Maven-4-green)

A **modular repackage** of the [SmallRye Mutiny Vert.x bindings](https://github.com/smallrye/smallrye-mutiny-vertx-bindings) with a proper **JPMS `module-info.java`** descriptor (`module io.vertx.mutiny`), Vert.x 5 compatibility fixes, and SmallRye Context Propagation shaded in so the entire reactive Vert.x stack works cleanly on the module path.

This module shades the SmallRye Mutiny Vert.x core, web, SQL client, and other bindings into a single module and provides patched `Vertx` and `HttpServer` wrappers updated for the Vert.x 5 API (Future-based instead of callback-based).

## ✨ Features

- **Full JPMS module** — ships `module-info.java` (`module io.vertx.mutiny`) with explicit `exports`, `requires`, and `opens` directives
- **Vert.x 5 compatible** — `Vertx` and `HttpServer` wrapper classes updated to use Vert.x 5 Future-based APIs
- **Mutiny-first API** — every async method returns `Uni<T>` with `*AndAwait()` (blocking) and `*AndForget()` (fire-and-forget) variants
- **Context Propagation** — SmallRye Context Propagation (`io.smallrye.context`) shaded in for seamless CDI/reactive context flow
- **All-in-one shade** — smallrye-mutiny-vertx-core, vertx-web, SQL clients (PostgreSQL, MySQL, MSSQL, Oracle, DB2, Cassandra, MongoDB), Redis, Kafka, RabbitMQ, AMQP, Mail, and Web Client bindings included
- **Consumer-friendly handlers** — handler methods accept `java.util.function.Consumer` in addition to Vert.x `Handler`

## 📦 Installation

```xml
<dependency>
    <groupId>com.guicedee.modules.services</groupId>
    <artifactId>vertx-mutiny</artifactId>
</dependency>
```

Then `requires` it in your `module-info.java`:

```java
module my.app {
    requires io.vertx.mutiny;
}
```

## 🚀 Quick Start

### Creating a Vert.x instance

```java
import io.vertx.mutiny.core.Vertx;

Vertx vertx = Vertx.vertx();
```

### Starting an HTTP server

```java
import io.vertx.mutiny.core.http.HttpServer;

HttpServer server = vertx.createHttpServer();

server.requestHandler(req -> {
    req.response().end("Hello from Mutiny!");
});

server.listenAndAwait(8080, "0.0.0.0");
```

### Async with Uni

```java
server.listen(8080, "0.0.0.0")
    .subscribe().with(
        s -> System.out.println("Listening on port " + s.actualPort()),
        err -> System.err.println("Failed: " + err.getMessage())
    );
```

### Execute blocking code

```java
Uni<String> result = vertx.executeBlocking(() -> {
    // long-running computation
    return "computed value";
});
```

## 📐 API Pattern

Every async operation follows a consistent triple-method pattern:

| Method | Behaviour |
|---|---|
| `operation()` | Returns `Uni<T>` — subscribe to trigger |
| `operationAndAwait()` | Blocks until complete, returns result |
| `operationAndForget()` | Subscribes and discards the outcome |

For example:
- `listen(port)` → `Uni<HttpServer>`
- `listenAndAwait(port)` → `HttpServer` (blocking)
- `listenAndForget(port)` → `void`

## 🗺️ Module Graph

```
io.vertx.mutiny
 ├── io.vertx.core              (Vert.x 5 core)
 ├── io.vertx.web               (Vert.x Web, static)
 ├── io.smallrye.mutiny          (Mutiny reactive types)
 ├── io.smallrye.common.ref      (SmallRye common)
 ├── jakarta.cdi                 (CDI annotations)
 └── io.vertx.codegen.api        (codegen, static)
```

### Exported Packages

| Package | Description |
|---|---|
| `io.vertx.mutiny.core` | Mutiny wrapper for `Vertx` — the main entry point |
| `io.vertx.mutiny.core.http` | Mutiny wrappers for `HttpServer`, `HttpClient`, WebSocket |
| `io.vertx.mutiny.ext.web` | Mutiny wrappers for Vert.x Web `Router`, `Route` |
| `io.vertx.mutiny.ext.web.client` | Mutiny wrapper for `WebClient` |
| `io.vertx.mutiny.ext.web.codec` | Body codecs for web client |
| `io.vertx.mutiny.ext.web.handler` | Route handlers |
| `io.vertx.mutiny.ext.web.multipart` | Multipart form support |
| `io.vertx.mutiny.ext.web.sstore` | Session stores |
| `io.smallrye.mutiny.vertx` | SmallRye Mutiny–Vert.x integration internals |
| `io.smallrye.mutiny.vertx.core` | Core Mutiny–Vert.x helpers |
| `io.smallrye.mutiny.vertx.codegen` | Codegen support types |
| `io.smallrye.mutiny.vertx.impl` | Implementation helpers |
| `io.smallrye.context` | SmallRye Context Propagation |

## 🔧 What Changed from Upstream

| Area | Change |
|---|---|
| **Module descriptor** | Added `module-info.java` with full `exports`, `requires`, `opens` |
| **Vert.x 5 migration** | `Vertx.java` and `HttpServer.java` rewritten for Future-based Vert.x 5 API |
| **Shading** | All SmallRye Mutiny Vert.x bindings + Context Propagation shaded into one module |
| **Deprecated removal** | Removed methods dropped in Vert.x 5 (e.g. `nettyEventLoopGroup()`, `requestStream()`) |
| **Consumer handlers** | All handler methods offer `Consumer<T>` overloads alongside `Handler<T>` |

## 🔌 Shaded Artifacts

The following SmallRye Reactive artifacts are shaded into this module:

- `smallrye-mutiny-vertx-core` — Mutiny bindings for Vert.x Core
- `smallrye-mutiny-vertx-web` — Mutiny bindings for Vert.x Web
- `smallrye-mutiny-vertx-web-client` — WebClient bindings
- `smallrye-mutiny-vertx-sql-client` — Generic SQL client bindings
- `smallrye-mutiny-vertx-pg-client` — PostgreSQL
- `smallrye-mutiny-vertx-mysql-client` — MySQL
- `smallrye-mutiny-vertx-mssql-client` — Microsoft SQL Server
- `smallrye-mutiny-vertx-oracle-client` — Oracle
- `smallrye-mutiny-vertx-db2-client` — DB2
- `smallrye-mutiny-vertx-mongo-client` — MongoDB
- `smallrye-mutiny-vertx-cassandra-client` — Cassandra
- `smallrye-mutiny-vertx-redis-client` — Redis
- `smallrye-mutiny-vertx-kafka-client` — Kafka
- `smallrye-mutiny-vertx-rabbitmq-client` — RabbitMQ
- `smallrye-mutiny-vertx-amqp-client` — AMQP
- `smallrye-mutiny-vertx-mail-client` — Mail
- `smallrye-mutiny-vertx-config` — Config
- `smallrye-mutiny-vertx-auth-sql-client` — Auth SQL
- `smallrye-mutiny-vertx-runtime` — Runtime helpers
- `mutiny-smallrye-context-propagation` — Context propagation
- `smallrye-context-propagation` — SmallRye Context Propagation core

## 🤝 Contributing

Issues and pull requests are welcome — especially for upstream version bumps, additional Vert.x 5 API patches, and new SQL client bindings.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

