package com.guicedee.client.implementations;

import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class GuicedEEClientStartup implements IGuicePreStartup<GuicedEEClientStartup>
{
    @Override
    public void onStartup()
    {
        System.setProperty("org.jboss.logging.provider", "slf4j");
        LogManager.getLogManager()
                  .getLogger("")
                  .addHandler(new SLF4JBridgeHandler());
    }
}
