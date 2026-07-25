/**
 *  Copyright 2011-2013 Terracotta, Inc.
 *  Copyright 2011-2013 Oracle America Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.jsr107.ri.annotations;


import io.smallrye.mutiny.Uni;

import javax.cache.Cache;
import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheResolver;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.GeneratedCacheKey;
import java.lang.annotation.Annotation;


/**
 * Interceptor for {@link CacheResult}
 *
 * @param <I> The intercepted method invocation
 * @author Rick Hightower
 * @author Eric Dalquist
 * @since 1.0
 */
public abstract class AbstractCacheResultInterceptor<I> extends AbstractKeyedCacheInterceptor<I, CacheResultMethodDetails> {

  /**
   * Handles the {@link Cache#get(Object)} and {@link Cache#put(Object, Object)} logic as specified for the
   * {@link CacheResult} annotation
   *
   * @param cacheContextSource The intercepted invocation
   * @param invocation         The intercepted invocation
   * @return The result from {@link #proceed(Object)}
   * @throws Throwable if {@link #proceed(Object)} threw
   */
  public final Object cacheResult(CacheContextSource<I> cacheContextSource, I invocation) throws Throwable {
    //Load details about the annotated method
    final InternalCacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext =
        cacheContextSource.getCacheKeyInvocationContext(invocation);
    final CacheResultMethodDetails methodDetails =
        this.getStaticCacheKeyInvocationContext(cacheKeyInvocationContext, InterceptorType.CACHE_RESULT);

    //Resolve primary cache
    final CacheResolver cacheResolver = methodDetails.getCacheResolver();
    final Cache<Object, Object> cache = cacheResolver.resolveCache(cacheKeyInvocationContext);

    //Resolve exception cache
    final Cache<Object, Throwable> exceptionCache = getExceptionCache(cacheKeyInvocationContext, methodDetails);

    //Generate the cache key
    final CacheKeyGenerator cacheKeyGenerator = methodDetails.getCacheKeyGenerator();
    final GeneratedCacheKey cacheKey = cacheKeyGenerator.generateCacheKey(cacheKeyInvocationContext);

    final CacheResult cacheResultAnnotation = methodDetails.getCacheAnnotation();

    //If skip-get is false check for a cached result or a cached exception
    Object result;
    if (!cacheResultAnnotation.skipGet()) {
      //Look in cache for existing data
      result = cache.get(cacheKey);
      if (result != null) {
        //Cache hit, return result
        return this.createResult(result, invocation);
      }

      //Look for a cached exception
      final Throwable cachedException = getCachedException(exceptionCache, cacheKey);
      if (cachedException != null) {
        return this.createFailure(cachedException, invocation);
      }
    }

    try {
      //Call the annotated method
      result = this.proceed(invocation);

      if (result instanceof Uni<?>) {
        return this.cacheUniResult((Uni<?>) result, cache, cacheKey, exceptionCache, cacheResultAnnotation);
      }

      //Cache non-null result
      if (result != null) {
        cache.put(cacheKey, result);
      }

      return result;
    } catch (Throwable t) {
      //If exception caching is enabled check if the throwable passes the include/exclude filters and then cache it
      cacheException(exceptionCache, cacheKey, cacheResultAnnotation, t);

      throw t;
    }
  }

  /**
   * Check to see if there is a cached exception that needs to be re-thrown
   *
   * @param exceptionCache The exception cache, may be null if no exception caching is being done
   * @param cacheKey       The cache key
   * @throws Throwable The cached exception
   */
  protected void checkForCachedException(final Cache<Object, Throwable> exceptionCache, final GeneratedCacheKey cacheKey)
      throws Throwable {
    final Throwable throwable = getCachedException(exceptionCache, cacheKey);
    if (throwable != null) {
      //Found exception, re-throw
      throw throwable;
    }
  }

