/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.service.jta.platform.internal;

import java.lang.reflect.Method;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformException;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformProvider;

/**
 * @author Steve Ebersole
 */
public class BitronixJtaPlatform extends AbstractJtaPlatform implements JtaPlatformProvider
{
	public static final String TM_CLASS_NAME = "bitronix.tm.TransactionManagerServices";

	@Override
	protected TransactionManager locateTransactionManager() {
		try {
			final Method getTransactionManagerMethod =
					serviceRegistry().requireService( ClassLoaderService.class )
							.classForName( TM_CLASS_NAME )
							.getMethod( "getTransactionManager" );
			return (TransactionManager) getTransactionManagerMethod.invoke( null );
		}
		catch (Exception e) {
			throw new JtaPlatformException( "Could not locate Bitronix TransactionManager", e );
		}
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		return (UserTransaction) jndiService().locate( "java:comp/UserTransaction" );
	}

	@Override
	public @UnknownKeyFor @NonNull @Initialized JtaPlatform getProvidedJtaPlatform()
	{
		return this;
	}
}
