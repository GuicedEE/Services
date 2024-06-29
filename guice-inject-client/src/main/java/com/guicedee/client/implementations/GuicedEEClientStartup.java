package com.guicedee.client.implementations;

import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;
import com.guicedee.guicedservlets.websockets.options.IGuicedWebSocket;
import lombok.extern.java.Log;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;

@Log
public class GuicedEEClientStartup implements IGuicePreStartup<GuicedEEClientStartup>
{
    @Override
    public void onStartup()
    {
        System.setProperty("org.jboss.logging.provider", "slf4j");
        LogManager.getLogManager()
                  .getLogger("")
                  .addHandler(new SLF4JBridgeHandler());
        try
        {
            IGuiceContext.instance()
                         .getConfig()
                         .setFieldScanning(true)
                         .setMethodInfo(true)
                         .setIgnoreClassVisibility(true)
                         .setIgnoreMethodVisibility(true)
                         .setIgnoreFieldVisibility(true)
                         .setAnnotationScanning(true);
        }catch (Throwable T)
        {
            log.log(Level.SEVERE,"No Guice Client Instantiation Found. Please add guiced-injection to the classpath");
        }
    }
}
