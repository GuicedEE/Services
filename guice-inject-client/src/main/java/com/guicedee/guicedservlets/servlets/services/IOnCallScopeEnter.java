package com.guicedee.guicedservlets.servlets.services;

import com.google.inject.Scope;
import com.guicedee.guicedinjection.interfaces.IDefaultService;

@FunctionalInterface
public interface IOnCallScopeEnter<J extends IOnCallScopeEnter<J>> extends IDefaultService<J> {
    void onScopeEnter(Scope scope);
}
