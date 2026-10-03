package com.google.inject;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.gee.BindScopeProvider;
import com.google.inject.gee.ScopeAnnotationProvider;
import org.junit.jupiter.api.Test;
import java.lang.annotation.Annotation;
import java.util.List;

public class LocalScopeTest {
  private static final AtomicInteger created = new AtomicInteger();

  static class Dependency {
    final int id = created.incrementAndGet();
  }

  static class ConstructorConsumer {
    final Dependency first;
    final Dependency second;

    @Inject ConstructorConsumer(@Singleton Dependency first, @Singleton Dependency second) {
      this.first = first;
      this.second = second;
    }
  }

  static class OtherConstructorConsumer {
    final Dependency value;
    @Inject OtherConstructorConsumer(@Singleton Dependency value) { this.value = value; }
  }

  static class FieldConsumer {
    @Inject @Singleton Dependency first;
    @Inject @Singleton Dependency second;
  }

  static class OtherFieldConsumer {
    @Inject @Singleton Dependency value;
  }

  static class QualifiedConstructorConsumer {
    final Dependency first;
    final Dependency second;
    @Inject QualifiedConstructorConsumer(
        @Named("first") @Singleton Dependency first,
        @Named("second") @Singleton Dependency second) {
      this.first = first;
      this.second = second;
    }
  }

  @Test public void constructorScopeSharesLocallyAndIsolatesOtherConsumers() {
    created.set(0);
    Injector injector = Guice.createInjector();
    ConstructorConsumer first = injector.getInstance(ConstructorConsumer.class);
    ConstructorConsumer second = injector.getInstance(ConstructorConsumer.class);
    assertSame(first.first, first.second);
    assertSame(first.first, second.first);
    assertNotSame(first.first, injector.getInstance(OtherConstructorConsumer.class).value);
    assertNotSame(first.first, injector.getInstance(Dependency.class));
  }

  @Test public void fieldScopeSharesLocallyAndIsolatesOtherConsumers() {
    created.set(0);
    Injector injector = Guice.createInjector();
    FieldConsumer first = injector.getInstance(FieldConsumer.class);
    FieldConsumer second = injector.getInstance(FieldConsumer.class);
    assertSame(first.first, first.second);
    assertSame(first.first, second.first);
    assertNotSame(first.first, injector.getInstance(OtherFieldConsumer.class).value);
    assertNotSame(first.first, injector.getInstance(Dependency.class));
  }

