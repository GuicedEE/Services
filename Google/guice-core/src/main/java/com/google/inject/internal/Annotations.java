/*
 * Copyright (C) 2006 Google Inc.
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

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import com.google.inject.ScopeAnnotation;
import com.google.inject.TypeLiteral;
import com.google.inject.gee.BindingAnnotationProvider;
import com.google.inject.gee.NamedAnnotationProvider;
import com.google.inject.gee.ScopeAnnotationProvider;
import com.google.inject.internal.util.Classes;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Annotation utilities.
 *
 * @author crazybob@google.com (Bob Lee)
 */
public class Annotations {

  /** Returns {@code true} if the given annotation type has no attributes. */
  public static boolean isMarker(Class<? extends Annotation> annotationType) {
    return annotationType.getDeclaredMethods().length == 0;
  }

  public static boolean isAllDefaultMethods(Class<? extends Annotation> annotationType) {
    boolean hasMethods = false;
    for (Method m : annotationType.getDeclaredMethods()) {
      hasMethods = true;
      if (m.getDefaultValue() == null) {
        return false;
      }
    }
    return hasMethods;
  }

  private static final LoadingCache<Class<? extends Annotation>, Annotation> cache =
      CacheBuilder.newBuilder()
          .weakKeys()
          .build(
              new CacheLoader<Class<? extends Annotation>, Annotation>() {
                @Override
                public Annotation load(Class<? extends Annotation> input) {
                  return generateAnnotationImpl(input);
                }
              });

  /**
   * Generates an Annotation for the annotation class. Requires that the annotation is all
   * optionals.
   */
  @SuppressWarnings("unchecked") // Safe because generateAnnotationImpl returns T for Class<T>
  public static <T extends Annotation> T generateAnnotation(Class<T> annotationType) {
    Preconditions.checkState(
        isAllDefaultMethods(annotationType), "%s is not all default methods", annotationType);
    return (T) cache.getUnchecked(annotationType);
  }

