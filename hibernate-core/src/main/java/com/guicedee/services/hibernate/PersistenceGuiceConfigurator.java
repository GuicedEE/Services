package com.guicedee.services.hibernate;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.guicedee.guicedinjection.GuiceConfig;
import com.guicedee.guicedinjection.implementations.ObjectMapperBinder;
import com.guicedee.guicedinjection.interfaces.IGuiceConfigurator;
import com.guicedee.guicedinjection.interfaces.ObjectBinderKeys;
import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;

public class PersistenceGuiceConfigurator
		implements IGuiceConfigurator
{
	@Override
	public GuiceConfig configure(GuiceConfig config)
	{
		return config.setPathScanning(true)
				.setClasspathScanning(true)
				.setExcludeModulesAndJars(true)
				.setExcludePaths(true)
				;
	}
}
