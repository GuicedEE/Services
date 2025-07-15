package com.google.inject.gee;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * A provider for injection points other than jakarta.@Inject or children of
 */
public interface InjectionPointProvider {
    Class<? extends Annotation> injectionPoint(AnnotatedElement member);
}
