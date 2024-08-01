package com.guicedee.client.implementations;

import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class GuicedEEClientStartup implements IGuicePreStartup<GuicedEEClientStartup>
{
    @Override
    public void onStartup()
    {
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
