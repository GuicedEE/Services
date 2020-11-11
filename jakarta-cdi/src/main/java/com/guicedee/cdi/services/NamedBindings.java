package com.guicedee.cdi.services;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.name.Names;
import io.github.classgraph.ClassInfo;

import jakarta.inject.Named;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.guicedee.guicedinjection.json.StaticStrings.*;

public class NamedBindings
{
	private static final Set<String> all = new HashSet<>();

	private static final Map<String,Class<?>> converters = new ConcurrentHashMap<>(5,2,1);
	private static final  Map<String,Class<?>> validators = new ConcurrentHashMap<>(5,2,1);

	/**
	 * Checks if bound, if not, adds to the list so next call its true
	 *
	 * @param name
	 *
	 * @return
	 */
	public static boolean isBound(String name)
	{
		boolean result = false;
		if (all.contains(name))
		{
			result = true;
		}
		else
		{
			all.add(name);
		}
		return result;
	}

	/**
	 * If name is empty, checks if the classInfo has a named annotation,
	 * if not then generate the name from the class name with the first letter as lowercase
	 *
	 * @param classInfo
	 * @param name
	 *
	 * @return
	 */
	public static String cleanName(ClassInfo classInfo, String name)
	{
		if (name.equals(STRING_EMPTY))
		{
			String namedName = STRING_EMPTY;
			if (classInfo.hasAnnotation(Named.class.getCanonicalName()))
			{
				namedName = classInfo.loadClass()
				                     .getAnnotation(Named.class)
				                     .value();
			}
			else if (classInfo.hasAnnotation(com.google.inject.name.Named.class.getCanonicalName()))
			{
				namedName = classInfo.loadClass()
				                     .getAnnotation(com.google.inject.name.Named.class)
				                     .value();
			}

			if (namedName.trim()
			             .isEmpty())
			{
				name = classInfo.getSimpleName();
				StringBuilder sb = new StringBuilder(name);
				sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
				name = sb.toString();
			}
			else
			{
				name = namedName;
			}
		}
		return name;
	}

	public static void bindToScope(Binder binder, Class<?> clazz, String name, Class<? extends Annotation> scopeAnnotation)
	{
		if (!isBound(name))
		{
			binder.bind(clazz)
			      .in(scopeAnnotation);
			binder.bind(Object.class)
			      .annotatedWith(Names.named(name))
			      .to(clazz)
			      .in(scopeAnnotation);
		}
	}

	public static void bindToScope(Binder binder, Class<?> clazz, String name)
	{
		if (!isBound(name))
		{
			binder.bind(clazz);
			binder.bind(Object.class)
			      .annotatedWith(Names.named(name))
			      .to(clazz);
		}
	}

	public static void bindToEagerSingleton(Binder binder, Class<?> clazz, String name)
	{
		if (!isBound(name))
		{
			binder.bind(clazz).asEagerSingleton();
			binder.bind(Object.class)
			      .annotatedWith(Names.named(name))
			      .to(clazz);
		}
	}

	public static Map<String, Class<?>> getConverters()
	{
		return converters;
	}

	public static Map<String, Class<?>> getValidators()
	{
		return validators;
	}
}
