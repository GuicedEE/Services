package com.guicedee.guicedservlets.websockets.options;

import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedservlets.websockets.services.IWebSocketMessageReceiver;

import java.util.*;

public interface IGuicedWebSocket
{
    Map<String, IWebSocketMessageReceiver> messageListeners = new HashMap<>();

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
        return messageListeners
                .containsKey(name);
    }

    ThreadLocal<Boolean> loadingReceivers = ThreadLocal.withInitial(() -> false);

    static void addReceiver(IWebSocketMessageReceiver messageReceiver, String action)
    {
        if (messageListeners
                .isEmpty() && !loadingReceivers.get())
        {
            loadingReceivers.set(true);
            IGuiceContext.loaderToSet(ServiceLoader.load(IWebSocketMessageReceiver.class))
                         .add(messageReceiver);
            loadWebSocketReceivers();
        }
        messageListeners
                .put(action,messageReceiver);
        Set<Class<IWebSocketMessageReceiver>> classes = IGuiceContext.loadClassSet(ServiceLoader.load(IWebSocketMessageReceiver.class));
        classes.add((Class<IWebSocketMessageReceiver>) messageReceiver.getClass());
    }

    static Map<String, IWebSocketMessageReceiver> getMessagesListeners()
    {
        if (messageListeners
                .isEmpty() && !loadingReceivers.get())
        {
            loadWebSocketReceivers();
        }
        return messageListeners;
    }

    static void loadWebSocketReceivers()
    {
        Set<IWebSocketMessageReceiver> messageReceivers = IGuiceContext.loaderToSet(ServiceLoader.load(IWebSocketMessageReceiver.class));
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
