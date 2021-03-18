package com.guicedee.cdi.implementations;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.guicedee.cdi.services.NamedBindings;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import com.guicedee.logger.LogFactory;
import io.github.classgraph.ClassInfo;

import jakarta.annotation.PostConstruct;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;

import jakarta.inject.Named;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.guicedee.guicedinjection.json.StaticStrings.*;
import static java.lang.String.*;

public class GuicedCDIModule
		extends AbstractModule
		implements IGuiceModule<GuicedCDIModule>, TypeListener
{
	@Override
	protected void configure()
	{
		super.configure();
		bindListener(Matchers.any(), this);

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(ApplicationScoped.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("jakarta.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			String name = NamedBindings.cleanName(classInfo, STRING_EMPTY);
			NamedBindings.bindToScope(binder(), clazz, name, Singleton.class);
		}

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(RequestScoped.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("jakarta.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			String name = NamedBindings.cleanName(classInfo, STRING_EMPTY);
			NamedBindings.bindToScope(binder(), clazz, name, com.google.inject.servlet.RequestScoped.class);
		}

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(SessionScoped.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("jakarta.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			String name = NamedBindings.cleanName(classInfo, STRING_EMPTY);
			NamedBindings.bindToScope(binder(), clazz, name, com.google.inject.servlet.SessionScoped.class);
		}

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(Named.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("jakarta.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			String name = NamedBindings.cleanName(classInfo, STRING_EMPTY);
			NamedBindings.bindToScope(binder(), clazz, name);
		}

		for (ClassInfo classInfo : GuiceContext.instance()
				.getScanResult()
				.getClassesWithAnnotation(com.google.inject.name.Named.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
					|| classInfo.hasAnnotation("jakarta.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			String name = NamedBindings.cleanName(classInfo, STRING_EMPTY);
			NamedBindings.bindToScope(binder(), clazz, name);
		}
	}

	@Override
	public Integer sortOrder()
	{
		//Let faces bind converters and things first
		return 151;
	}

	private Map<Class<?>, Method> postConstructMethods = new HashMap<>();
	/**
	 * Call postconstruct method (if annotation exists).
	 */
	@Override
	public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter)
	{
		encounter.register((InjectionListener<I>) injectee ->
		{
			Class<?> clazz = injectee.getClass();
			if(postConstructMethods.containsKey(clazz))
			{
				try {
					postConstructMethods.get(clazz).invoke(injectee);
				} catch (IllegalAccessException e) {
					LogFactory.getLog(getClass()).log(Level.WARNING,
							"Illegal Access Exception to Cached Class Method during invoke of @PostConstruct - " + clazz.getCanonicalName(),e);
				} catch (InvocationTargetException e) {
					LogFactory.getLog(getClass()).log(Level.WARNING,
							"Invocation Target Exception to Cached Class Method during invoke of @PostConstruct - " + clazz.getCanonicalName(),e);
				}
				return ;
			}
			for (Method methodInfo : clazz.getDeclaredMethods())
			{
				if (methodInfo.isAnnotationPresent(PostConstruct.class))
				{
					try
					{
						methodInfo.setAccessible(true);
						postConstructMethods.put(clazz,methodInfo);
						methodInfo.invoke(injectee);
					}
					catch (final IllegalAccessException e)
					{
						LogFactory.getLog(getClass()).log(Level.WARNING,
								"Illegal Access Exception to Class during invoke of @PostConstruct - " + clazz.getCanonicalName(),e);
					}
					catch (final Exception e)
					{
						LogFactory.getLog(getClass()).log(Level.WARNING,
								"Invocation Target Exception to Class Method during invoke of @PostConstruct - " + clazz.getCanonicalName(),e);
						throw new RuntimeException(format("@PostConstruct %s", methodInfo), e);
					}
				}
			}
		});
	}
}
