package com.guicedee.guicedservlets.undertow.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import io.undertow.server.HttpHandler;

import java.util.Map;

@FunctionalInterface
public interface UndertowPathHandler<J extends UndertowPathHandler<J>> extends IDefaultService<J>
{
	/**
	 * Returns a path prefix with the associated path handler
	 * @return The path prefix
	 */
	Map<String,HttpHandler> registerPathHandler();
}
