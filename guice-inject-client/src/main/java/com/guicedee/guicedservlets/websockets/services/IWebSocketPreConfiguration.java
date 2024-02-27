package com.guicedee.guicedservlets.websockets.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import com.guicedee.guicedinjection.interfaces.IServiceEnablement;

/**
 * A service for JWebMPWebSockets to configure app servers
 */
public interface IWebSocketPreConfiguration<J extends IWebSocketPreConfiguration<J>>
		extends IDefaultService<J>, IServiceEnablement<J>
{
	void configure();
}
