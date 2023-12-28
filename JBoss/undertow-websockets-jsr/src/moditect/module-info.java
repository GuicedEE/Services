module undertow.websockets.jsr {

	exports io.undertow.websockets.jsr;
	//exports io.undertow.websockets.jsr.util;
	exports io.undertow.websockets.jsr.annotated;
	//exports io.undertow.websockets.jsr.handshake;

	requires jakarta.websocket.api;
	requires undertow.servlet;

	requires undertow.core;

	requires jakarta.servlet;

	requires org.jboss.logging;
	requires jakarta.annotation;

	opens io.undertow.websockets.jsr to org.jboss.logging, undertow.servlet;

	uses io.undertow.websockets.jsr.WebsocketClientSslProvider;

	provides jakarta.websocket.ContainerProvider with io.undertow.websockets.jsr.UndertowContainerProvider;
	provides jakarta.websocket.server.ServerEndpointConfig.Configurator with io.undertow.websockets.jsr.DefaultContainerConfigurator;
	provides io.undertow.websockets.jsr.WebsocketClientSslProvider with io.undertow.websockets.jsr.DefaultWebSocketClientSslProvider;
	provides io.undertow.servlet.ServletExtension with io.undertow.websockets.jsr.Bootstrap;
}
