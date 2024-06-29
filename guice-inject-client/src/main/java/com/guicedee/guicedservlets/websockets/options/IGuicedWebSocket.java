package com.guicedee.guicedservlets.websockets.options;

import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedservlets.websockets.services.IWebSocketMessageReceiver;

import java.util.*;

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

    static void loadWebSocketReceivers()
    {
        Set<IWebSocketMessageReceiver> messageReceivers = IGuiceContext
                .instance()
                .getLoader(IWebSocketMessageReceiver.class, true, ServiceLoader.load(IWebSocketMessageReceiver.class));
        for (IWebSocketMessageReceiver messageReceiver : messageReceivers)
        {
            for (String s : messageReceiver.messageNames())
            {
                if (!IGuicedWebSocket.isWebSocketReceiverRegistered(s))
                {
                    IGuicedWebSocket.addReceiver(messageReceiver, s);
                }
            }
        }
    }
}
