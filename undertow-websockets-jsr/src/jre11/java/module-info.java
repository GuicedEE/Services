module undertow.websockets.jsr {
	requires javax.websocket.api;
	requires undertow.servlet;

	requires undertow.core;

	requires java.servlet;
	requires org.jboss.logging;
	requires java.annotation;
}
