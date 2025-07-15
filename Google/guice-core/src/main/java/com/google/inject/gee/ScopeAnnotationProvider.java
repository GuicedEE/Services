package com.google.inject.gee;

import com.google.inject.ScopeAnnotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface ScopeAnnotationProvider
{
    List<Class<? extends Annotation>> getScopeAnnotations();

    static List<Class<? extends Annotation>> getAllScopeAnnotations()
    {
        List<Class<? extends Annotation>> out = new ArrayList<>();
        out.add(ScopeAnnotation.class);
        ServiceLoader<ScopeAnnotationProvider> loader = ServiceLoader.load(ScopeAnnotationProvider.class);
        for (ScopeAnnotationProvider scopeAnnotationProvider : loader)
        {
            out.addAll(scopeAnnotationProvider.getScopeAnnotations());
        }
        return out;
    }
}
