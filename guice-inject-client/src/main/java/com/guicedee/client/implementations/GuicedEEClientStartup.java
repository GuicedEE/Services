package com.guicedee.client.implementations;

import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;
import io.vertx.core.Future;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class GuicedEEClientStartup implements IGuicePreStartup<GuicedEEClientStartup>
{
    @Override
    public List<Future<Boolean>> onStartup()
    {
        log.trace("🚀 Starting GuicedEE Client initialization");
        try
        {
            log.debug("📋 Configuring GuicedEE scanning options");
            IGuiceContext.instance()
                         .getConfig()
                         .setFieldScanning(true)
                         .setMethodInfo(true)
                         .setIgnoreClassVisibility(true)
                         .setIgnoreMethodVisibility(true)
                         .setIgnoreFieldVisibility(true)
                         .setAnnotationScanning(true);
            log.debug("✅ GuicedEE scanning options configured successfully");
            log.trace("✅ GuicedEE Client initialized successfully");
        }catch (Throwable T)
        {
            log.error("❌ No Guice Client Instantiation Found: {}", T.getMessage(), T);
            log.error("💥 Please add guiced-injection to the classpath to resolve this issue");
        }
        log.debug("📤 Returning startup result");
        return List.of(Future.succeededFuture(true));
    }

    @Override
    public Integer sortOrder()
    {
        return Integer.MIN_VALUE + 1;
    }
}
