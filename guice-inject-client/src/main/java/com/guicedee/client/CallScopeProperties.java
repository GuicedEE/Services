package com.guicedee.client;

import com.guicedee.guicedservlets.servlets.services.scopes.CallScope;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@CallScope
@Getter
@Setter
@Accessors(chain = true)
public class CallScopeProperties implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * The source of the call scope entry
	 */
	private CallScopeSource source;
	/**
	 * Any properties to carry within the call scope
	 */
	private Map<Object, Object> properties = new HashMap<>();

}
