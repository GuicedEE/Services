/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal.shade;

import org.hibernate.loader.ast.internal.LoaderSelectBuilder;
import org.hibernate.loader.ast.internal.SingleIdLoadPlan;
import org.hibernate.loader.ast.internal.SingleIdEntityLoaderSupport;

import static org.hibernate.internal.util.config.ConfigurationHelper.getBoolean;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import org.hibernate.Internal;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.loader.ast.spi.CascadingFetchProfile;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.sql.ast.spi.SqlAliasBaseManager;
import org.hibernate.sql.exec.spi.JdbcParametersList;

/**
 * Standard implementation of {@link org.hibernate.loader.ast.spi.SingleIdEntityLoader}.
 *
 * @author Steve Ebersole
 */
public class SingleIdEntityLoaderStandardImpl<T> extends SingleIdEntityLoaderSupport<T> {

	private final ConcurrentMap<LockMode, SingleIdLoadPlan<T>> selectByLockMode =
			new ConcurrentHashMap<>();
	private final ConcurrentMap<CascadingFetchProfile, ConcurrentMap<LockMode,SingleIdLoadPlan<T>>> selectByInternalCascadeProfile =
			new ConcurrentHashMap<>();

	private final BiFunction<LockOptions, LoadQueryInfluencers, SingleIdLoadPlan<T>> loadPlanCreator;

	public SingleIdEntityLoaderStandardImpl(
			EntityMappingType entityDescriptor,
			LoadQueryInfluencers loadQueryInfluencers) {
		this( entityDescriptor, loadQueryInfluencers,
				(lockOptions, influencers) -> createLoadPlan( entityDescriptor, lockOptions, influencers) );
	}

	/**
	 * For Hibernate Reactive.
	 * <p>
	 * Hibernate Reactive needs to be able to override the LoadPlan.
	 * </p>
	 */
	protected SingleIdEntityLoaderStandardImpl(
			EntityMappingType entityDescriptor,
			LoadQueryInfluencers influencers,
			BiFunction<LockOptions, LoadQueryInfluencers, SingleIdLoadPlan<T>> loadPlanCreator) {
		// todo (6.0) : consider creating a base AST and "cloning" it
		super( entityDescriptor, influencers.getSessionFactory() );
		this.loadPlanCreator = loadPlanCreator;
		// A projection query or a write does not require an identifier-load plan.
		// Other lock modes are already lazy upstream; treat NONE the same way.
		// Preserve an explicit eager option for applications that prefer prewarming.
		if (getBoolean("guicedee.persistence.eager_entity_load_plans", sessionFactory.getProperties(), false)) {
			selectByLockMode.put(LockMode.NONE, loadPlanCreator.apply(LockOptions.NONE, influencers));
		}
	}

	@Override
	public T load(Object key, LockOptions lockOptions, Boolean readOnly, SharedSessionContractImplementor session) {
		return resolveLoadPlan( lockOptions, session.getLoadQueryInfluencers() )
				.load( key, readOnly, true, session );
	}

	@Override
	public T load(
			Object key,
			Object entityInstance,
			LockOptions lockOptions,
			Boolean readOnly,
			SharedSessionContractImplementor session) {
		return resolveLoadPlan( lockOptions, session.getLoadQueryInfluencers() )
				.load( key, entityInstance, readOnly, false, session );
	}

