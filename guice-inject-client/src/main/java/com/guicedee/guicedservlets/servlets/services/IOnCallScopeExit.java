package com.guicedee.guicedservlets.servlets.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;

@FunctionalInterface
public interface IOnCallScopeExit<J extends IOnCallScopeExit<J>> extends IDefaultService<J> {
    void onScopeExit();
}
