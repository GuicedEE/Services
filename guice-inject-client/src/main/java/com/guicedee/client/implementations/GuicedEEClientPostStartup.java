package com.guicedee.client.implementations;

import com.guicedee.guicedinjection.interfaces.IGuicePostStartup;
import com.guicedee.guicedservlets.websockets.options.IGuicedWebSocket;
import io.vertx.core.Future;

import java.util.List;

public class GuicedEEClientPostStartup implements IGuicePostStartup<GuicedEEClientPostStartup>
{

    @Override
    public List<Future<Boolean>> postLoad()
    {
        IGuicedWebSocket.loadWebSocketReceivers();
        return List.of(Future.succeededFuture(true));
    }

    @Override
    public Integer sortOrder()
    {
        return Integer.MIN_VALUE + 650;
    }
}
