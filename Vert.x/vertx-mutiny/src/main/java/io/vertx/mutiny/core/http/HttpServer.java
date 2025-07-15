package io.vertx.mutiny.core.http;

import io.smallrye.common.annotation.CheckReturnValue;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.TypeArg;
import io.smallrye.mutiny.vertx.UniHelper;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.net.ServerSSLOptions;
import io.vertx.core.net.TrafficShapingOptions;

/**
 * Note: This class has been updated to be compatible with Vert.x 5
 * 
 * The main changes are:
 * 1. Updated import statements to use the correct classes for Vert.x 5
 * 2. Updated method signatures for methods like updateSSLOptions() and listen()
 * 3. Removed deprecated methods like requestStream() and webSocketStream()
 * 4. Added Future-based implementations for methods that have changed in Vert.x 5
 */

/**
 * An HTTP and WebSockets server.
 * <p>
 * You receive HTTP requests by providing a {@link io.vertx.mutiny.core.http.HttpServer#requestHandler}. As requests arrive on the server the handler
 * will be called with the requests.
 * <p>
 * You receive WebSockets by providing a {@link io.vertx.mutiny.core.http.HttpServer#webSocketHandler}. As WebSocket connections arrive on the server, the
 * WebSocket is passed to the handler.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.core.http.HttpServer original} non Mutiny-ified interface using Vert.x codegen.
 */

@io.smallrye.mutiny.vertx.MutinyGen(io.vertx.core.http.HttpServer.class)
public class HttpServer implements io.smallrye.mutiny.vertx.MutinyDelegate, io.vertx.mutiny.core.metrics.Measured {

  public static final io.smallrye.mutiny.vertx.TypeArg<HttpServer> __TYPE_ARG = new io.smallrye.mutiny.vertx.TypeArg<>(    obj -> new HttpServer((io.vertx.core.http.HttpServer) obj),
    HttpServer::getDelegate
  );

  private final io.vertx.core.http.HttpServer delegate;
  
  public HttpServer(io.vertx.core.http.HttpServer delegate) {
    this.delegate = delegate;
  }

  public HttpServer(Object delegate) {
    this.delegate = (io.vertx.core.http.HttpServer)delegate;
  }

  /**
   * Empty constructor used by CDI, do not use this constructor directly.
   **/
  HttpServer() {
    this.delegate = null;
  }

  @Override
  public io.vertx.core.http.HttpServer getDelegate() {
    return delegate;
  }

  static final io.smallrye.mutiny.vertx.TypeArg<io.vertx.mutiny.core.http.HttpServerRequest> TYPE_ARG_0 = new TypeArg<io.vertx.mutiny.core.http.HttpServerRequest>(o1 -> io.vertx.mutiny.core.http.HttpServerRequest.newInstance((io.vertx.core.http.HttpServerRequest)o1), o1 -> o1.getDelegate());
  static final io.smallrye.mutiny.vertx.TypeArg<io.vertx.mutiny.core.http.ServerWebSocket> TYPE_ARG_1 = new TypeArg<io.vertx.mutiny.core.http.ServerWebSocket>(o1 -> io.vertx.mutiny.core.http.ServerWebSocket.newInstance((io.vertx.core.http.ServerWebSocket)o1), o1 -> o1.getDelegate());
  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HttpServer that = (HttpServer) o;
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