	@Internal // public for tests, also called by Hibernate Reactive
	public SingleIdLoadPlan<T> resolveLoadPlan(LockOptions lockOptions, LoadQueryInfluencers influencers) {
		if ( getLoadable().isAffectedByEnabledFilters( influencers, true ) ) {
			// This case is special because the filters need to be applied in order to
			// properly restrict the SQL/JDBC results.  For this reason it has higher
			// precedence than even "internal" fetch profiles.
			return loadPlanCreator.apply( lockOptions, influencers );
		}
		else if ( influencers.getTemporalIdentifier() != null
				&& getLoadable().getEntityPersister().getAuditMapping() != null ) {
			// Audit context requires a fresh plan that excludes @Audited.Excluded
			// columns (which don't exist in the audit table)
			return loadPlanCreator.apply( lockOptions, influencers );
		}
		else if ( influencers.hasEnabledCascadingFetchProfile()
				// and if it's a non-exclusive (optimistic) lock
				&& LockMode.PESSIMISTIC_READ.greaterThan( lockOptions.getLockMode() ) ) {
			return getInternalCascadeLoadPlan( lockOptions, influencers );
		}
		else {
			// otherwise see if the loader for the requested load can be cached,
			// which also means we should look in the cache for an existing one
			return getRegularLoadPlan( lockOptions, influencers );
		}
	}

	private SingleIdLoadPlan<T> getRegularLoadPlan(LockOptions lockOptions, LoadQueryInfluencers influencers) {
		if ( isLoadPlanReusable( lockOptions, influencers )  ) {
			final var existing = selectByLockMode.get( lockOptions.getLockMode() );
			if ( existing != null ) {
				return existing;
			}
			else {
				final var singleIdLoadPlan = loadPlanCreator.apply( lockOptions, influencers );
				final var raced = selectByLockMode.putIfAbsent( lockOptions.getLockMode(), singleIdLoadPlan );
				return raced == null ? singleIdLoadPlan : raced;
			}
		}
		else {
			return loadPlanCreator.apply( lockOptions, influencers );
		}
	}

	private SingleIdLoadPlan<T> getInternalCascadeLoadPlan(LockOptions lockOptions, LoadQueryInfluencers influencers) {
		final var lockMode = lockOptions.getLockMode();
		final ConcurrentMap<LockMode, SingleIdLoadPlan<T>> map;
		if (isLoadPlanReusable(lockOptions, influencers)) {
			map = selectByInternalCascadeProfile.computeIfAbsent(
					influencers.getEnabledCascadingFetchProfile(), ignored -> new ConcurrentHashMap<>());
			final var existing = map.get(lockMode);
			if (existing != null) return existing;
		} else {
			map = null;
		}
		final var plan = loadPlanCreator.apply( lockOptions, influencers );
		if ( map != null ) {
			final var raced = map.putIfAbsent( lockMode, plan );
			if (raced != null) return raced;
		}
		return plan;
	}

	/**
	 * We key the caches only by {@link LockMode} and {@link CascadingFetchProfile}.
	 * If there is a pessimistic lock with non-default options like timeout, a custom
	 * fetch profile, or an entity graph, we don't cache and reuse the plan.
	 */
	private boolean isLoadPlanReusable(LockOptions lockOptions, LoadQueryInfluencers influencers) {
		if ( lockOptions.getLockMode().isPessimistic() && lockOptions.hasNonDefaultOptions() ) {
			return false;
		}
		else {
			final var loadable = getLoadable();
			return !loadable.isAffectedByEntityGraph( influencers )
				&& !loadable.isAffectedByEnabledFetchProfiles( influencers );
		}
	}

	private static <T> SingleIdLoadPlan<T> createLoadPlan(
			EntityMappingType loadable,
			LockOptions lockOptions,
			LoadQueryInfluencers influencers) {
		final var jdbcParametersBuilder = JdbcParametersList.newBuilder();
		final var factory = influencers.getSessionFactory();
		return new SingleIdLoadPlan<>(
				loadable,
				loadable.getIdentifierMapping(),
				LoaderSelectBuilder.createSelect(
						loadable,
						// null here means to select everything
						null,
						loadable.getIdentifierMapping(),
						null,
						1,
						influencers,
						lockOptions,
						jdbcParametersBuilder::add,
						new SqlAliasBaseManager(),
						factory
				),
				jdbcParametersBuilder.build(),
				lockOptions,
				factory
		);
	}
}
