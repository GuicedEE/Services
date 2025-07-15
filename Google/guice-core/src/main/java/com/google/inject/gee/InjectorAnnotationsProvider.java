package com.google.inject.gee;

import com.google.inject.Inject;

import java.util.ServiceLoader;

public interface InjectorAnnotationsProvider
{
    boolean isInjectorAnnotation(Class<? extends java.lang.annotation.Annotation> annotationType);

    static boolean checkAllInjectorAnnotations(Class<? extends java.lang.annotation.Annotation> annotationType)
    {
        if(annotationType.equals(Inject.class))
            return true;
        ServiceLoader<InjectorAnnotationsProvider> loader = ServiceLoader.load(InjectorAnnotationsProvider.class);
        for (var annotationsProvider : loader)
        {
            boolean is = annotationsProvider.isInjectorAnnotation(annotationType);
            if(is)
            {
                return true;
            }
        }
        return false;
    }
}
