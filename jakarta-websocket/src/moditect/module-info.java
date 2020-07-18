module javax.websocket.api {
	exports javax.websocket;
	exports javax.websocket.server;

	uses javax.websocket.ContainerProvider;
	uses javax.websocket.server.ServerEndpointConfig.Configurator;
}