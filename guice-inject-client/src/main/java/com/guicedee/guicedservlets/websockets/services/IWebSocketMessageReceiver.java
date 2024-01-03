package com.guicedee.guicedservlets.websockets.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import com.guicedee.guicedservlets.websockets.options.WebSocketMessageReceiver;

import java.util.Set;

/**
 * Registers receivers for web socket messages
 */
public interface IWebSocketMessageReceiver
		extends IDefaultService<IWebSocketMessageReceiver>
{
	/**
	 * Returns a unique list of names that this applies for
	 *
	 * @return
	 */
	Set<String> messageNames();

	/**
	 * Receives a message on the web socket to a specific designated name registered on GuicedWebSocket
	 *
	 * @param message
	 * 		The message if required
	 *
	 * @throws java.lang.SecurityException
	 * 		if any consumer decides the connection is not valid
	 */
	void receiveMessage(WebSocketMessageReceiver<?> message) throws SecurityException;
}