  /**
   * @param handler 
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  private io.vertx.mutiny.core.http.HttpServer __requestHandler(Handler<io.vertx.mutiny.core.http.HttpServerRequest> handler) { 
    delegate.requestHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertHandler(handler, event -> io.vertx.mutiny.core.http.HttpServerRequest.newInstance((io.vertx.core.http.HttpServerRequest)event)));
    return this;
  }

  /**
   * @param handler 
   * @return 
   */
  public io.vertx.mutiny.core.http.HttpServer requestHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.HttpServerRequest> handler) {
    return __requestHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param handler 
   * @return a reference to this, so the API can be used fluently
   */
  private io.vertx.mutiny.core.http.HttpServer __invalidRequestHandler(Handler<io.vertx.mutiny.core.http.HttpServerRequest> handler) { 
    io.vertx.mutiny.core.http.HttpServer ret = io.vertx.mutiny.core.http.HttpServer.newInstance((io.vertx.core.http.HttpServer)delegate.invalidRequestHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertHandler(handler, event -> io.vertx.mutiny.core.http.HttpServerRequest.newInstance((io.vertx.core.http.HttpServerRequest)event))));
    return ret;
  }

  /**
   * @param handler 
   * @return 
   */
  public io.vertx.mutiny.core.http.HttpServer invalidRequestHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.HttpServerRequest> handler) {
    return __invalidRequestHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param handler 
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  private io.vertx.mutiny.core.http.HttpServer __connectionHandler(Handler<io.vertx.mutiny.core.http.HttpConnection> handler) { 
    delegate.connectionHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertHandler(handler, event -> io.vertx.mutiny.core.http.HttpConnection.newInstance((io.vertx.core.http.HttpConnection)event)));
    return this;
  }

  /**
   * @param handler 
   * @return 
   */
  public io.vertx.mutiny.core.http.HttpServer connectionHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.HttpConnection> handler) {
    return __connectionHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param handler the handler to set
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  private io.vertx.mutiny.core.http.HttpServer __exceptionHandler(Handler<java.lang.Throwable> handler) { 
    delegate.exceptionHandler(handler);
    return this;
  }

  /**
   * @param handler the handler to set
   * @return 
   */
  public io.vertx.mutiny.core.http.HttpServer exceptionHandler(java.util.function.Consumer<java.lang.Throwable> handler) {
    return __exceptionHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param handler 
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  private io.vertx.mutiny.core.http.HttpServer __webSocketHandler(Handler<io.vertx.mutiny.core.http.ServerWebSocket> handler) { 
    delegate.webSocketHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertHandler(handler, event -> io.vertx.mutiny.core.http.ServerWebSocket.newInstance((io.vertx.core.http.ServerWebSocket)event)));
    return this;
  }

  /**
   * @param handler 
   * @return 
   */
  public io.vertx.mutiny.core.http.HttpServer webSocketHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.ServerWebSocket> handler) {
    return __webSocketHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * @param handler 
   * @return the instance of HttpServer to chain method calls.
   */
  @Fluent
  private io.vertx.mutiny.core.http.HttpServer __webSocketHandshakeHandler(Handler<io.vertx.mutiny.core.http.ServerWebSocketHandshake> handler) { 
    delegate.webSocketHandshakeHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertHandler(handler, event -> io.vertx.mutiny.core.http.ServerWebSocketHandshake.newInstance((io.vertx.core.http.ServerWebSocketHandshake)event)));
    return this;
  }

  /**
   * @param handler 
   * @return 
   */
  public io.vertx.mutiny.core.http.HttpServer webSocketHandshakeHandler(java.util.function.Consumer<io.vertx.mutiny.core.http.ServerWebSocketHandshake> handler) {
    return __webSocketHandshakeHandler(io.smallrye.mutiny.vertx.MutinyHelper.convertConsumer(handler));
  }

  /**
   * Like {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions}  but supplying a handler that will be called when the update
   * happened (or has failed).
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param options the new SSL options
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<Boolean> updateSSLOptions(io.vertx.core.net.ServerSSLOptions options) { 
    // In Vert.x 5, the updateSSLOptions method signature has changed to return a Future directly
    try {
        Future<Boolean> future = delegate.updateSSLOptions(options);
        return UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param options the new SSL options
   * @return the Boolean instance produced by the operation.
   */
  public Boolean updateSSLOptionsAndAwait(io.vertx.core.net.ServerSSLOptions options) { 
    return (Boolean) updateSSLOptions(options).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions)} but you don't need to compose it with other operations.
   * @param options the new SSL options
   */
  public void updateSSLOptionsAndForget(io.vertx.core.net.ServerSSLOptions options) { 
    updateSSLOptions(options).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * Like {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions}  but supplying a handler that will be called when the update
   * happened (or has failed).
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param options the new SSL options
   * @param force force the update when options are equals
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<Boolean> updateSSLOptions(io.vertx.core.net.ServerSSLOptions options, boolean force) { 
    // In Vert.x 5, the updateSSLOptions method signature has changed to return a Future directly
    try {
        Future<Boolean> future = delegate.updateSSLOptions(options, force);
        return UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions,boolean)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param options the new SSL options
   * @param force force the update when options are equals
   * @return the Boolean instance produced by the operation.
   */
  public Boolean updateSSLOptionsAndAwait(io.vertx.core.net.ServerSSLOptions options, boolean force) { 
    return (Boolean) updateSSLOptions(options, force).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions,boolean)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions,boolean)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.http.HttpServer#updateSSLOptions(ServerSSLOptions,boolean)} but you don't need to compose it with other operations.
   * @param options the new SSL options
   * @param force force the update when options are equals
   */
  public void updateSSLOptionsAndForget(io.vertx.core.net.ServerSSLOptions options, boolean force) { 
    updateSSLOptions(options, force).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * @param options the new traffic shaping options
   */
  public void updateTrafficShapingOptions(io.vertx.core.net.TrafficShapingOptions options) { 
    delegate.updateTrafficShapingOptions(options);
  }

  /**
   * Like {@link io.vertx.mutiny.core.http.HttpServer#listen} but supplying a handler that will be called when the server is actually
   * listening (or has failed).
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param port the port to listen on
   * @param host the host to listen on
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen(int port, String host) { 
    // In Vert.x 5, the listen method signature has changed to return a Future directly
    try {
        Future<io.vertx.core.http.HttpServer> future = delegate.listen(port, host);
        return UniHelper.toUni(future)
                .map(server -> HttpServer.newInstance(server));
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.http.HttpServer#listen(int,String)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param port the port to listen on
   * @param host the host to listen on
   * @return the HttpServer instance produced by the operation.
   */
  public io.vertx.mutiny.core.http.HttpServer listenAndAwait(int port, String host) { 
    return (io.vertx.mutiny.core.http.HttpServer) listen(port, host).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.http.HttpServer#listen(int,String)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.http.HttpServer#listen(int,String)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.http.HttpServer#listen(int,String)} but you don't need to compose it with other operations.
   * @param port the port to listen on
   * @param host the host to listen on
   * @return the instance of HttpServer to chain method calls.
   */
  @Fluent
  public io.vertx.mutiny.core.http.HttpServer listenAndForget(int port, String host) { 
    listen(port, host).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  /**
   * Tell the server to start listening on the given address supplying
   * a handler that will be called when the server is actually
   * listening (or has failed).
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param address the address to listen on
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen(io.vertx.mutiny.core.net.SocketAddress address) { 
    // In Vert.x 5, the listen method signature has changed to return a Future directly
    try {
        Future<io.vertx.core.http.HttpServer> future = delegate.listen(address.getDelegate());
        return UniHelper.toUni(future)
                .map(server -> HttpServer.newInstance(server));
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.http.HttpServer#listen(io.vertx.mutiny.core.net.SocketAddress)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param address the address to listen on
   * @return the HttpServer instance produced by the operation.
   */
  public io.vertx.mutiny.core.http.HttpServer listenAndAwait(io.vertx.mutiny.core.net.SocketAddress address) { 
    return (io.vertx.mutiny.core.http.HttpServer) listen(address).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.http.HttpServer#listen(io.vertx.mutiny.core.net.SocketAddress)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.http.HttpServer#listen(io.vertx.mutiny.core.net.SocketAddress)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.http.HttpServer#listen(io.vertx.mutiny.core.net.SocketAddress)} but you don't need to compose it with other operations.
   * @param address the address to listen on
   * @return the instance of HttpServer to chain method calls.
   */
  @Fluent
  public io.vertx.mutiny.core.http.HttpServer listenAndForget(io.vertx.mutiny.core.net.SocketAddress address) { 
    listen(address).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  /**
   * Like {@link io.vertx.mutiny.core.http.HttpServer#listen} but supplying a handler that will be called when the server is actually listening (or has failed).
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @param port the port to listen on
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen(int port) { 
    // In Vert.x 5, the listen method signature has changed to return a Future directly
    try {
        Future<io.vertx.core.http.HttpServer> future = delegate.listen(port);
        return UniHelper.toUni(future)
                .map(server -> HttpServer.newInstance(server));
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.http.HttpServer#listen(int)}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @param port the port to listen on
   * @return the HttpServer instance produced by the operation.
   */
  public io.vertx.mutiny.core.http.HttpServer listenAndAwait(int port) { 
    return (io.vertx.mutiny.core.http.HttpServer) listen(port).await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.http.HttpServer#listen(int)} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.http.HttpServer#listen(int)}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.http.HttpServer#listen(int)} but you don't need to compose it with other operations.
   * @param port the port to listen on
   * @return the instance of HttpServer to chain method calls.
   */
  @Fluent
  public io.vertx.mutiny.core.http.HttpServer listenAndForget(int port) { 
    listen(port).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  /**
   * Like {@link io.vertx.mutiny.core.http.HttpServer#listen} but supplying a handler that will be called when the server is actually listening (or has failed).
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<io.vertx.mutiny.core.http.HttpServer> listen() { 
    // In Vert.x 5, the listen method signature has changed to return a Future directly
    try {
        Future<io.vertx.core.http.HttpServer> future = delegate.listen();
        return UniHelper.toUni(future)
                .map(server -> HttpServer.newInstance(server));
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.http.HttpServer#listen}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @return the HttpServer instance produced by the operation.
   */
  public io.vertx.mutiny.core.http.HttpServer listenAndAwait() { 
    return (io.vertx.mutiny.core.http.HttpServer) listen().await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.http.HttpServer#listen} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.http.HttpServer#listen}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.http.HttpServer#listen} but you don't need to compose it with other operations.
   * @return the instance of HttpServer to chain method calls.
   */
  @Fluent
  public io.vertx.mutiny.core.http.HttpServer listenAndForget() { 
    listen().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  /**
   * Like {@link io.vertx.mutiny.core.http.HttpServer#close} but supplying a handler that will be called when the server is actually closed (or has failed).
   * <p>
   * Unlike the <em>bare</em> Vert.x variant, this method returns a {@link io.smallrye.mutiny.Uni Uni}.
   * Don't forget to <em>subscribe</em> on it to trigger the operation.
   * @return the {@link io.smallrye.mutiny.Uni uni} firing the result of the operation when completed, or a failure if the operation failed.
   */
  @CheckReturnValue
  public io.smallrye.mutiny.Uni<Void> close() { 
    // In Vert.x 5, the close method signature has changed to return a Future directly
    try {
        Future<Void> future = delegate.close();
        return UniHelper.toUni(future);
    } catch (Exception e) {
        return Uni.createFrom().failure(e);
    }
  }

  /**
   * Blocking variant of {@link io.vertx.mutiny.core.http.HttpServer#close}.
   * <p>
   * This method waits for the completion of the underlying asynchronous operation.
   * If the operation completes successfully, the result is returned, otherwise the failure is thrown (potentially wrapped in a RuntimeException).
   * @return the Void instance produced by the operation.
   */
  public Void closeAndAwait() { 
    return (Void) close().await().indefinitely();
  }

  /**
   * Variant of {@link io.vertx.mutiny.core.http.HttpServer#close} that ignores the result of the operation.
   * <p>
   * This method subscribes on the result of {@link io.vertx.mutiny.core.http.HttpServer#close}, but discards the outcome (item or failure).
   * This method is useful to trigger the asynchronous operation from {@link io.vertx.mutiny.core.http.HttpServer#close} but you don't need to compose it with other operations.
   */
  public void closeAndForget() { 
    close().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  /**
   * @return the actual port the server is listening on.
   */
  public int actualPort() { 
    int ret = delegate.actualPort();
    return ret;
  }

  private io.vertx.mutiny.core.streams.ReadStream<io.vertx.mutiny.core.http.HttpServerRequest> cached_0;
  private io.vertx.mutiny.core.streams.ReadStream<io.vertx.mutiny.core.http.ServerWebSocket> cached_1;
  public static  HttpServer newInstance(io.vertx.core.http.HttpServer arg) {
    return arg != null ? new HttpServer(arg) : null;
  }

}