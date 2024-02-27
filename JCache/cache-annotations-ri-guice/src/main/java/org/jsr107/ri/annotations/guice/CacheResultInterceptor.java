/**
 * Copyright 2011-2013 Terracotta, Inc.
 * Copyright 2011-2013 Oracle America Incorporated
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jsr107.ri.annotations.guice;

import jakarta.inject.*;
import org.aopalliance.intercept.*;
import org.jsr107.ri.annotations.*;

import java.util.logging.*;

/**
 * @author Michael Stachel
 * @version $Revision$
 */
public class CacheResultInterceptor extends AbstractCacheResultInterceptor<MethodInvocation> implements CacheMethodInterceptor
{
	private static final Logger log = Logger.getLogger(CacheResultInterceptor.class.getCanonicalName());
	private CacheContextSource<MethodInvocation> cacheContextSource;
	
	/**
	 * @param cacheContextSource the CacheContextSource to use
	 */
	@Inject
	public void setCacheContextSource(CacheContextSource<MethodInvocation> cacheContextSource)
	{
		this.cacheContextSource = cacheContextSource;
	}
	
	@Override
	public InterceptorType getInterceptorType()
	{
		return InterceptorType.CACHE_RESULT;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable
	{
		try
		{
			return this.cacheResult(cacheContextSource, invocation);
		}
		catch (Throwable T)
		{
			log.log(Level.SEVERE, "Error reported from caching interceptor-ri-guice", T);
            throw T;
		}
	}
	
	@Override
	protected Object proceed(MethodInvocation invocation) throws Throwable
	{
		return invocation.proceed();
	}
	
}
