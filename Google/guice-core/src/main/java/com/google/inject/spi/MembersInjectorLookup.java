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

package com.google.inject.spi;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.Objects;
import com.google.inject.Binder;
import com.google.inject.ConfigurationException;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.gee.InjectionPoint;

import java.util.Set;

/**
 * A lookup of the members injector for a type. Lookups are created explicitly in a module using
 * {@link Binder#getMembersInjector(Class) getMembersInjector()} statements:
 *
 * <pre>
 *     MembersInjector&lt;PaymentService&gt; membersInjector
 *         = getMembersInjector(PaymentService.class);</pre>
 *
 * @author crazybob@google.com (Bob Lee)
 * @since 2.0
 */
public final class MembersInjectorLookup<T> implements Element {

  private final Object source;
  private final TypeLiteral<T> type;
  private MembersInjector<T> delegate;

  public MembersInjectorLookup(Object source, TypeLiteral<T> type) {
    this.source = checkNotNull(source, "source");
    this.type = checkNotNull(type, "type");
  }

  @Override
  public Object getSource() {
    return source;
  }

  /** Gets the type containing the members to be injected. */
  public TypeLiteral<T> getType() {
    return type;
  }

  @Override
  public <T> T acceptVisitor(ElementVisitor<T> visitor) {
    return visitor.visit(this);
  }

  /**
   * Sets the actual members injector.
   *
   * @throws IllegalStateException if the delegate is already set
   */
  public void initializeDelegate(MembersInjector<T> delegate) {
    checkState(this.delegate == null, "delegate already initialized");
    this.delegate = checkNotNull(delegate, "delegate");
  }

  @Override
  public void applyTo(Binder binder) {
    initializeDelegate(binder.withSource(getSource()).getMembersInjector(type));
  }

  /**
   * Returns the delegate members injector, or {@code null} if it has not yet been initialized. The
   * delegate will be initialized when this element is processed, or otherwise used to create an
   * injector.
   */
  public MembersInjector<T> getDelegate() {
    return delegate;
  }

  /**
   * Returns the instance methods and fields that will be injected to fulfill this request.
   *
   * @since 4.2.3
   * @return a possibly empty set of injection points. The set has a specified iteration order. All
   *     fields are returned and then all methods. Within the fields, supertype fields are returned
   *     before subtype fields. Similarly, supertype methods are returned before subtype methods.
   * @throws ConfigurationException if there is a malformed injection point on the class of {@code
   *     instance}, such as a field with multiple binding annotations. The exception's {@link
   *     ConfigurationException#getPartialValue() partial value} is a {@code Set<InjectionPoint>} of
   *     the valid injection points.
   */
  public Set<com.google.inject.gee.InjectionPoint> getInjectionPoints() throws ConfigurationException {
    return InjectionPoint.forInstanceMethodsAndFields(type);
  }

  /**
   * Returns the looked up members injector. The result is not valid until this lookup has been
   * initialized, which usually happens when the injector is created. The members injector will
   * throw an {@code IllegalStateException} if you try to use it beforehand.
   */
  public MembersInjector<T> getMembersInjector() {
    return new MembersInjector<T>() {
      @Override
      public void injectMembers(T instance) {
        MembersInjector<T> local = delegate;
        if (local == null) {
          throw new IllegalStateException(
              "This MembersInjector cannot be used until the Injector has been created.");
        }
        local.injectMembers(instance);
      }

      @Override
      public String toString() {
        return "MembersInjector<" + type + ">";
      }
    };
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof MembersInjectorLookup
        && ((MembersInjectorLookup<?>) obj).type.equals(type)
        && ((MembersInjectorLookup<?>) obj).source.equals(source);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(type, source);
  }
}
