package com.google.inject.gee;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface BindingAnnotationProvider
{
    List<Class<? extends Annotation>> getBindingAnnotations();

    static List<Class<? extends Annotation>> getAllBindingAnnotations()
    {
        List<Class<? extends Annotation>> out = new ArrayList<>();
        out.add(BindingAnnotation.class);
        ServiceLoader<BindingAnnotationProvider> loader = ServiceLoader.load(BindingAnnotationProvider.class);
        for (BindingAnnotationProvider bindingAnnotationProvider : loader)
        {
            out.addAll(bindingAnnotationProvider.getBindingAnnotations());
        }
        return out;
    }
}
