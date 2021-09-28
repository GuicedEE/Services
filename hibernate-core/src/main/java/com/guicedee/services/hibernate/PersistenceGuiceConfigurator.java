package com.guicedee.services.hibernate;

import com.guicedee.guicedinjection.GuiceConfig;
import com.guicedee.guicedinjection.interfaces.IGuiceConfigurator;

public class PersistenceGuiceConfigurator
		implements IGuiceConfigurator
{
	@Override
	public GuiceConfig configure(GuiceConfig config)
	{
		config.setClasspathScanning(true)
				.setAnnotationScanning(true);
		return config;
	}
}
