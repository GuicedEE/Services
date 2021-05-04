package it.auties.whatsapp4j.socket;

import jakarta.validation.constraints.NotNull;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.HandshakeResponse;

import java.util.*;

/**
 * A class used to define a pair of headers necessary to start a session with WhatsappWeb's WebSocket.
 * Without these, WhatsappWeb's WebSocket would respond with a 401 http status error code.
 */
public class WhatsappSocketConfiguration extends ClientEndpointConfig.Configurator{
  @Override
  public void beforeRequest(@NotNull Map<String, List<String>> headers) {
    headers.put("Origin", Arrays.asList("https://web.whatsapp.com"));
    headers.put("Host", Arrays.asList("web.whatsapp.com"));
  }
  
  @Override
  public void afterResponse(HandshakeResponse hr) {
    Map<String, List<String>> headers = hr.getHeaders();
    System.out.println(headers);
    //log.info("headers -> "+headers);
  }
  
  
}