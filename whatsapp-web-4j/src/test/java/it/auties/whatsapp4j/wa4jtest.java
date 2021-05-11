package it.auties.whatsapp4j;

import it.auties.whatsapp4j.api.WhatsappAPI;
import it.auties.whatsapp4j.api.WhatsappConfiguration;
import it.auties.whatsapp4j.manager.WhatsappDataManager;
import it.auties.whatsapp4j.model.WhatsappChat;
import it.auties.whatsapp4j.model.WhatsappContact;

import java.net.*;
import java.net.http.*;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

public class wa4jtest
{
	public static void main(String[] args) throws ExecutionException, InterruptedException
	{
		HttpClient client = HttpClient.newHttpClient();
		CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
		                                        .header("Origin","https://web.whatsapp.com")
		                                        //.header("Host","web.whatsapp.com")
		                                        .buildAsync(URI.create("wss://web.whatsapp.com/ws"), new WebSocket.Listener()
		                                        {
			                                        @Override
			                                        public void onOpen(WebSocket webSocket)
			                                        {
				                                        WebSocket.Listener.super.onOpen(webSocket);
			                                        }
			
			                                        @Override
			                                        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last)
			                                        {
				                                        return WebSocket.Listener.super.onText(webSocket, data, last);
			                                        }
			
			                                        @Override
			                                        public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last)
			                                        {
				                                        return WebSocket.Listener.super.onBinary(webSocket, data, last);
			                                        }
			
			                                        @Override
			                                        public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message)
			                                        {
				                                        return WebSocket.Listener.super.onPing(webSocket, message);
			                                        }
			
			                                        @Override
			                                        public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message)
			                                        {
				                                        return WebSocket.Listener.super.onPong(webSocket, message);
			                                        }
			
			                                        @Override
			                                        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason)
			                                        {
				                                        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
			                                        }
			
			                                        @Override
			                                        public void onError(WebSocket webSocket, Throwable error)
			                                        {
				                                        WebSocket.Listener.super.onError(webSocket, error);
			                                        }
		                                        });
		WebSocket webSocket1 = ws.get();
		var configuration = WhatsappConfiguration.builder()
		                                         .whatsappUrl("wss://web.whatsapp.com/ws") // WhatsappWeb's WebSocket URL
		                                         .requestTag("BayportCheck1") // The tag used for requests made to WhatsappWeb's WebSocket
		                                         .description("test") // The description provided to Whatsapp during the authentication process
		                                         .shortDescription("test") // An acronym for the description
		                                         .reconnectWhenDisconnected((reason) -> true) // Determines whether the connection should be reclaimed
		                                         .async(false) // Determines whether requests sent to whatsapp should be asyncronous or not
		                                         .build(); // Builds an instance of WhatsappConfiguration
		
		var api = new WhatsappAPI(configuration);
		
		
		api.registerListener(new YourAwesomeListener(api));
		api.connect();
		
		WhatsappDataManager manager = api.manager();
		List<WhatsappChat> chats = manager.chats();
		List<WhatsappContact> contacts = manager.contacts();
	}
}
