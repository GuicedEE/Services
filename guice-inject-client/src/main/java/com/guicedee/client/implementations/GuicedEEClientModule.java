package com.guicedee.client.implementations;

import com.google.inject.AbstractModule;
import com.guicedee.client.CallScoper;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import com.guicedee.guicedservlets.servlets.services.scopes.CallScope;
import com.guicedee.guicedservlets.websockets.options.CallScopeProperties;

public class GuicedEEClientModule extends AbstractModule implements IGuiceModule<GuicedEEClientModule>
{
    @Override
    protected void configure()
    {
        bindScope(CallScope.class, new CallScoper());
        bind(CallScopeProperties.class).in(CallScope.class);
    }
}
