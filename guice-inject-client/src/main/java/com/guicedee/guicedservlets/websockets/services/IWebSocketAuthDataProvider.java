package com.guicedee.guicedservlets.websockets.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import com.guicedee.guicedinjection.interfaces.IServiceEnablement;

/**
 * Service to load authentication data for web service
 */
@SuppressWarnings("unused")
public interface IWebSocketAuthDataProvider<J extends IWebSocketAuthDataProvider<J>>
		extends IDefaultService<J>, IServiceEnablement<J>
{
	StringBuilder getJavascriptToPopulate();

	String name();
}
