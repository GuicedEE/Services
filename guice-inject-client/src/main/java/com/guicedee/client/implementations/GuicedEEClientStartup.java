package com.guicedee.client.implementations;

import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;

public class GuicedEEClientStartup implements IGuicePreStartup<GuicedEEClientStartup>
{
    @Override
    public void onStartup()
    {
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }
}
