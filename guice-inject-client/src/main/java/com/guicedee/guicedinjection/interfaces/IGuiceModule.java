package com.guicedee.guicedinjection.interfaces;

/**
 * Service Locator for configuring the module
 */
public interface IGuiceModule<J extends com.google.inject.Module & IGuiceModule<J>>
				extends IDefaultService<J>
{

}
