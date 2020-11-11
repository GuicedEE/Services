/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jakarta.cache.configuration;

import jakarta.cache.expiry.ExpiryPolicy;
import jakarta.cache.integration.CacheLoader;
import jakarta.cache.integration.CacheWriter;
import java.io.Serializable;

/**
 * A read-only representation of the complete JCache {@link jakarta.cache.Cache}
 * configuration.
 * <p>
 * The properties provided by instances of this interface are used by
 * {@link jakarta.cache.CacheManager}s to configure {@link jakarta.cache.Cache}s.
 * <p>
 * Implementations of this interface must override {@link Object#hashCode()} and
 * {@link Object#equals(Object)} as
 * {@link jakarta.cache.configuration.CompleteConfiguration}s are often compared at
 * runtime.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * @since 1.0
 */
public interface CompleteConfiguration<K, V> extends Configuration<K, V>,
    Serializable {

  /**
   * Determines if a {@link jakarta.cache.Cache} should operate in read-through mode.
   * <p>
   * When in "read-through" mode, cache misses that occur due to cache entries
   * not existing as a result of performing a "get" will appropriately
   * cause the configured {@link jakarta.cache.integration.CacheLoader} to be
   * invoked.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link jakarta.cache.Cache} is in
   * "read-through" mode.
   * @see #getCacheLoaderFactory()
   */
  boolean isReadThrough();

  /**
   * Determines if a {@link jakarta.cache.Cache} should operate in write-through
   * mode.
   * <p>
   * When in "write-through" mode, cache updates that occur as a result of
   * performing "put" operations called via one of
   * {@link jakarta.cache.Cache#put(Object, Object)},
   * {@link jakarta.cache.Cache#getAndRemove(Object)},
   * {@link jakarta.cache.Cache#removeAll()},
   * {@link jakarta.cache.Cache#getAndPut(Object, Object)}
   * {@link jakarta.cache.Cache#getAndRemove(Object)},
   * {@link jakarta.cache.Cache#getAndReplace(Object,
   * Object)}, {@link jakarta.cache.Cache#invoke(Object,
   * jakarta.cache.processor.EntryProcessor,
   * Object...)}, {@link jakarta.cache.Cache#invokeAll(java.util.Set,
   * jakarta.cache.processor.EntryProcessor, Object...)} will appropriately cause
   * the configured {@link jakarta.cache.integration.CacheWriter} to be invoked.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link jakarta.cache.Cache} is in
   *        "write-through" mode.
   * @see #getCacheWriterFactory()
   */
  boolean isWriteThrough();

  /**
   * Checks whether statistics collection is enabled in this cache.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return true if statistics collection is enabled
   */
  boolean isStatisticsEnabled();

  /**
   * Checks whether management is enabled on this cache.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return true if management is enabled
   */
  boolean isManagementEnabled();

  /**
   * Obtains the {@link jakarta.cache.configuration.CacheEntryListenerConfiguration}s
   * for {@link jakarta.cache.event.CacheEntryListener}s to be configured on a
   * {@link jakarta.cache.Cache}.
   *
   * @return an {@link Iterable} over the
   * {@link jakarta.cache.configuration.CacheEntryListenerConfiguration}s
   */
  Iterable<CacheEntryListenerConfiguration<K,
      V>> getCacheEntryListenerConfigurations();

  /**
   * Gets the {@link jakarta.cache.configuration.Factory} for the
   * {@link jakarta.cache.integration.CacheLoader}, if any.
   * <p>
   * A CacheLoader should be configured for "Read Through" caches to load values
   * when a cache miss occurs using either the
   * {@link jakarta.cache.Cache#get(Object)} and/or
   * {@link jakarta.cache.Cache#getAll(java.util.Set)} methods.
   * <p>
   * The default value is <code>null</code>.
   *
   * @return the {@link jakarta.cache.configuration.Factory} for the
   * {@link jakarta.cache.integration.CacheLoader} or null if none has been set.
   */
  Factory<CacheLoader<K, V>> getCacheLoaderFactory();

  /**
   * Gets the {@link jakarta.cache.configuration.Factory} for the
   * {@link jakarta.cache.integration.CacheWriter}, if any.
   * <p>
   * The default value is <code>null</code>.
   *
   * @return the {@link jakarta.cache.configuration.Factory} for the
   * {@link jakarta.cache.integration.CacheWriter} or null if none has been set.
   */
  Factory<CacheWriter<? super K, ? super V>> getCacheWriterFactory();

  /**
   * Gets the {@link jakarta.cache.configuration.Factory} for the
   * {@link jakarta.cache.expiry.ExpiryPolicy} to be used for caches.
   * <p>
   * The default value is a {@link jakarta.cache.configuration.Factory} that will
   * produce a {@link jakarta.cache.expiry.EternalExpiryPolicy} instance.
   *
   * @return the {@link jakarta.cache.configuration.Factory} for
   * {@link jakarta.cache.expiry.ExpiryPolicy} (must not be <code>null</code>)
   */
  Factory<ExpiryPolicy> getExpiryPolicyFactory();

}
