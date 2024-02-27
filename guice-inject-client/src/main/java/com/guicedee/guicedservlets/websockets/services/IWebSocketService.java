package com.guicedee.guicedservlets.websockets.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import com.guicedee.guicedinjection.interfaces.IServiceEnablement;
import com.guicedee.guicedservlets.websockets.options.WebSocketMessageReceiver;

import jakarta.websocket.Session;

public interface IWebSocketService
		extends IDefaultService<IWebSocketService>, IServiceEnablement<IWebSocketService>
{
	void onOpen(Session session);

	void onClose(Session session);

	void onMessage(String message, Session session, WebSocketMessageReceiver<?> messageReceiver);

	void onError(Throwable t);
}
