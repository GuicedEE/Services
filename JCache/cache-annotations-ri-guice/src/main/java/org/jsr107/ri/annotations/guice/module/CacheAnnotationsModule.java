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

package org.jsr107.ri.annotations.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.guicedee.client.services.lifecycle.IGuiceModule;
import org.aopalliance.intercept.MethodInvocation;
import org.jsr107.ri.annotations.CacheContextSource;
import org.jsr107.ri.annotations.DefaultCacheKeyGenerator;
import org.jsr107.ri.annotations.DefaultCacheResolverFactory;
import org.jsr107.ri.annotations.guice.*;

import javax.cache.annotation.*;
import java.lang.reflect.Method;

/**
 * Standard cache module for binding all cache interceptors to their respective annotations. This module needs to be part of the
 * Guice injector instantiation to activate intercepting of the cache annotations.
 * Every interceptor is bound twice due to the fact that the annotations defining the joinpoints have retention type
 * Method and Type.
 *
 * @author Michael Stachel
 * @version $Revision$
 */
public class CacheAnnotationsModule
		extends AbstractModule implements IGuiceModule<CacheAnnotationsModule>
{

	@SuppressWarnings("unchecked")
	@Override
	protected void configure()
	{
		bind(CacheKeyGenerator.class).to(DefaultCacheKeyGenerator.class);
		bind(CacheResolverFactory.class).to(DefaultCacheResolverFactory.class);
		bind(new TypeLiteral<CacheContextSource<MethodInvocation>>()
		{
		}).to(CacheLookupUtil.class);

		CachePutInterceptor cachePutInterceptor = new CachePutInterceptor();
		requestInjection(cachePutInterceptor);
		bindInterceptor(Matchers.annotatedWith(CachePut.class), Matchers.any(), cachePutInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(CachePut.class).and(new MatcherNotSynthetic()), cachePutInterceptor);

		CacheResultInterceptor cacheResultInterceptor = new CacheResultInterceptor();
		requestInjection(cacheResultInterceptor);
		bindInterceptor(Matchers.annotatedWith(CacheResult.class), Matchers.any(), cacheResultInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(CacheResult.class)
		                                        .and(new MatcherNotSynthetic()), cacheResultInterceptor);

		CacheRemoveEntryInterceptor cacheRemoveEntryInterceptor = new CacheRemoveEntryInterceptor();
		requestInjection(cacheRemoveEntryInterceptor);
		bindInterceptor(Matchers.annotatedWith(CacheRemove.class), Matchers.any(), cacheRemoveEntryInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(CacheRemove.class).and(new MatcherNotSynthetic()), cacheRemoveEntryInterceptor);

		CacheRemoveAllInterceptor cacheRemoveAllInterceptor = new CacheRemoveAllInterceptor();
		requestInjection(cacheRemoveAllInterceptor);
		bindInterceptor(Matchers.annotatedWith(CacheRemoveAll.class), Matchers.any(), cacheRemoveAllInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(CacheRemoveAll.class).and(new MatcherNotSynthetic()), cacheRemoveAllInterceptor);
	}

	private static class MatcherNotSynthetic<M extends Method>
			extends AbstractMatcher<M>
			implements Matcher<M>
	{

		@Override
		public boolean matches(M m)
		{
			return !m.isSynthetic();
		}
	}
}
