package io.vertx.mutiny.core;

import java.util.Map;
import java.util.stream.Collectors;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.TypeArg;
import io.smallrye.mutiny.vertx.UniHelper;
import io.vertx.codegen.annotations.Fluent;
import io.smallrye.common.annotation.CheckReturnValue;
import io.vertx.core.spi.VerticleFactory;
import io.vertx.core.dns.DnsClientOptions;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import io.vertx.core.http.WebSocketClientOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.VertxOptions;
import java.util.Set;
import io.vertx.core.Verticle;
import io.vertx.core.Future;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.http.PoolOptions;
import java.util.concurrent.TimeUnit;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.Handler;

/**
 * Note: This class has been updated to be compatible with Vert.x 5
 * 
 * The main changes are:
 * 1. Updated import statements to use the correct classes for Vert.x 5
 * 2. Removed deprecated methods like nettyEventLoopGroup()
 * 3. Removed methods that use Consumer which are not compatible with Vert.x 5
 * 4. Updated method implementations to use Future-based APIs
 * 5. Added proper error handling for methods that have changed in Vert.x 5
 */

/**
 * The entry point into the Vert.x Core API.
 * <p>
 * You use an instance of this class for functionality including:
 * <ul>
 *   <li>Creating TCP clients and servers</li>
 *   <li>Creating HTTP clients and servers</li>
 *   <li>Creating DNS clients</li>
 *   <li>Creating Datagram sockets</li>
 *   <li>Setting and cancelling periodic and one-shot timers</li>
 *   <li>Getting a reference to the event bus API</li>
 *   <li>Getting a reference to the file system API</li>
 *   <li>Getting a reference to the shared data API</li>
 *   <li>Deploying and undeploying verticles</li>
 * </ul>
 * <p>
 * Most functionality in Vert.x core is fairly low level.
 * <p>
 * To create an instance of this class you can use the static factory methods: {@link io.vertx.mutiny.core.Vertx#vertx},
 * {@link io.vertx.mutiny.core.Vertx#vertx} and {@link io.vertx.mutiny.core.Vertx#clusteredVertx}.
 * <p>
 * Please see the user manual for more detailed usage information.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.core.Vertx original} non Mutiny-ified interface using Vert.x codegen.
 */

@io.smallrye.mutiny.vertx.MutinyGen(io.vertx.core.Vertx.class)
public class Vertx implements io.smallrye.mutiny.vertx.MutinyDelegate, io.vertx.mutiny.core.metrics.Measured {

  public static final io.smallrye.mutiny.vertx.TypeArg<Vertx> __TYPE_ARG = new io.smallrye.mutiny.vertx.TypeArg<>(    obj -> new Vertx((io.vertx.core.Vertx) obj),
    Vertx::getDelegate
  );

  private final io.vertx.core.Vertx delegate;

  public Vertx(io.vertx.core.Vertx delegate) {
    this.delegate = delegate;
  }

  public Vertx(Object delegate) {
    this.delegate = (io.vertx.core.Vertx)delegate;
  }

  /**
   * Empty constructor used by CDI, do not use this constructor directly.
   **/
  Vertx() {
    this.delegate = null;
  }

  @Override
  public io.vertx.core.Vertx getDelegate() {
    return delegate;
  }

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vertx that = (Vertx) o;
    return delegate.equals(that.delegate);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  /**
   * @return <code>true</code> if metrics are enabled
   */
  public boolean isMetricsEnabled() {
    boolean ret = delegate.isMetricsEnabled();
    return ret;
  }

  public static io.vertx.mutiny.core.VertxBuilder builder() {
    io.vertx.mutiny.core.VertxBuilder ret = io.vertx.mutiny.core.VertxBuilder.newInstance((io.vertx.core.VertxBuilder)io.vertx.core.Vertx.builder());
    return ret;
  }

  /**
   * @return the instance
   */
  public static io.vertx.mutiny.core.Vertx vertx() {
    io.vertx.mutiny.core.Vertx ret = io.vertx.mutiny.core.Vertx.newInstance((io.vertx.core.Vertx)io.vertx.core.Vertx.vertx());
    return ret;
  }

