/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.gee.InjectionPoint;
import com.google.inject.spi.InterceptorBinding;

/**
 * Constructor injectors by type.
 *
 * @author jessewilson@google.com (Jesse Wilson)
 */
final class ConstructorInjectorStore {
  private final InjectorImpl injector;

  private final FailableCache<InjectionPoint, ConstructorInjector<?>> cache =
      new FailableCache<InjectionPoint, ConstructorInjector<?>>() {
        @Override
        protected ConstructorInjector<?> create(InjectionPoint constructorInjector, Errors errors)
            throws ErrorsException {
          return createConstructor(constructorInjector, errors);
        }
      };

  ConstructorInjectorStore(InjectorImpl injector) {
    this.injector = injector;
  }

  /** Returns true if the store is in the process of loading this injection point. */
  boolean isLoading(InjectionPoint ip) {
    return cache.isLoading(ip);
  }

  /** Returns a new complete constructor injector with injection listeners registered. */
  public ConstructorInjector<?> get(InjectionPoint constructorInjector, Errors errors)
      throws ErrorsException {
    return cache.get(constructorInjector, errors);
  }

  /**
   * Purges an injection point from the cache. Use this only if the cache is not actually valid and
   * needs to be purged. (See issue 319 and
   * ImplicitBindingTest#testCircularJitBindingsLeaveNoResidue and
   * #testInstancesRequestingProvidersForThemselvesWithChildInjectors for examples of when this is
   * necessary.)
   *
   * <p>Returns true if the injector for that point was stored in the cache, false otherwise.
   */
  boolean remove(InjectionPoint ip) {
    return cache.remove(ip);
  }

  private <T> ConstructorInjector<T> createConstructor(InjectionPoint injectionPoint, Errors errors)
      throws ErrorsException {
    int numErrorsBefore = errors.size();

    SingleParameterInjector<?>[] constructorParameterInjectors =
        injector.getParametersInjectors(injectionPoint.getDependencies(), errors);

    @SuppressWarnings("unchecked") // the injector type agrees with the injection point type
    MembersInjectorImpl<T> membersInjector =
        (MembersInjectorImpl<T>)
            injector.membersInjectorStore.get(injectionPoint.getDeclaringType(), errors);
    ConstructionProxyFactory<T> factory = null;
    if (InternalFlags.isBytecodeGenEnabled()) {
      ImmutableList<InterceptorBinding> injectorBindings =
          injector.getBindingData().getInterceptorBindings();
      ImmutableList<MethodAspect> methodAspects =
          ImmutableList.<MethodAspect>builder()
              .addAll(Lists.transform(injectorBindings, MethodAspect::fromBinding))
              .addAll(membersInjector.getAddedAspects())
              .build();
      factory = new ProxyFactory<>(injectionPoint, methodAspects);
    } else {
      factory = new DefaultConstructionProxyFactory<>(injectionPoint);
    }

    errors.throwIfNewErrors(numErrorsBefore);

    return new ConstructorInjector<T>(
        membersInjector.getInjectionPoints(),
        factory.create(),
        constructorParameterInjectors,
        membersInjector);
  }
}
