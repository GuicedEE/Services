package com.guicedee.cdi.implementations;

import com.guicedee.guicedinjection.GuiceConfig;
import com.guicedee.guicedinjection.interfaces.IGuiceConfigurator;

public class GuicedCDIConfigurator
		implements IGuiceConfigurator
{
	@Override
	public GuiceConfig<?> configure(GuiceConfig config)
	{
		config.setPathScanning(true);
		config.setExcludePaths(true);
		config.setClasspathScanning(true);
		config.setAnnotationScanning(true);
		config.setMethodInfo(true);

		config.setExcludeModulesAndJars(true);

		return config;
	}
}
