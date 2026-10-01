# Google Guice � GuicedEE Modular Fork

[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)
![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Modular](https://img.shields.io/badge/Modular-Level3-green)

A **full source copy** of [Google Guice 7](https://github.com/google/guice) repackaged with a proper **JPMS `module-info.java`** descriptor, modular access fixes for **JDK 25**, and a set of **SPI extension points** (`com.google.inject.gee`) that allow downstream modules to plug in custom annotations for injection, binding, scoping, and naming � without forking Guice internals themselves.

> **Upstream policy:** this module tracks Google Guice releases and carries documented GuicedEE extensions for JPMS, annotation SPIs, and local dependency scoping. Local scoping follows the proposal in google/guice#1949 while it remains a draft upstream.

## Features

- **Full JPMS module** � ships a real `module-info.java` (`module com.google.guice`) with explicit `exports`, `requires`, and `uses` directives
- **JDK 25 compatibility** � access-level and reflection fixes so Guice runs cleanly on the module path without `--add-opens` hacks
- **Jakarta namespace** � uses `jakarta.inject` and `jakarta.annotation` (not `javax.*`)
- **SPI-driven extensibility** � six `ServiceLoader`-based SPIs let you register custom annotations for injection points, scopes, bindings, and naming without touching Guice source
- **Local dependency scopes** � apply a scope to an injected constructor parameter or field without changing the dependency binding for other consumers
- **Multibindings built-in** � `MapBinder`, `Multibinder`, and `OptionalBinder` are included in the same module (no separate `guice-multibindings` artifact needed)
- **Drop-in replacement** � all public Guice APIs remain unchanged; existing `@Inject`, `@Provides`, `@Singleton`, `bind()` EDSL code works as-is

## Installation

```xml
<dependency>
    <groupId>com.guicedee.modules.services</groupId>
    <artifactId>guice</artifactId>
</dependency>
```

Then `requires` it in your `module-info.java`:

```java
module my.app {
    requires com.google.guice;
}
```

## Local scopes on injected dependencies

Scope annotations can now be placed on an injectable constructor parameter or injected field. The
scope wraps the dependency's existing binding for that consumer only. The original binding keeps
its own scope for direct injection and other consumers. Parameters with the same dependency key
and scope annotation in one constructor share a scoped provider; fields with the same key and
scope annotation in one injected type do the same. Other constructors or fields get separate
providers. An existing scoped binding remains scoped underneath the local wrapper.

```java
final class Handler {
    private final RequestState constructorState;

    @Inject
    Handler(@RequestScoped RequestState state) {
        this.constructorState = state;
    }

    @Inject @RequestScoped RequestState fieldState;
}
```

This also works with `@Singleton` and registered custom scopes. Method-injection and
provider-method parameters retain their existing behavior. A local scope is resolved when the
injector builds the consumer, so an unregistered scope fails injector creation.

#### Register `@RequestScoped`

1. Define a runtime scope annotation with the targets you intend to use. `@ScopeAnnotation`
   is built in, so this annotation needs no `ScopeAnnotationProvider`.

```java
@ScopeAnnotation
@Retention(RUNTIME)
@Target({TYPE, METHOD, PARAMETER, FIELD})
public @interface RequestScoped {}
```

2. Provide a Guice `Scope` that caches values by `Key` in the active request context. Its
   `scope(key, unscoped)` provider should return the cached value within one request, create a
   new value in the next request, and throw `OutOfScopeException` when no request is active.
   The request context implementation belongs to the application; `guice-core` does not
   create or propagate request boundaries.

For a synchronous request, a minimal scope implementation is:

```java
public final class MyRequestScope implements Scope {
    public static final MyRequestScope INSTANCE = new MyRequestScope();
    private final ThreadLocal<Map<Key<?>, Object>> current = new ThreadLocal<>();

    public void enter() {
        if (current.get() != null) throw new IllegalStateException("Request already active");
        current.set(new HashMap<>());
    }

    public void exit() {
        current.remove();
    }

    @Override
    public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
        return () -> {
            Map<Key<?>, Object> values = current.get();
            if (values == null) throw new OutOfScopeException("No active request");
            if (values.containsKey(key)) {
                @SuppressWarnings("unchecked") T cached = (T) values.get(key);
                return cached;
            }
            T value = unscoped.get();
            if (!Scopes.isCircularProxy(value)) values.put(key, value);
            return value;
        };
    }
}
```

3. Register the concrete annotation and scope implementation through the existing SPI:

```java
public final class RequestScopeBinding implements BindScopeProvider {
    @Override
    public void bindScope(Binder binder) {
        binder.bindScope(RequestScoped.class, MyRequestScope.INSTANCE);
    }
}
```

```java
module my.scopes {
    requires com.google.guice;
    provides com.google.inject.gee.BindScopeProvider
        with my.scopes.RequestScopeBinding;
}
```

On the classpath, register `my.scopes.RequestScopeBinding` in
`META-INF/services/com.google.inject.gee.BindScopeProvider` instead. Open the scope at request
entry with `MyRequestScope.INSTANCE.enter()` and close it in a `finally` block with
`MyRequestScope.INSTANCE.exit()`. The `ThreadLocal` example is for synchronous requests; use
context-aware storage and propagation for asynchronous processing.

For an annotation marked with a different scope **meta-annotation** (such as
`jakarta.inject.Scope`), register the meta-annotation type through
`ScopeAnnotationProvider` as well. Return the marker type from that SPI, not
`RequestScoped.class`. A concrete annotation still needs a `BindScopeProvider` binding.

## SPI Extension Points

The `com.google.inject.gee` package exposes six SPIs loaded via `java.util.ServiceLoader`. Declare `uses` in the Guice module and `provides` in your own module to extend Guice's annotation handling at runtime.

| SPI | Purpose |
|---|---|
| `InjectionPointProvider` | Register custom annotations that mark injection points (beyond `@Inject`) |
| `InjectorAnnotationsProvider` | Declare additional annotations that Guice should treat as injector annotations |
| `BindScopeProvider` | Programmatically bind custom scope annotations during module configuration |
| `ScopeAnnotationProvider` | Supply scope meta-annotations used to recognise concrete scope annotations (for example, `jakarta.inject.Scope`) |
| `BindingAnnotationProvider` | Supply a list of annotation classes to be recognised as binding annotations (like `@Named`) |
| `NamedAnnotationProvider` | Map custom naming annotations to Guice's `@Named`, enabling alternative naming strategies |

### Example � Custom Injection Annotation

**1. Define your annotation:**

```java
@Retention(RUNTIME)
@Target({FIELD, METHOD, CONSTRUCTOR})
public @interface MyInject {}
```

**2. Implement the SPI:**

```java
public class MyInjectionPointProvider implements InjectionPointProvider {
    @Override
    public Class<? extends Annotation> injectionPoint(AnnotatedElement member) {
        return member.isAnnotationPresent(MyInject.class) ? MyInject.class : null;
    }
}
```

**3. Register in `module-info.java`:**

```java
module my.extensions {
    requires com.google.guice;
    provides com.google.inject.gee.InjectionPointProvider
        with my.extensions.MyInjectionPointProvider;
}
```

Now Guice will inject members annotated with `@MyInject` in addition to `@Inject`.

### Example � Custom Scope Annotation

A scope marked with `@ScopeAnnotation` can target injectable parameters and fields. Bind
its implementation through `BindScopeProvider`:

```java
@Retention(RUNTIME)
@Target({TYPE, METHOD, PARAMETER, FIELD})
@ScopeAnnotation
public @interface LocalSingleton {}

public class LocalSingletonBinding implements BindScopeProvider {
    @Override
    public void bindScope(Binder binder) {
        binder.bindScope(LocalSingleton.class, Scopes.SINGLETON);
    }
}
```

```java
module my.scopes {
    requires com.google.guice;
    provides com.google.inject.gee.BindScopeProvider
        with my.scopes.LocalSingletonBinding;
}
```

For an alternative scope meta-annotation, register the marker type with
`ScopeAnnotationProvider` rather than returning the concrete scope annotation:

```java
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface MyScopeMarker {}

public class MyScopes implements ScopeAnnotationProvider {
    @Override
    public List<Class<? extends Annotation>> getScopeAnnotations() {
        return List.of(MyScopeMarker.class);
    }
}
```

Register `MyScopes` as a `ScopeAnnotationProvider` service and bind each concrete scope
through `BindScopeProvider`.

### Example � Custom Binding Annotation

```java
public class MyBindings implements BindingAnnotationProvider {
    @Override
    public List<Class<? extends Annotation>> getBindingAnnotations() {
        return List.of(MyQualifier.class);
    }
}
```

## Module Graph

```
com.google.guice
  +-- com.google.common                    (Guava)
  +-- aopalliance                          (AOP Alliance)
  +-- jakarta.inject                       (static)
  +-- jakarta.annotation                   (static)
  +-- org.objectweb.asm                    (ASM bytecode)
  +-- java.logging
```

### Exported Packages

| Package | Description |
|---|---|
| `com.google.inject` | Core API � `Injector`, `Module`, `Binder`, `Key`, `TypeLiteral`, `Provider`, `Scope` |
| `com.google.inject.binder` | Binding EDSL � `LinkedBindingBuilder`, `AnnotatedBindingBuilder`, `ScopedBindingBuilder` |
| `com.google.inject.matcher` | Class and method matchers for AOP interceptors |
| `com.google.inject.multibindings` | `MapBinder`, `Multibinder`, `OptionalBinder` |
| `com.google.inject.name` | `@Named` and `Names` utility |
| `com.google.inject.spi` | Elements API � introspection, visitors, `InjectionPoint`, `Dependency` |
| `com.google.inject.util` | `Modules.override()`, `Providers`, `Types` |
| `com.google.inject.gee` | **GuicedEE SPIs** � extension points for custom annotations |
| `com.google.inject.internal` | Internal implementation (exported for framework use) |
| `com.google.inject.internal.aop` | Internal AOP support |
| `com.google.inject.internal.util` | Internal utilities |

## What Changed from Upstream

| Area | Change |
|---|---|
| **Module descriptor** | Added `module-info.java` with full `exports`, `requires`, `uses` |
| **Jakarta namespace** | `javax.inject` ? `jakarta.inject`, `javax.annotation` ? `jakarta.annotation` |
| **Access levels** | Widened select internal access modifiers so packages work under strict JPMS enforcement |
| **`com.google.inject.gee`** | New package with 6 SPI interfaces for pluggable annotation handling |
| **Multibindings** | Bundled into the core module instead of a separate artifact |
| **Guice internals** | `com.google.inject.internal` exported for framework-level consumers |
| **Local dependency scoping** | Constructor parameters and injected fields can apply scope annotations around an existing binding |

## Contributing

Issues and pull requests are welcome � especially for upstream Guice version bumps, additional SPI hooks, and JDK compatibility fixes.

## License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0) � same as upstream Google Guice.
