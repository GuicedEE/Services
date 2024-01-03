package com.guicedee.guicedservlets.websockets.services;
import com.guicedee.guicedinjection.interfaces.IDefaultService;

import jakarta.servlet.http.HttpSession;

/**
 * Returns the HTTPSession associated with the given id
 */
public interface IWebSocketSessionProvider extends IDefaultService<IWebSocketSessionProvider>
{
	HttpSession getSession(String sessionID);
}
