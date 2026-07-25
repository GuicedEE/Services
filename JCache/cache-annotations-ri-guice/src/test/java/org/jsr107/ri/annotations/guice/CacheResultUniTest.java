package org.jsr107.ri.annotations.guice;

import com.google.inject.Guice;
import io.smallrye.mutiny.Uni;
import org.jsr107.ri.annotations.guice.module.CacheAnnotationsModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.cache.Cache;
import javax.cache.annotation.CacheInvocationContext;
import javax.cache.annotation.CacheMethodDetails;
import javax.cache.annotation.CacheResolver;
import javax.cache.annotation.CacheResolverFactory;
import javax.cache.annotation.CacheResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CacheResultUniTest {

  private static final Duration UNI_TIMEOUT = Duration.ofSeconds(5);

  private TestService service;

  @BeforeEach
  void setUp() {
    MapCacheResolverFactory.clear();
    service = Guice.createInjector(new CacheAnnotationsModule()).getInstance(TestService.class);
  }

  @Test
  void cachesUniItemAndReturnsUniOnHit() {
    assertEquals("value-a-1", service.cachedUni("a").await().atMost(UNI_TIMEOUT));
    assertEquals("value-a-1", service.cachedUni("a").await().atMost(UNI_TIMEOUT));

    assertEquals(1, service.uniCalls);
    assertEquals(1, MapCacheResolverFactory.cache("uni-values").size());
    assertInstanceOf(String.class, MapCacheResolverFactory.cache("uni-values").values().iterator().next());
  }

  @Test
  void doesNotCacheNullUniItem() {
    assertNull(service.cachedNullUni("a").await().atMost(UNI_TIMEOUT));
    assertNull(service.cachedNullUni("a").await().atMost(UNI_TIMEOUT));

    assertEquals(2, service.nullCalls);
    assertTrue(MapCacheResolverFactory.cache("uni-null-values").isEmpty());
  }

  @Test
  void cachesUniFailureAndReturnsFailedUniOnHit() {
    IllegalStateException first = assertThrows(IllegalStateException.class,
        () -> service.failingUni("a").await().atMost(UNI_TIMEOUT));
    IllegalStateException second = assertThrows(IllegalStateException.class,
        () -> service.failingUni("a").await().atMost(UNI_TIMEOUT));

    assertEquals("failure-a-1", first.getMessage());
    assertEquals("failure-a-1", second.getMessage());
    assertEquals(1, service.failureCalls);
    assertEquals(1, MapCacheResolverFactory.cache("uni-exceptions").size());
  }

  @Test
  void keepsSynchronousCacheResultBehavior() {
    assertEquals("sync-a-1", service.cachedSync("a"));
    assertEquals("sync-a-1", service.cachedSync("a"));

    assertEquals(1, service.syncCalls);
    assertEquals(1, MapCacheResolverFactory.cache("sync-values").size());
  }

  static class TestService {
    int uniCalls;
    int nullCalls;
    int failureCalls;
    int syncCalls;

    @CacheResult(cacheName = "uni-values", cacheResolverFactory = MapCacheResolverFactory.class)
    public Uni<String> cachedUni(String id) {
      uniCalls++;
      return Uni.createFrom().item("value-" + id + "-" + uniCalls);
    }

    @CacheResult(cacheName = "uni-null-values", cacheResolverFactory = MapCacheResolverFactory.class)
    public Uni<String> cachedNullUni(String id) {
      nullCalls++;
      return Uni.createFrom().nullItem();
    }

    @CacheResult(cacheName = "uni-failures", exceptionCacheName = "uni-exceptions",
        cachedExceptions = IllegalStateException.class, cacheResolverFactory = MapCacheResolverFactory.class)
    public Uni<String> failingUni(String id) {
      failureCalls++;
      return Uni.createFrom().failure(new IllegalStateException("failure-" + id + "-" + failureCalls));
    }

    @CacheResult(cacheName = "sync-values", cacheResolverFactory = MapCacheResolverFactory.class)
    public String cachedSync(String id) {
      syncCalls++;
      return "sync-" + id + "-" + syncCalls;
    }
  }

  public static class MapCacheResolverFactory implements CacheResolverFactory {
    private static final Map<String, Map<Object, Object>> CACHES = new ConcurrentHashMap<>();

    static void clear() {
      CACHES.clear();
    }

    static Map<Object, Object> cache(String name) {
      return CACHES.computeIfAbsent(name, ignored -> new ConcurrentHashMap<>());
    }

    @Override
    public CacheResolver getCacheResolver(CacheMethodDetails<? extends Annotation> cacheMethodDetails) {
      return resolver(cacheMethodDetails.getCacheName());
    }

    @Override
    public CacheResolver getExceptionCacheResolver(CacheMethodDetails<CacheResult> cacheMethodDetails) {
      return resolver(cacheMethodDetails.getCacheAnnotation().exceptionCacheName());
    }

    private CacheResolver resolver(String cacheName) {
      return new CacheResolver() {
        @Override
        public <K, V> Cache<K, V> resolveCache(
            CacheInvocationContext<? extends Annotation> cacheInvocationContext) {
          return createCache(cacheName);
        }
      };
    }

    @SuppressWarnings("unchecked")
    private <K, V> Cache<K, V> createCache(String cacheName) {
      Map<Object, Object> cache = cache(cacheName);
      return (Cache<K, V>) Proxy.newProxyInstance(Cache.class.getClassLoader(), new Class<?>[]{Cache.class},
          (proxy, method, args) -> {
            switch (method.getName()) {
              case "get":
                return cache.get(args[0]);
              case "put":
                cache.put(args[0], args[1]);
                return null;
              case "remove":
                return cache.remove(args[0]) != null;
              case "removeAll":
                cache.clear();
                return null;
              case "containsKey":
                return cache.containsKey(args[0]);
              case "getName":
                return cacheName;
              case "isClosed":
                return false;
              case "close":
                return null;
              case "unwrap":
                return cache;
              case "toString":
                return cache.toString();
              default:
                throw new UnsupportedOperationException(method.toString());
            }
          });
    }
  }
}