  private static <T extends Annotation> T generateAnnotationImpl(final Class<T> annotationType) {
    final Map<String, Object> members = resolveMembers(annotationType);
    return annotationType.cast(
        Proxy.newProxyInstance(
            annotationType.getClassLoader(),
            new Class<?>[] {annotationType},
            new InvocationHandler() {
              @Override
              public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                String name = method.getName();
                if (name.equals("annotationType")) {
                  return annotationType;
                } else if (name.equals("toString")) {
                  return annotationToString(annotationType, members);
                } else if (name.equals("hashCode")) {
                  return annotationHashCode(annotationType, members);
                } else if (name.equals("equals")) {
                  return annotationEquals(annotationType, members, args[0]);
                } else {
                  return members.get(name);
                }
              }
            }));
  }

  private static ImmutableMap<String, Object> resolveMembers(
      Class<? extends Annotation> annotationType) {
    ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();
    for (Method method : annotationType.getDeclaredMethods()) {
      result.put(method.getName(), method.getDefaultValue());
    }
    return result.buildOrThrow();
  }

  /** Implements {@link Annotation#equals}. */
  private static boolean annotationEquals(
      Class<? extends Annotation> type, Map<String, Object> members, Object other)
      throws Exception {
    if (!type.isInstance(other)) {
      return false;
    }
    for (Method method : type.getDeclaredMethods()) {
      String name = method.getName();
      if (!Arrays.deepEquals(
          new Object[] {method.invoke(other)}, new Object[] {members.get(name)})) {
        return false;
      }
    }
    return true;
  }

  /** Implements {@link Annotation#hashCode}. */
  private static int annotationHashCode(
      Class<? extends Annotation> type, Map<String, Object> members) throws Exception {
    int result = 0;
    for (Method method : type.getDeclaredMethods()) {
      String name = method.getName();
      Object value = members.get(name);
      result += (127 * name.hashCode()) ^ (Arrays.deepHashCode(new Object[] {value}) - 31);
    }
    return result;
  }

  private static final MapJoiner JOINER = Joiner.on(", ").withKeyValueSeparator("=");

  /** Implements {@link Annotation#toString}. */
  private static String annotationToString(
      Class<? extends Annotation> type, Map<String, Object> members) throws Exception {
    StringBuilder sb = new StringBuilder().append('@').append(type.getName()).append('(');
    JOINER.appendTo(
        sb,
        Maps.transformValues(
            members,
            arg -> {
              String s = Arrays.deepToString(new Object[] {arg});
              return s.substring(1, s.length() - 1); // cut off brackets
            }));
    return sb.append(')').toString();
  }

  /** Returns true if the given annotation is retained at runtime. */
  public static boolean isRetainedAtRuntime(Class<? extends Annotation> annotationType) {
    Retention retention = annotationType.getAnnotation(Retention.class);
    return retention != null && retention.value() == RetentionPolicy.RUNTIME;
  }

  /** Returns the scope annotation on {@code type}, or null if none is specified. */
  public static Class<? extends Annotation> findScopeAnnotation(
      Errors errors, Class<?> implementation) {
    return findScopeAnnotation(errors, implementation.getAnnotations());
  }

  /** Returns the scoping annotation, or null if there isn't one. */
  public static Class<? extends Annotation> findScopeAnnotation(
      Errors errors, Annotation[] annotations) {
    Class<? extends Annotation> found = null;

    for (Annotation annotation : annotations) {
      Class<? extends Annotation> annotationType = annotation.annotationType();
      if (isScopeAnnotation(annotationType)) {
        if (found != null) {
          errors.duplicateScopeAnnotations(found, annotationType);
        } else {
          found = annotationType;
        }
      }
    }

    return found;
  }

  static boolean containsComponentAnnotation(Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      // TODO(user): Should we scope this down to dagger.Component?
      if (annotation.annotationType().getSimpleName().equals("Component")) {
        return true;
      }
    }

    return false;
  }

  private static class AnnotationToStringConfig {
    final boolean quote;
    final boolean includeMemberName;
    final boolean dollarSeparator;

    AnnotationToStringConfig(boolean quote, boolean includeMemberName, boolean dollarSeparator) {
      this.quote = quote;
      this.includeMemberName = includeMemberName;
      this.dollarSeparator = dollarSeparator;
    }
  }

  private static final AnnotationToStringConfig ANNOTATION_TO_STRING_CONFIG =
      determineAnnotationToStringConfig();

  /**
   * Returns {@code value}, quoted if annotation implementations quote their member values. In Java
   * 9, annotations quote their string members.
   */
  public static String memberValueString(String value) {
    return ANNOTATION_TO_STRING_CONFIG.quote ? "\"" + value + "\"" : value;
  }

  /**
   * Returns string representation of the annotation memeber.
   *
   * <p>The value of the member is prefixed with `memberName=` unless the runtime omits the member
   * name. The value of the member is quoted if annotation implementations quote their member values
   * and the value type is String.
   *
   * <p>In Java 9, annotations quote their string members and in Java 15, the member name is
   * omitted.
   */
  public static String memberValueString(String memberName, Object value) {
    StringBuilder sb = new StringBuilder();
    boolean quote = ANNOTATION_TO_STRING_CONFIG.quote;
    boolean includeMemberName = ANNOTATION_TO_STRING_CONFIG.includeMemberName;
    if (includeMemberName) {
      sb.append(memberName).append('=');
    }
    if (quote && (value instanceof String)) {
      sb.append('"').append(value).append('"');
    } else {
      sb.append(value);
    }
    return sb.toString();
  }

  /**
   * Returns the string representation of the annotation class as it would appear in an annotation
   * instance's toString() method.
   *
   * <p>In earlier JDKs, the string representation mirrored Class.getName() (which uses $ to
   * separate outer and inner classes), but newer JDKs use . as the separator).
   */
  public static String annotationInstanceClassString(
      Class<? extends Annotation> ann, boolean includePackage) {
    if (ANNOTATION_TO_STRING_CONFIG.dollarSeparator) {
      if (includePackage || ann.getPackage() == null) {
        return "@" + ann.getName();
      }
      return "@" + ann.getName().substring(ann.getPackage().getName().length() + 1);
    }
    return "@" + dotSeparatedParentOrPackage(ann, includePackage);
  }

  private static String dotSeparatedParentOrPackage(Class<?> ann, boolean includePackage) {
    if (ann.getDeclaringClass() == null) {
      if (includePackage) {
        return ann.getPackage().getName() + "." + ann.getSimpleName();
      }
      return ann.getSimpleName();
    }
    return dotSeparatedParentOrPackage(ann.getDeclaringClass(), includePackage)
        + "."
        + ann.getSimpleName();
  }

  @Retention(RUNTIME)
  private @interface TestAnnotation {
    String value();
  }

  @TestAnnotation("determineAnnotationToStringConfig")
  private static AnnotationToStringConfig determineAnnotationToStringConfig() {
    try {
      String annotation =
          Annotations.class
              .getDeclaredMethod("determineAnnotationToStringConfig")
              .getAnnotation(TestAnnotation.class)
              .toString();
      boolean quote = annotation.contains("\"determineAnnotationToStringConfig\"");
      boolean includeMemberName = annotation.contains("value=");
      boolean dollarSeparator = annotation.contains("Annotations$TestAnnotation");
      return new AnnotationToStringConfig(quote, includeMemberName, dollarSeparator);
    } catch (NoSuchMethodException e) {
      throw new AssertionError(e);
    }
  }

  /** Checks for the presence of annotations. Caches results because Android doesn't. */
  static class AnnotationChecker {
    private final Collection<Class<? extends Annotation>> annotationTypes;

    /** Returns true if the given class has one of the desired annotations. */
    private CacheLoader<Class<? extends Annotation>, Boolean> hasAnnotations =
        new CacheLoader<Class<? extends Annotation>, Boolean>() {
          @Override
          public Boolean load(Class<? extends Annotation> annotationType) {
            for (Annotation annotation : annotationType.getAnnotations()) {
              if (annotationTypes.contains(annotation.annotationType())) {
                return true;
              }
            }
            return false;
          }
        };

    final LoadingCache<Class<? extends Annotation>, Boolean> cache =
        CacheBuilder.newBuilder().weakKeys().build(hasAnnotations);

    /** Constructs a new checker that looks for annotations of the given types. */
    AnnotationChecker(Collection<Class<? extends Annotation>> annotationTypes) {
      this.annotationTypes = annotationTypes;
    }

    /** Returns true if the given type has one of the desired annotations. */
    boolean hasAnnotations(Class<? extends Annotation> annotated) {
      return cache.getUnchecked(annotated);
    }
  }

  private static final AnnotationChecker scopeChecker =
      new AnnotationChecker(ScopeAnnotationProvider.getAllScopeAnnotations());

  public static boolean isScopeAnnotation(Class<? extends Annotation> annotationType) {
    return scopeChecker.hasAnnotations(annotationType);
  }

  /**
   * Adds an error if there is a misplaced annotations on {@code type}. Scoping annotations are not
   * allowed on abstract classes or interfaces.
   */
  public static void checkForMisplacedScopeAnnotations(
      Class<?> type, Object source, Errors errors) {
    if (Classes.isConcrete(type)) {
      return;
    }

    Class<? extends Annotation> scopeAnnotation = findScopeAnnotation(errors, type);
    if (scopeAnnotation != null
        // We let Dagger Components through to aid migrations.
        && !containsComponentAnnotation(type.getAnnotations())) {
      errors.withSource(type).scopeAnnotationOnAbstractType(scopeAnnotation, type, source);
    }
  }

  // NOTE: getKey/findBindingAnnotation are used by Gin which is abandoned.  So changing this API
  // will prevent Gin users from upgrading Guice version.

  /** Gets a key for the given type, member and annotations. */
  public static Key<?> getKey(
      TypeLiteral<?> type, Member member, Annotation[] annotations, Errors errors)
      throws ErrorsException {
    int numErrorsBefore = errors.size();
    Annotation found = findBindingAnnotation(errors, member, annotations);
    errors.throwIfNewErrors(numErrorsBefore);
    return found == null ? Key.get(type) : Key.get(type, found);
  }

  /** Returns the binding annotation on {@code member}, or null if there isn't one. */
  public static Annotation findBindingAnnotation(
      Errors errors, Member member, Annotation[] annotations) {
    Annotation found = null;

    for (Annotation annotation : annotations) {
      Class<? extends Annotation> annotationType = annotation.annotationType();
      if (isBindingAnnotation(annotationType)) {
        if (found != null) {
          errors.duplicateBindingAnnotations(member, found.annotationType(), annotationType);
        } else {
          found = annotation;
        }
      }
    }

    return found;
  }

  private static final AnnotationChecker bindingAnnotationChecker =
      new AnnotationChecker(BindingAnnotationProvider.getAllBindingAnnotations());

  /** Returns true if annotations of the specified type are binding annotations. */
  public static boolean isBindingAnnotation(Class<? extends Annotation> annotationType) {
    return bindingAnnotationChecker.hasAnnotations(annotationType);
  }

  /**
   * If the annotation is an instance of {@code jakarta.inject.Named}, canonicalizes to
   * com.google.guice.name.Named. Returns the given annotation otherwise.
   */
  public static Annotation canonicalizeIfNamed(Annotation annotation) {
    ServiceLoader<NamedAnnotationProvider> namedAnnotationProviders = ServiceLoader.load(NamedAnnotationProvider.class);
    for (NamedAnnotationProvider namedAnnotationProvider : namedAnnotationProviders)
    {
      var o = namedAnnotationProvider.getNamedAnnotation(annotation);
      if (o != null)
      {
        return o;
      }
    }

    return annotation;
  }

  /**
   * If the annotation is the class {@code jakarta.inject.Named}, canonicalizes to
   * com.google.guice.name.Named. Returns the given annotation class otherwise.
   */
  public static Class<? extends Annotation> canonicalizeIfNamed(
      Class<? extends Annotation> annotationType) {
    ServiceLoader<NamedAnnotationProvider> namedAnnotationProviders = ServiceLoader.load(NamedAnnotationProvider.class);
    for (NamedAnnotationProvider namedAnnotationProvider : namedAnnotationProviders)
    {
      var o = namedAnnotationProvider.getNamedAnnotation(annotationType);
      if (o != null)
      {
        return Named.class;
      }
    }
    return annotationType;
  }

  /**
   * Returns the name the binding should use. This is based on the annotation. If the annotation has
   * an instance and is not a marker annotation, we ask the annotation for its toString. If it was a
   * marker annotation or just an annotation type, we use the annotation's name. Otherwise, the name
   * is the empty string.
   */
  public static String nameOf(Key<?> key) {
    Annotation annotation = key.getAnnotation();
    Class<? extends Annotation> annotationType = key.getAnnotationType();
    if (annotation != null && !isMarker(annotationType)) {
      return key.getAnnotation().toString();
    } else if (key.getAnnotationType() != null) {
      return '@' + key.getAnnotationType().getName();
    } else {
      return "";
    }
  }
}
