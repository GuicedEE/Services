/*
 * Copyright (C) 2017 GedMarc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.guicedee.guicedinjection.interfaces;

import com.guicedee.client.IGuiceContext;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executes immediately after Guice has been initialized
 *
 * @author GedMarc
 * @since 15 May 2017
 */
public interface IGuicePostStartup<J extends IGuicePostStartup<J>>
        extends IDefaultService<J>
{
    /**
     * Runs immediately after the post load
     */
    List<Future<Boolean>> postLoad();

    default Future<Boolean> executeSingle(Callable<Boolean> callable, boolean grouped)
    {
        Promise<Boolean> promise = Promise.promise();
        execute(callable, grouped)
                .onComplete(promise::complete, promise::fail);
        return promise.future();
    }

    default Future<Boolean> execute(Callable<Boolean> callable, boolean grouped)
    {
        Promise<Boolean> promise = Promise.promise();
        var executor = getVertx().createSharedWorkerExecutor("startup.worker.pool");
        executor.executeBlocking(callable, grouped)
                .onComplete(((result, failure) -> {
                    promise.complete(result, failure);
                }));
        return promise.future();
    }

    /**
     * Sets the order in which this must run, default 100.
     *
     * @return the sort order to return
     */
    @Override
    default Integer sortOrder()
    {
        return 50;
    }

    default Vertx getVertx()
    {
        return IGuiceContext.get(Vertx.class);
    }

}
