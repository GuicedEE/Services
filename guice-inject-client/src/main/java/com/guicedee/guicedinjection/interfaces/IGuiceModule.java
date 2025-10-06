package com.guicedee.guicedinjection.interfaces;

/**
 * Service Locator for configuring the module
 */
public interface IGuiceModule<J extends IGuiceModule<J>>
		extends IDefaultService<J>, com.google.inject.Module
{
	default boolean enabled()
	{
		return true;
	}
}
