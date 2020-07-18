package com.guicedee.faces.implementations;

import com.google.inject.AbstractModule;
import com.guicedee.cdi.services.NamedBindings;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import io.github.classgraph.ClassInfo;

import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.view.ViewScoped;

import static com.guicedee.guicedinjection.json.StaticStrings.*;

public class GuicedFacesModule
		extends AbstractModule
		implements IGuiceModule<GuicedFacesModule>
{
	@Override
	protected void configure()
	{
		super.configure();
		bindScope(ViewScoped.class, new ViewScopeImpl());

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(FacesConverter.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("javax.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			javax.faces.convert.FacesConverter nn = clazz.getAnnotation(javax.faces.convert.FacesConverter.class);
			String name = NamedBindings.cleanName(classInfo, nn.value());
			NamedBindings.bindToScope(binder(), clazz, name);
			NamedBindings.getConverters()
			             .put(name, clazz);
		}

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(FacesValidator.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("javax.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			FacesValidator nn = clazz.getAnnotation(FacesValidator.class);
			String name = NamedBindings.cleanName(classInfo, nn.value());
			NamedBindings.bindToScope(binder(), clazz, name);
			NamedBindings.getValidators()
			             .put(name, clazz);
		}


		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(ViewScoped.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("javax.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			String name = NamedBindings.cleanName(classInfo, STRING_EMPTY);
			NamedBindings.bindToScope(binder(), clazz, name, ViewScoped.class);
		}
	}

	@Override
	public Integer sortOrder()
	{
		return 150;
	}

}