  /**
   * Get a cached exception that needs to be re-thrown.
   *
   * @param exceptionCache The exception cache, may be null if no exception caching is being done
   * @param cacheKey       The cache key
   * @return The cached exception, or null if no exception is cached
   */
  protected Throwable getCachedException(final Cache<Object, Throwable> exceptionCache, final GeneratedCacheKey cacheKey) {
    if (exceptionCache == null) {
      return null;
    }

    return exceptionCache.get(cacheKey);
  }

  /**
   * Cache the exception if exception caching is enabled.
   *
   * @param exceptionCache        The exception cache, may be null if no exception caching is being done
   * @param cacheKey              The cache key
   * @param cacheResultAnnotation The cache result annotation
   * @param t                     The exception to cache
   */
  protected void cacheException(final Cache<Object, Throwable> exceptionCache, final GeneratedCacheKey cacheKey,
                                final CacheResult cacheResultAnnotation, Throwable t) {
    if (exceptionCache == null) {
      return;
    }

    final Class<? extends Throwable>[] cachedExceptions = cacheResultAnnotation.cachedExceptions();
    final Class<? extends Throwable>[] nonCachedExceptions = cacheResultAnnotation.nonCachedExceptions();
    final boolean included = ClassFilter.isIncluded(t, cachedExceptions, nonCachedExceptions, true);
    if (included) {
      //Cache the exception for future rethrow
      exceptionCache.put(cacheKey, t);
    }
  }

  /**
   * Get the exception cache if one is configured
   *
   * @param cacheKeyInvocationContext The invocation details
   * @param methodDetails             The method details
   * @return The exception cache, null if exception caching is disabled.
   */
  protected Cache<Object, Throwable> getExceptionCache(
      final InternalCacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext,
      final CacheResultMethodDetails methodDetails) {

    final CacheResolver exceptionCacheResolver = methodDetails.getExceptionCacheResolver();
    if (exceptionCacheResolver != null) {
      return exceptionCacheResolver.resolveCache(cacheKeyInvocationContext);
    }

    return null;
  }

  /**
   * Cache a Uni item or failure once the Uni is subscribed.
   *
   * @param uni                   The Uni returned by the intercepted method
   * @param cache                 The cache to store non-null items in
   * @param cacheKey              The cache key
   * @param exceptionCache        The exception cache, may be null if no exception caching is being done
   * @param cacheResultAnnotation The cache result annotation
   * @return A Uni that preserves the original item/failure while updating caches
   */
  protected Uni<?> cacheUniResult(final Uni<?> uni, final Cache<Object, Object> cache, final GeneratedCacheKey cacheKey,
                                  final Cache<Object, Throwable> exceptionCache,
                                  final CacheResult cacheResultAnnotation) {
    return uni.onItem().invoke(item -> {
      if (item != null) {
        cache.put(cacheKey, item);
      }
    }).onFailure().invoke(t -> cacheException(exceptionCache, cacheKey, cacheResultAnnotation, t));
  }

  /**
   * Create the method result for a cached value.
   *
   * @param result     The cached value
   * @param invocation The intercepted invocation
   * @return The cached value, wrapped in a Uni if the method returns Uni
   */
  protected Object createResult(final Object result, final I invocation) {
    if (isUniReturn(invocation)) {
      return Uni.createFrom().item(result);
    }

    return result;
  }

  /**
   * Create the method result for a cached failure.
   *
   * @param throwable  The cached failure
   * @param invocation The intercepted invocation
   * @return A failed Uni if the method returns Uni
   * @throws Throwable The cached failure for non-Uni methods
   */
  protected Object createFailure(final Throwable throwable, final I invocation) throws Throwable {
    if (isUniReturn(invocation)) {
      return Uni.createFrom().failure(throwable);
    }

    throw throwable;
  }

  private boolean isUniReturn(final I invocation) {
    return Uni.class.isAssignableFrom(getReturnType(invocation));
  }
}
