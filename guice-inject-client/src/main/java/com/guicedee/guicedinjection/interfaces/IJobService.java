package com.guicedee.guicedinjection.interfaces;

import com.guicedee.client.*;

import java.util.*;
import java.util.concurrent.*;

public interface IJobService
{
	ThreadLocal<IJobService> context = ThreadLocal.withInitial(() -> null);
	
	static IJobService getInstance()
	{
		if (context.get() == null)
		{
			ServiceLoader<IJobServiceProvider> load = ServiceLoader.load(IJobServiceProvider.class);
			for (IJobServiceProvider iGuiceProvider : load)
			{
				IJobService iGuiceContext = iGuiceProvider.get();
				context.set(iGuiceContext);
				break;
			}
		}
		return context.get();
	}
	
	/**
	 * Gets a list of all job pools currently registered
	 *
	 * @return
	 */
	Set<String> getJobPools();
	
	/**
	 * Returns the list of repeating task pools registered
	 *
	 * @return
	 */
	Set<String> getPollingPools();
	
	/**
	 * Completes and Removes all jobs running from the given pool
	 *
	 * @param pool The pool to remove
	 */
	ExecutorService removeJob(String pool);
	
	/**
	 * Completes and Removes all jobs running from the given pool
	 *
	 * @param pool The pool name to remove
	 */
	ScheduledExecutorService removePollingJob(String pool);
	
	/**
	 * Registers a new job pool with a specific service
	 *
	 * @param name
	 * @param executorService
	 */
	ExecutorService registerJobPool(String name, ExecutorService executorService);
	
	/**
	 * Registers a repeating task to be registered and monitored
	 *
	 * @param name            The name of the pool
	 * @param executorService The service executor
	 */
	ScheduledExecutorService registerJobPollingPool(String name, ScheduledExecutorService executorService);
	
	/**
	 * Adds a static run once job to the monitored collections
	 *
	 * @param jobPoolName
	 * @param thread
	 */
	ExecutorService addJob(String jobPoolName, Runnable thread);
	
	/**
	 * Adds a static run once job to the monitored collections
	 *
	 * @param jobPoolName
	 * @param thread
	 */
	Future<?> addTask(String jobPoolName, Callable<?> thread);
	
	void waitForJob(String jobName);
	
	void waitForJob(String jobName, long timeout, TimeUnit unit);
	
	/**
	 * Adds a static run once job to the monitored collections
	 *
	 * @param jobPoolName
	 * @param thread
	 */
	ScheduledExecutorService addPollingJob(String jobPoolName, Runnable thread, long delay, TimeUnit unit);
	
	/**
	 * Adds a static run once job to the monitored collections
	 *
	 * @param jobPoolName
	 * @param thread
	 */
	ScheduledExecutorService addPollingJob(String jobPoolName, Runnable thread, long initialDelay, long delay, TimeUnit unit);
	
	/**
	 * Shutdowns
	 */
	void destroy();

	ExecutorService removeJobNoWait(String pool);
}
