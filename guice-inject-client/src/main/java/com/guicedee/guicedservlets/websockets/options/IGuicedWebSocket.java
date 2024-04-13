package com.guicedee.guicedservlets.websockets.options;

public interface IGuicedWebSocket
{
    String EveryoneGroup = "Everyone";

    void addToGroup(String groupName);
    void removeFromGroup(String groupName);
    void broadcastMessage(String groupName, String message);
    void broadcastMessageSync(String groupName, String message);
}
