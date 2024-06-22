package com.guicedee.guicedservlets.websockets.options;

import com.guicedee.guicedservlets.websockets.services.IWebSocketMessageReceiver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface IGuicedWebSocket
{
    Map<String, Set<Class<? extends IWebSocketMessageReceiver>>> messageListeners = new HashMap<>();

    String EveryoneGroup = "Everyone";

    void addToGroup(String groupName);
    void removeFromGroup(String groupName);
    void broadcastMessage(String groupName, String message);
    void broadcastMessage(String message);
    void broadcastMessageSync(String groupName, String message);

    static void addWebSocketMessageReceiver(IWebSocketMessageReceiver receiver)
    {
        for (String messageName : receiver.messageNames())
        {
            addReceiver(receiver, messageName);
        }
    }

    static boolean isWebSocketReceiverRegistered(String name)
    {
        return messageListeners.containsKey(name);
    }

    static void addReceiver(IWebSocketMessageReceiver messageReceiver, String action)
    {
        if (!messageListeners.containsKey(action))
        {
            messageListeners.put(action, new HashSet<>());
        }
        messageListeners.get(action)
                        .add(messageReceiver.getClass());
    }
}