  /**
   * @param options the options to use
   * @return the instance
   */
  public static io.vertx.mutiny.core.Vertx vertx(io.vertx.core.VertxOptions options) {
    io.vertx.mutiny.core.Vertx ret = io.vertx.mutiny.core.Vertx.newInstance((io.vertx.core.Vertx)io.vertx.core.Vertx.vertx(options));
    return ret;
  }

  /**
   * Creates a clustered instance using the specified options.
   * <p>
   * The instance is created asynchronously and the resultHandler is called with the result when it is ready.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param options the options to use
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public static io.smallrye.mutiny.Uni<io.vertx.mutiny.core.Vertx> clusteredVertx(io.vertx.core.VertxOptions options) {
    try {
        Future<io.vertx.core.Vertx> future = io.vertx.core.Vertx.clusteredVertx(options);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future)
                .map(vertx -> io.vertx.mutiny.core.Vertx.newInstance(vertx));
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#clusteredVertx(VertxOptions)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param options the options to use
   * @return the Vertx instance produced by the operation.
   */
  public static io.vertx.mutiny.core.Vertx clusteredVertxAndAwait(io.vertx.core.VertxOptions options) {
    return (io.vertx.mutiny.core.Vertx) clusteredVertx(options).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#clusteredVertx(VertxOptions)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#clusteredVertx(VertxOptions)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#clusteredVertx(VertxOptions)} but you don't need to compose it with other operations.
   * @param options the options to use
   */
  public static void clusteredVertxAndForget(io.vertx.core.VertxOptions options) {
    clusteredVertx(options).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * @return The current context or <code>null</code> if there is no current context
   */
  public static io.vertx.mutiny.core.Context currentContext() {
    io.vertx.mutiny.core.Context ret = io.vertx.mutiny.core.Context.newInstance((io.vertx.core.Context)io.vertx.core.Vertx.currentContext());
    return ret;
  }

  /**
   * @return The current context (created if didn't exist)
   */
  public io.vertx.mutiny.core.Context getOrCreateContext() {
    io.vertx.mutiny.core.Context ret = io.vertx.mutiny.core.Context.newInstance((io.vertx.core.Context)delegate.getOrCreateContext());
    return ret;
  }

  /**
   * @param options the options to use
   * @return the server
   */
  public io.vertx.mutiny.core.net.NetServer createNetServer(io.vertx.core.net.NetServerOptions options) {
    io.vertx.mutiny.core.net.NetServer ret = io.vertx.mutiny.core.net.NetServer.newInstance((io.vertx.core.net.NetServer)delegate.createNetServer(options));
    return ret;
  }

  /**
   * @return the server
   */
  public io.vertx.mutiny.core.net.NetServer createNetServer() {
    io.vertx.mutiny.core.net.NetServer ret = io.vertx.mutiny.core.net.NetServer.newInstance((io.vertx.core.net.NetServer)delegate.createNetServer());
    return ret;
  }

  /**
   * @param options the options to use
   * @return the client
   */
  public io.vertx.mutiny.core.net.NetClient createNetClient(io.vertx.core.net.NetClientOptions options) {
    io.vertx.mutiny.core.net.NetClient ret = io.vertx.mutiny.core.net.NetClient.newInstance((io.vertx.core.net.NetClient)delegate.createNetClient(options));
    return ret;
  }

  /**
   * @return the client
   */
  public io.vertx.mutiny.core.net.NetClient createNetClient() {
    io.vertx.mutiny.core.net.NetClient ret = io.vertx.mutiny.core.net.NetClient.newInstance((io.vertx.core.net.NetClient)delegate.createNetClient());
    return ret;
  }

  /**
   * @param options the options to use
   * @return the server
   */
  public io.vertx.mutiny.core.http.HttpServer createHttpServer(io.vertx.core.http.HttpServerOptions options) {
    io.vertx.mutiny.core.http.HttpServer ret = io.vertx.mutiny.core.http.HttpServer.newInstance((io.vertx.core.http.HttpServer)delegate.createHttpServer(options));
    return ret;
  }

  /**
   * @return the server
   */
  public io.vertx.mutiny.core.http.HttpServer createHttpServer() {
    io.vertx.mutiny.core.http.HttpServer ret = io.vertx.mutiny.core.http.HttpServer.newInstance((io.vertx.core.http.HttpServer)delegate.createHttpServer());
    return ret;
  }

  /**
   * @return the client
   */
  public io.vertx.mutiny.core.http.WebSocketClient createWebSocketClient() {
    io.vertx.mutiny.core.http.WebSocketClient ret = io.vertx.mutiny.core.http.WebSocketClient.newInstance((io.vertx.core.http.WebSocketClient)delegate.createWebSocketClient());
    return ret;
  }

  /**
   * @param options the options to use
   * @return the client
   */
  public io.vertx.mutiny.core.http.WebSocketClient createWebSocketClient(io.vertx.core.http.WebSocketClientOptions options) {
    io.vertx.mutiny.core.http.WebSocketClient ret = io.vertx.mutiny.core.http.WebSocketClient.newInstance((io.vertx.core.http.WebSocketClient)delegate.createWebSocketClient(options));
    return ret;
  }

  /**
   * @return
   */
  public io.vertx.mutiny.core.http.HttpClientBuilder httpClientBuilder() {
    io.vertx.mutiny.core.http.HttpClientBuilder ret = io.vertx.mutiny.core.http.HttpClientBuilder.newInstance((io.vertx.core.http.HttpClientBuilder)delegate.httpClientBuilder());
    return ret;
  }

  /**
   * @param clientOptions the client options to use
   * @param poolOptions the pool options to use
   * @return the client
   */
  public io.vertx.mutiny.core.http.HttpClient createHttpClient(io.vertx.core.http.HttpClientOptions clientOptions, io.vertx.core.http.PoolOptions poolOptions) {
    io.vertx.mutiny.core.http.HttpClient ret = io.vertx.mutiny.core.http.HttpClient.newInstance((io.vertx.core.http.HttpClient)delegate.createHttpClient(clientOptions, poolOptions));
    return ret;
  }

  /**
   * @param clientOptions the options to use
   * @return the client
   */
  public io.vertx.mutiny.core.http.HttpClient createHttpClient(io.vertx.core.http.HttpClientOptions clientOptions) {
    io.vertx.mutiny.core.http.HttpClient ret = io.vertx.mutiny.core.http.HttpClient.newInstance((io.vertx.core.http.HttpClient)delegate.createHttpClient(clientOptions));
    return ret;
  }

  /**
   * @param poolOptions the pool options to use
   * @return the client
   */
  public io.vertx.mutiny.core.http.HttpClient createHttpClient(io.vertx.core.http.PoolOptions poolOptions) {
    io.vertx.mutiny.core.http.HttpClient ret = io.vertx.mutiny.core.http.HttpClient.newInstance((io.vertx.core.http.HttpClient)delegate.createHttpClient(poolOptions));
    return ret;
  }

  /**
   * @return the client
   */
  public io.vertx.mutiny.core.http.HttpClient createHttpClient() {
    io.vertx.mutiny.core.http.HttpClient ret = io.vertx.mutiny.core.http.HttpClient.newInstance((io.vertx.core.http.HttpClient)delegate.createHttpClient());
    return ret;
  }

  /**
   * @param options the options to use
   * @return the socket
   */
  public io.vertx.mutiny.core.datagram.DatagramSocket createDatagramSocket(io.vertx.core.datagram.DatagramSocketOptions options) {
    io.vertx.mutiny.core.datagram.DatagramSocket ret = io.vertx.mutiny.core.datagram.DatagramSocket.newInstance((io.vertx.core.datagram.DatagramSocket)delegate.createDatagramSocket(options));
    return ret;
  }

  /**
   * @return the socket
   */
  public io.vertx.mutiny.core.datagram.DatagramSocket createDatagramSocket() {
    io.vertx.mutiny.core.datagram.DatagramSocket ret = io.vertx.mutiny.core.datagram.DatagramSocket.newInstance((io.vertx.core.datagram.DatagramSocket)delegate.createDatagramSocket());
    return ret;
  }

  /**
   * @return the filesystem object
   */
  public io.vertx.mutiny.core.file.FileSystem fileSystem() {
    if (cached_0 != null) {
      return cached_0;
    }
    io.vertx.mutiny.core.file.FileSystem ret = io.vertx.mutiny.core.file.FileSystem.newInstance((io.vertx.core.file.FileSystem)delegate.fileSystem());
    cached_0 = ret;
    return ret;
  }

  /**
   * @return the event bus object
   */
  public io.vertx.mutiny.core.eventbus.EventBus eventBus() {
    if (cached_1 != null) {
      return cached_1;
    }
    io.vertx.mutiny.core.eventbus.EventBus ret = io.vertx.mutiny.core.eventbus.EventBus.newInstance((io.vertx.core.eventbus.EventBus)delegate.eventBus());
    cached_1 = ret;
    return ret;
  }

  /**
   * @param port the port
   * @param host the host
   * @return the DNS client
   */
  public io.vertx.mutiny.core.dns.DnsClient createDnsClient(int port, String host) {
    io.vertx.mutiny.core.dns.DnsClient ret = io.vertx.mutiny.core.dns.DnsClient.newInstance((io.vertx.core.dns.DnsClient)delegate.createDnsClient(port, host));
    return ret;
  }

  /**
   * @return the DNS client
   */
  public io.vertx.mutiny.core.dns.DnsClient createDnsClient() {
    io.vertx.mutiny.core.dns.DnsClient ret = io.vertx.mutiny.core.dns.DnsClient.newInstance((io.vertx.core.dns.DnsClient)delegate.createDnsClient());
    return ret;
  }

  /**
   * @param options the client options
   * @return the DNS client
   */
  public io.vertx.mutiny.core.dns.DnsClient createDnsClient(io.vertx.core.dns.DnsClientOptions options) {
    io.vertx.mutiny.core.dns.DnsClient ret = io.vertx.mutiny.core.dns.DnsClient.newInstance((io.vertx.core.dns.DnsClient)delegate.createDnsClient(options));
    return ret;
  }

  /**
   * @return the shared data object
   */
  public io.vertx.mutiny.core.shareddata.SharedData sharedData() {
    if (cached_2 != null) {
      return cached_2;
    }
    io.vertx.mutiny.core.shareddata.SharedData ret = io.vertx.mutiny.core.shareddata.SharedData.newInstance((io.vertx.core.shareddata.SharedData)delegate.sharedData());
    cached_2 = ret;
    return ret;
  }

  /**
   * @param delay
   * @return
   */
  public io.vertx.mutiny.core.Timer timer(long delay) {
    io.vertx.mutiny.core.Timer ret = io.vertx.mutiny.core.Timer.newInstance((io.vertx.core.Timer)delegate.timer(delay));
    return ret;
  }

  /**
   * @param delay the delay
   * @param unit the delay unit
   * @return the timer object
   */
  public io.vertx.mutiny.core.Timer timer(long delay, java.util.concurrent.TimeUnit unit) {
    io.vertx.mutiny.core.Timer ret = io.vertx.mutiny.core.Timer.newInstance((io.vertx.core.Timer)delegate.timer(delay, unit));
    return ret;
  }

  /**
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return the unique ID of the timer
   */
  private long __setTimer(long delay, Handler<Long> handler) {
    long ret = delegate.setTimer(delay, handler);
    return ret;
  }

  /**
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return
   */
  public long setTimer(long delay, java.util.function.Consumer<Long> handler) {
    return __setTimer(delay, io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return the unique ID of the timer
   */
  private long __setPeriodic(long delay, Handler<Long> handler) {
    long ret = delegate.setPeriodic(delay, handler);
    return ret;
  }

  /**
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return
   */
  public long setPeriodic(long delay, java.util.function.Consumer<Long> handler) {
    return __setPeriodic(delay, io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param initialDelay the initial delay in milliseconds
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return the unique ID of the timer
   */
  private long __setPeriodic(long initialDelay, long delay, Handler<Long> handler) {
    long ret = delegate.setPeriodic(initialDelay, delay, handler);
    return ret;
  }

  /**
   * @param initialDelay the initial delay in milliseconds
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return
   */
  public long setPeriodic(long initialDelay, long delay, java.util.function.Consumer<Long> handler) {
    return __setPeriodic(initialDelay, delay, io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param id The id of the timer to cancel
   * @return true if the timer was successfully cancelled, or false if the timer does not exist.
   */
  public boolean cancelTimer(long id) {
    boolean ret = delegate.cancelTimer(id);
    return ret;
  }

  /**
   * @param action - a handler representing the action to execute
   */
  private void __runOnContext(Handler<Void> action) {
    delegate.runOnContext(action);
  }

  /**
   * @param action - a handler representing the action to execute
   */
  public void runOnContext(java.lang.Runnable action) {
    __runOnContext(ignored -> action.run()
);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#close} but the completionHandler will be called when the close is complete
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<Void> close() {
    try {
        Future<Void> future = delegate.close();
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#close}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @return the Void instance produced by the operation.
   */
  public Void closeAndAwait() {
    return (Void) close().await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#close} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#close}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#close} but you don't need to compose it with other operations.
   */
  public void closeAndForget() {
    close().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#deployVerticle} but the completionHandler will be notified when the deployment is complete.
   * <p>
   * If the deployment is successful the result will contain a String representing the unique deployment ID of the
   * deployment.
   * <p>
   * This deployment ID can subsequently be used to undeploy the verticle.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param name The identifier
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<String> deployVerticle(String name) {
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(completionHandler -> {
        delegate.deployVerticle(name).onComplete(completionHandler);
    });
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(String)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param name The identifier
   * @return the String instance produced by the operation.
   */
  public String deployVerticleAndAwait(String name) {
    return (String) deployVerticle(name).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(String)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#deployVerticle(String)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#deployVerticle(String)} but you don't need to compose it with other operations.
   * @param name The identifier
   */
  public void deployVerticleAndForget(String name) {
    deployVerticle(name).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#deployVerticle} but {@link io.vertx.core.DeploymentOptions} are provided to configure the
   * deployment.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param name the name
   * @param options the deployment options.
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<String> deployVerticle(String name, io.vertx.core.DeploymentOptions options) {
    try {
        Future<String> future = delegate.deployVerticle(name, options);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(String,DeploymentOptions)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param name the name
   * @param options the deployment options.
   * @return the String instance produced by the operation.
   */
  public String deployVerticleAndAwait(String name, io.vertx.core.DeploymentOptions options) {
    return (String) deployVerticle(name, options).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(String,DeploymentOptions)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#deployVerticle(String,DeploymentOptions)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#deployVerticle(String,DeploymentOptions)} but you don't need to compose it with other operations.
   * @param name the name
   * @param options the deployment options.
   */
  public void deployVerticleAndForget(String name, io.vertx.core.DeploymentOptions options) {
    deployVerticle(name, options).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx #undeploy(String)} but the completionHandler will be notified when the undeployment is complete.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param deploymentID the deployment ID
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<Void> undeploy(String deploymentID) {
    try {
        Future<Void> future = delegate.undeploy(deploymentID);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#undeploy(String)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param deploymentID the deployment ID
   * @return the Void instance produced by the operation.
   */
  public Void undeployAndAwait(String deploymentID) {
    return (Void) undeploy(deploymentID).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#undeploy(String)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#undeploy(String)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#undeploy(String)} but you don't need to compose it with other operations.
   * @param deploymentID the deployment ID
   */
  public void undeployAndForget(String deploymentID) {
    undeploy(deploymentID).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * @return Set of deployment IDs
   */
  public Set<String> deploymentIDs() {
    Set<String> ret = delegate.deploymentIDs();
    return ret;
  }

  /**
   * @return true if clustered
   */
  public boolean isClustered() {
    boolean ret = delegate.isClustered();
    return ret;
  }

  // Deprecated methods removed in Vert.x 5

  /**
   * Safely execute some blocking code.
   * <p>
   * Executes the blocking code in the handler <code>blockingCodeHandler</code> using a thread from the worker pool.
   * <p>
   * The returned future will be completed with the result on the original context (i.e. on the original event loop of the caller)
   * or failed when the handler throws an exception.
   * <p>
   * A <code>Future</code> instance is passed into <code>blockingCodeHandler</code>. When the blocking code successfully completes,
   * the handler should call the {@link io.vertx.mutiny.core.Promise#complete} or {@link io.vertx.mutiny.core.Promise#complete} method, or the {@link io.vertx.mutiny.core.Promise#fail}
   * method if it failed.
   * <p>
   * In the <code>blockingCodeHandler</code> the current context remains the original context and therefore any task
   * scheduled in the <code>blockingCodeHandler</code> will be executed on this context and not on the worker thread.
   * <p>
   * The blocking code should block for a reasonable amount of time (i.e no more than a few seconds). Long blocking operations
   * or polling operations (i.e a thread that spin in a loop polling events in a blocking fashion) are precluded.
   * <p>
   * When the blocking operation lasts more than the 10 seconds, a message will be printed on the console by the
   * blocked thread checker.
   * <p>
   * Long blocking operations should use a dedicated thread managed by the application, which can interact with
   * verticles using the event-bus or {@link io.vertx.mutiny.core.Context#runOnContext}
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param blockingCodeHandler handler representing the blocking code to run
   * @param ordered if true then if executeBlocking is called several times on the same context, the executions for that context will be executed serially, not in parallel. if false then they will be no ordering guarantees
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public <T> io.smallrye.mutiny.Uni<T> executeBlocking(io.smallrye.mutiny.Uni<T> blockingCodeHandler, boolean ordered) {
    try {
        // Create a Promise that will be completed by the Uni
        io.vertx.core.Promise<T> promise = io.vertx.core.Promise.promise();

        // Subscribe to the Uni and complete the Promise when it completes
        blockingCodeHandler.subscribe().with(
            item -> promise.complete(item),
            failure -> promise.fail(failure)
        );

        // Execute the Promise's future in a blocking context
        Future<T> future = delegate.executeBlocking(() -> promise.future().result(), ordered);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni,boolean)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param blockingCodeHandler handler representing the blocking code to run
   * @param ordered if true then if executeBlocking is called several times on the same context, the executions for that context will be executed serially, not in parallel. if false then they will be no ordering guarantees
   * @return the T instance produced by the operation.
   */
  public <T> T executeBlockingAndAwait(io.smallrye.mutiny.Uni<T> blockingCodeHandler, boolean ordered) {
    return (T) executeBlocking(blockingCodeHandler, ordered).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni,boolean)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni,boolean)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni,boolean)} but you don't need to compose it with other operations.
   * @param blockingCodeHandler handler representing the blocking code to run
   * @param ordered if true then if executeBlocking is called several times on the same context, the executions for that context will be executed serially, not in parallel. if false then they will be no ordering guarantees
   */
  public <T> void executeBlockingAndForget(io.smallrye.mutiny.Uni<T> blockingCodeHandler, boolean ordered) {
    executeBlocking(blockingCodeHandler, ordered).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#executeBlocking} called with ordered = true.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param blockingCodeHandler
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public <T> io.smallrye.mutiny.Uni<T> executeBlocking(io.smallrye.mutiny.Uni<T> blockingCodeHandler) {
    try {
        // Create a Promise that will be completed by the Uni
        io.vertx.core.Promise<T> promise = io.vertx.core.Promise.promise();

        // Subscribe to the Uni and complete the Promise when it completes
        blockingCodeHandler.subscribe().with(
            item -> promise.complete(item),
            failure -> promise.fail(failure)
        );

        // Execute the Promise's future in a blocking context
        Future<T> future = delegate.executeBlocking(() -> promise.future().result());
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param blockingCodeHandler
   * @return the T instance produced by the operation.
   */
  public <T> T executeBlockingAndAwait(io.smallrye.mutiny.Uni<T> blockingCodeHandler) {
    return (T) executeBlocking(blockingCodeHandler).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#executeBlocking(Uni)} but you don't need to compose it with other operations.
   * @param blockingCodeHandler
   */
  public <T> void executeBlockingAndForget(io.smallrye.mutiny.Uni<T> blockingCodeHandler) {
    executeBlocking(blockingCodeHandler).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * @param name
   * @return
   */
  public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name) {
    io.vertx.mutiny.core.WorkerExecutor ret = io.vertx.mutiny.core.WorkerExecutor.newInstance((io.vertx.core.WorkerExecutor)delegate.createSharedWorkerExecutor(name));
    return ret;
  }

  /**
   * @param name
   * @param poolSize
   * @return
   */
  public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name, int poolSize) {
    io.vertx.mutiny.core.WorkerExecutor ret = io.vertx.mutiny.core.WorkerExecutor.newInstance((io.vertx.core.WorkerExecutor)delegate.createSharedWorkerExecutor(name, poolSize));
    return ret;
  }

  /**
   * @param name
   * @param poolSize
   * @param maxExecuteTime
   * @return
   */
  public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime) {
    io.vertx.mutiny.core.WorkerExecutor ret = io.vertx.mutiny.core.WorkerExecutor.newInstance((io.vertx.core.WorkerExecutor)delegate.createSharedWorkerExecutor(name, poolSize, maxExecuteTime));
    return ret;
  }

  /**
   * @param name the name of the worker executor
   * @param poolSize the size of the pool
   * @param maxExecuteTime the value of max worker execute time
   * @param maxExecuteTimeUnit the value of unit of max worker execute time
   * @return the named worker executor
   */
  public io.vertx.mutiny.core.WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime, java.util.concurrent.TimeUnit maxExecuteTimeUnit) {
    io.vertx.mutiny.core.WorkerExecutor ret = io.vertx.mutiny.core.WorkerExecutor.newInstance((io.vertx.core.WorkerExecutor)delegate.createSharedWorkerExecutor(name, poolSize, maxExecuteTime, maxExecuteTimeUnit));
    return ret;
  }

  /**
   * @return whether the native transport is used
   */
  public boolean isNativeTransportEnabled() {
    if (cached_3 != null) {
      return cached_3;
    }
    boolean ret = delegate.isNativeTransportEnabled();
    cached_3 = ret;
    return ret;
  }

  /**
   * @return the error (if any) that cause the unavailability of native transport when {@link io.vertx.mutiny.core.Vertx#isNativeTransportEnabled} returns <code>false</code>.
   */
  public java.lang.Throwable unavailableNativeTransportCause() {
    if (cached_4 != null) {
      return cached_4;
    }
    java.lang.Throwable ret = delegate.unavailableNativeTransportCause();
    cached_4 = ret;
    return ret;
  }

  /**
   * @param handler the exception handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  private io.vertx.mutiny.core.Vertx __exceptionHandler(Handler<java.lang.Throwable> handler) {
    delegate.exceptionHandler(handler);
    return this;
  }

  /**
   * @param handler the exception handler
   * @return
   */
  public io.vertx.mutiny.core.Vertx exceptionHandler(java.util.function.Consumer<java.lang.Throwable> handler) {
    return __exceptionHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#deployVerticle} but the completionHandler will be notified when the deployment is complete.
   * <p>
   * If the deployment is successful the result will contain a string representing the unique deployment ID of the
   * deployment.
   * <p>
   * This deployment ID can subsequently be used to undeploy the verticle.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param verticle the verticle instance to deploy
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<String> deployVerticle(io.vertx.core.Verticle verticle) {
    try {
        Future<String> future = delegate.deployVerticle(verticle);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param verticle the verticle instance to deploy
   * @return the String instance produced by the operation.
   */
  public String deployVerticleAndAwait(io.vertx.core.Verticle verticle) {
    return (String) deployVerticle(verticle).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle)} but you don't need to compose it with other operations.
   * @param verticle the verticle instance to deploy
   */
  public void deployVerticleAndForget(io.vertx.core.Verticle verticle) {
    deployVerticle(verticle).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#deployVerticle} but {@link io.vertx.core.DeploymentOptions} are provided to configure the
   * deployment.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param verticle the verticle instance to deploy
   * @param options the deployment options.
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<String> deployVerticle(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options) {
    try {
        Future<String> future = delegate.deployVerticle(verticle, options);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle,DeploymentOptions)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param verticle the verticle instance to deploy
   * @param options the deployment options.
   * @return the String instance produced by the operation.
   */
  public String deployVerticleAndAwait(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options) {
    return (String) deployVerticle(verticle, options).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle,DeploymentOptions)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle,DeploymentOptions)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#deployVerticle(Verticle,DeploymentOptions)} but you don't need to compose it with other operations.
   * @param verticle the verticle instance to deploy
   * @param options the deployment options.
   */
  public void deployVerticleAndForget(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options) {
    deployVerticle(verticle, options).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#deployVerticle} but {@link io.vertx.core.Verticle} instance is created by
   * invoking the <code>verticleSupplier</code>.
   * <p>
   * The supplier will be invoked as many times as {@link io.vertx.core.DeploymentOptions}.
   * It must not return the same instance twice.
   * <p>
   * Note that the supplier will be invoked on the caller thread.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param verticleSupplier
   * @param options
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<String> deployVerticle(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options) {
    try {
        Future<String> future = delegate.deployVerticle(verticleSupplier, options);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Supplier,DeploymentOptions)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param verticleSupplier
   * @param options
   * @return the String instance produced by the operation.
   */
  public String deployVerticleAndAwait(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options) {
    return (String) deployVerticle(verticleSupplier, options).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Supplier,DeploymentOptions)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#deployVerticle(Supplier,DeploymentOptions)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#deployVerticle(Supplier,DeploymentOptions)} but you don't need to compose it with other operations.
   * @param verticleSupplier
   * @param options
   */
  public void deployVerticleAndForget(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options) {
    deployVerticle(verticleSupplier, options).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * @param factory the factory to register
   */
  public void registerVerticleFactory(io.vertx.core.spi.VerticleFactory factory) {
    delegate.registerVerticleFactory(factory);
  }

  /**
   * @param factory the factory to unregister
   */
  public void unregisterVerticleFactory(io.vertx.core.spi.VerticleFactory factory) {
    delegate.unregisterVerticleFactory(factory);
  }

  /**
   * @return the set of verticle factories
   */
  public Set<io.vertx.core.spi.VerticleFactory> verticleFactories() {
    Set<io.vertx.core.spi.VerticleFactory> ret = delegate.verticleFactories();
    return ret;
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#executeBlocking} but using a callback.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param blockingCodeHandler
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public <T> io.smallrye.mutiny.Uni<T> executeBlocking(java.util.concurrent.Callable<T> blockingCodeHandler) {
    try {
        Future<T> future = delegate.executeBlocking(blockingCodeHandler);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param blockingCodeHandler
   * @return the T instance produced by the operation.
   */
  public <T> T executeBlockingAndAwait(java.util.concurrent.Callable<T> blockingCodeHandler) {
    return (T) executeBlocking(blockingCodeHandler).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable)} but you don't need to compose it with other operations.
   * @param blockingCodeHandler
   */
  public <T> void executeBlockingAndForget(java.util.concurrent.Callable<T> blockingCodeHandler) {
    executeBlocking(blockingCodeHandler).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.Vertx#executeBlocking} but using a callback.
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param blockingCodeHandler
   * @param ordered
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public <T> io.smallrye.mutiny.Uni<T> executeBlocking(java.util.concurrent.Callable<T> blockingCodeHandler, boolean ordered) {
    try {
        Future<T> future = delegate.executeBlocking(blockingCodeHandler, ordered);
        return io.smallrye.mutiny.vertx.UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable,boolean)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param blockingCodeHandler
   * @param ordered
   * @return the T instance produced by the operation.
   */
  public <T> T executeBlockingAndAwait(java.util.concurrent.Callable<T> blockingCodeHandler, boolean ordered) {
    return (T) executeBlocking(blockingCodeHandler, ordered).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable,boolean)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable,boolean)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.Vertx#executeBlocking(Callable,boolean)} but you don't need to compose it with other operations.
   * @param blockingCodeHandler
   * @param ordered
   */
  public <T> void executeBlockingAndForget(java.util.concurrent.Callable<T> blockingCodeHandler, boolean ordered) {
    executeBlocking(blockingCodeHandler, ordered).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  // nettyEventLoopGroup method has been removed in Vert.x 5

  private io.vertx.mutiny.core.file.FileSystem cached_0;
  private io.vertx.mutiny.core.eventbus.EventBus cached_1;
  private io.vertx.mutiny.core.shareddata.SharedData cached_2;
  private java.lang.Boolean cached_3;
  private java.lang.Throwable cached_4;
  public static  Vertx newInstance(io.vertx.core.Vertx arg) {
    return arg != null ? new Vertx(arg) : null;
  }

}