  @Test public void differentDependencyKeysHaveSeparateLocalProviders() {
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override protected void configure() {
        bind(Dependency.class).annotatedWith(Names.named("first"))
            .toProvider(Dependency::new);
        bind(Dependency.class).annotatedWith(Names.named("second"))
            .toProvider(Dependency::new);
      }
    });
    QualifiedConstructorConsumer first = injector.getInstance(QualifiedConstructorConsumer.class);
    QualifiedConstructorConsumer second = injector.getInstance(QualifiedConstructorConsumer.class);
    assertSame(first.first, second.first);
    assertSame(first.second, second.second);
    assertNotSame(first.first, first.second);
  }

  @Target({ElementType.PARAMETER, ElementType.FIELD})
  @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  @ScopeAnnotation
  public @interface LocalScoped {}

  static class DifferentScopesConstructorConsumer {
    final Dependency singleton;
    final Dependency local;

    @Inject DifferentScopesConstructorConsumer(
        @Singleton Dependency singleton, @LocalScoped Dependency local) {
      this.singleton = singleton;
      this.local = local;
    }
  }

  static class DifferentScopesFieldConsumer {
    @Inject @Singleton Dependency singleton;
    @Inject @LocalScoped Dependency local;
  }

  @Test public void constructorScopeAnnotationsHaveSeparateLocalProviders() {
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override protected void configure() { bindScope(LocalScoped.class, Scopes.SINGLETON); }
    });
    DifferentScopesConstructorConsumer first =
        injector.getInstance(DifferentScopesConstructorConsumer.class);
    DifferentScopesConstructorConsumer second =
        injector.getInstance(DifferentScopesConstructorConsumer.class);
    assertSame(first.singleton, second.singleton);
    assertSame(first.local, second.local);
    assertNotSame(first.singleton, first.local);
  }

  @Test public void fieldScopeAnnotationsHaveSeparateLocalProviders() {
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override protected void configure() { bindScope(LocalScoped.class, Scopes.SINGLETON); }
    });
    DifferentScopesFieldConsumer first = injector.getInstance(DifferentScopesFieldConsumer.class);
    DifferentScopesFieldConsumer second = injector.getInstance(DifferentScopesFieldConsumer.class);
    assertSame(first.singleton, second.singleton);
    assertSame(first.local, second.local);
    assertNotSame(first.singleton, first.local);
  }

  @Target({ElementType.PARAMETER, ElementType.FIELD})
  @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  @ScopeAnnotation
  public @interface UnboundScoped {}

  static class CustomConstructorConsumer {
    final Dependency value;
    @Inject CustomConstructorConsumer(@LocalScoped Dependency value) { this.value = value; }
  }

  static class CustomFieldConsumer {
    @Inject @LocalScoped Dependency value;
  }

  static class UnboundConstructorConsumer {
    @Inject UnboundConstructorConsumer(@UnboundScoped Dependency value) {}
  }

  static class UnboundFieldConsumer {
    @Inject @UnboundScoped Dependency value;
  }

  static class DuplicateConstructorConsumer {
    @Inject DuplicateConstructorConsumer(@Singleton @LocalScoped Dependency value) {}
  }

  static class DuplicateFieldConsumer {
    @Inject @Singleton @LocalScoped Dependency value;
  }

  @Test public void registeredCustomScopeWorksForBothInjectionKinds() {
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override protected void configure() { bindScope(LocalScoped.class, Scopes.SINGLETON); }
    });
    assertSame(injector.getInstance(CustomConstructorConsumer.class).value,
        injector.getInstance(CustomConstructorConsumer.class).value);
    assertSame(injector.getInstance(CustomFieldConsumer.class).value,
        injector.getInstance(CustomFieldConsumer.class).value);
  }

  @Test public void localScopeWrapsExistingScopedBinding() {
    created.set(0);
    AtomicInteger wrapperCalls = new AtomicInteger();
    Scope recordingScope = new Scope() {
      @Override public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
        return () -> {
          wrapperCalls.incrementAndGet();
          return unscoped.get();
        };
      }
    };
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override protected void configure() {
        bindScope(LocalScoped.class, recordingScope);
        bind(Dependency.class).in(Scopes.SINGLETON);
      }
    });
    Dependency direct = injector.getInstance(Dependency.class);
    assertEquals(0, wrapperCalls.get());
    assertSame(direct, injector.getInstance(CustomConstructorConsumer.class).value);
    assertEquals(1, wrapperCalls.get());
    assertSame(direct, injector.getInstance(CustomConstructorConsumer.class).value);
    assertEquals(2, wrapperCalls.get());
    assertSame(direct, injector.getInstance(CustomFieldConsumer.class).value);
    assertEquals(3, wrapperCalls.get());
    assertSame(direct, injector.getInstance(CustomFieldConsumer.class).value);
    assertEquals(4, wrapperCalls.get());
    assertSame(direct, injector.getInstance(Dependency.class));
    assertEquals(4, wrapperCalls.get());
    assertEquals(1, created.get());
  }

  @Test public void unboundAndDuplicateScopesReportErrors() {
    assertCreationError(UnboundConstructorConsumer.class, "No scope is bound");
    assertCreationError(UnboundFieldConsumer.class, "No scope is bound");
    assertCreationError(DuplicateConstructorConsumer.class, "More than one scope annotation");
    assertCreationError(DuplicateFieldConsumer.class, "More than one scope annotation");
  }

  private static void assertCreationError(Class<?> type, String expected) {
    try {
      Guice.createInjector(new AbstractModule() {
        @Override protected void configure() {
          bindScope(LocalScoped.class, Scopes.SINGLETON);
          bind(type);
        }
      });
      fail("Expected CreationException for " + type);
    } catch (CreationException expectedException) {
      assertTrue(expectedException.getMessage().contains(expected), expectedException.getMessage());
    }
  }

  @Target({ElementType.PARAMETER, ElementType.FIELD})
  @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  @ScopeAnnotation
  public @interface RequestLike {}

  static class RequestConstructorConsumer {
    final Dependency value;
    @Inject RequestConstructorConsumer(@RequestLike Dependency value) { this.value = value; }
  }

  static class RequestFieldConsumer {
    @Inject @RequestLike Dependency value;
  }

  @Test public void localScopeFollowsCustomRequestLifecycle() {
    ThreadLocal<Map<Key<?>, Object>> request = new ThreadLocal<>();
    Scope requestScope = new Scope() {
      @Override public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
        return () -> {
          Map<Key<?>, Object> values = request.get();
          if (values == null) throw new OutOfScopeException("No request");
          @SuppressWarnings("unchecked") T value = (T) values.computeIfAbsent(key, unused -> unscoped.get());
          return value;
        };
      }

      @Override public String toString() { return "request-like"; }
    };
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override protected void configure() { bindScope(RequestLike.class, requestScope); }
    });
    Dependency first;
    request.set(new HashMap<>());
    try {
      first = injector.getInstance(RequestConstructorConsumer.class).value;
      assertSame(first, injector.getInstance(RequestConstructorConsumer.class).value);
      Dependency field = injector.getInstance(RequestFieldConsumer.class).value;
      assertSame(field, injector.getInstance(RequestFieldConsumer.class).value);
      assertNotSame(first, field);
    } finally {
      request.remove();
    }
    request.set(new HashMap<>());
    try {
      assertNotSame(first, injector.getInstance(RequestConstructorConsumer.class).value);
    } finally {
      request.remove();
    }
  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface ScopeMarker {}

  @Target({ElementType.PARAMETER, ElementType.FIELD})
  @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  @ScopeMarker
  public @interface SpiScoped {}

  public static class MarkerProvider implements ScopeAnnotationProvider {
    @Override public List<Class<? extends Annotation>> getScopeAnnotations() {
      return List.of(ScopeMarker.class);
    }
  }

  public static class ScopeBindingProvider implements BindScopeProvider {
    @Override public void bindScope(Binder binder) {
      binder.bindScope(SpiScoped.class, Scopes.SINGLETON);
    }
  }

  static class SpiConstructorConsumer {
    final Dependency value;
    @Inject SpiConstructorConsumer(@SpiScoped Dependency value) { this.value = value; }
  }

  static class SpiFieldConsumer {
    @Inject @SpiScoped Dependency value;
  }

  @Test public void existingScopeSpisRegisterParameterAndFieldScopes() {
    Injector injector = Guice.createInjector();
    assertSame(injector.getInstance(SpiConstructorConsumer.class).value,
        injector.getInstance(SpiConstructorConsumer.class).value);
    assertSame(injector.getInstance(SpiFieldConsumer.class).value,
        injector.getInstance(SpiFieldConsumer.class).value);
  }
}
