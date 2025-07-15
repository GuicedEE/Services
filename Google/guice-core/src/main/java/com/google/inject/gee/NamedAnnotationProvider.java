package com.google.inject.gee;

import com.google.inject.name.Named;

import java.lang.annotation.Annotation;

public interface NamedAnnotationProvider
{
    Named getNamedAnnotation(Annotation annotationType);

    Named getNamedAnnotation(Class<? extends Annotation> annotationType);
}
