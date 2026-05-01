# 🧰 GuicedEE Services


[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Modular](https://img.shields.io/badge/Modular-Level3-green)

A collection of popular third-party libraries repackaged with full **JPMS `module-info.java`** descriptors so they can participate in [GuicedEE](https://github.com/GuicedEE) Level 3 modularization.

Each service module provides proper `exports`, `requires`, `opens`, and `provides` directives, allowing libraries that ship as automatic or unnamed modules to work seamlessly on the module path alongside the rest of the GuicedEE ecosystem.

## 📁 Module Layout

| Category | Modules | Description |
|---|---|---|
| **Apache** | `Commons`, `CXF`, `POI` | Apache foundation libraries |
| **Database** | `msal4j`, `mssql-jdbc`, `postgresql` | JDBC drivers and database clients |
| **Google** | `guice-core`, `guice-assistedinject`, `guice-grapher`, `guice-jmx`, `guice-jndi`, `aop` | Google Guice and AOP |
| **Hibernate** | `hibernate-core`, `hibernate-c3p0`, `hibernate-jcache`, `hibernate-reactive`, `hibernate-validator` | Hibernate ORM and extensions |
| **Jakarta** | `jakarta-security-jacc` | Jakarta EE specifications |
| **JCache** | `cache-api`, `ehcache`, `hazelcast`, `hazelcast-hibernate`, `cache-annotations-ri-common`, `cache-annotations-ri-guice` | JSR 107 / JCache providers |
| **JNI** | `jna-platform`, `nrjavaserial` | Native interface libraries |
| **Libraries** | `bcrypt`, `BitronixTransactionManager`, `cloudevents`, `ibm-mq`, `jandex`, `javassist`, `json`, `junit-jupiter`, `kafka-client`, `mapstruct`, `openpdf`, `rabbitmq-client`, `scram`, `swagger`, `testcontainers`, `uadetector-core`, `uadetector-resources` | General-purpose libraries |
| **MicroProfile** | `config-core`, `health-core`, `metrics-core`, `telemetry-core` | Eclipse MicroProfile APIs |
| **Vert.x** | `vertx-mutiny`, `vertx-rabbitmq` | Vert.x ecosystem modules |

## 📦 Usage

Add the specific service module you need as a dependency — each one is published independently:

```xml
<dependency>
    <groupId>com.guicedee.modules.services</groupId>
    <artifactId>hibernate-core</artifactId>
</dependency>
```

Then `requires` it in your `module-info.java`:

```java
module my.app {
    requires org.hibernate.orm.core;
}
```

## 🤝 Contributing

PRs welcome — especially for adding `module-info.java` descriptors to libraries that don't yet have them.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)
