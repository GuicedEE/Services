package com.guicedee.guicedservlets.websockets.options;

import com.guicedee.guicedservlets.websockets.services.IWebSocketMessageReceiver;

public interface IGuicedWebSocket
{
    String EveryoneGroup = "Everyone";

    void addToGroup(String groupName);
    void removeFromGroup(String groupName);
    void broadcastMessage(String groupName, String message);
    void broadcastMessageSync(String groupName, String message);
    void addWebSocketMessageReceiver(IWebSocketMessageReceiver receiver);
    void addReceiver(IWebSocketMessageReceiver messageReceiver, String action);
    boolean isWebSocketReceiverRegistered(String name);
}
