module undertow.core {

    requires org.jboss.logging;

    requires java.security.jgss;

    requires static alpn.api;
    requires static io.undertow.parser.generator;
    requires jdk.unsupported;
    requires java.naming;
    requires static java.sql;
    requires java.compiler;

    exports io.undertow;
    exports io.undertow.util;

    exports org.xnio;
    exports org.xnio.nio;
    exports org.xnio.management;
    exports org.xnio.channels;
    //exports org.xnio.fc;
    exports org.xnio.conduits;
    exports org.xnio.ssl;
    exports org.xnio.http;

    exports org.wildfly.common.context;

    requires java.management;
    requires java.security.sasl;

    opens org.xnio._private to org.jboss.logging;

    exports io.undertow.security.api;
    exports io.undertow.security.handlers;
    exports io.undertow.security.idm;
    exports io.undertow.security.impl;
    exports io.undertow.attribute;
    exports io.undertow.channels;
    exports io.undertow.client;
    exports io.undertow.client.ajp;
    exports io.undertow.client.http;
    exports io.undertow.client.http2;
    exports io.undertow.conduits;
    exports io.undertow.connector;
    exports io.undertow.io;
    exports io.undertow.predicate;
    exports io.undertow.protocols.ajp;

    exports io.undertow.protocols.alpn;
    exports io.undertow.protocols.http2;
    exports io.undertow.protocols.ssl;

    exports io.undertow.server;
    exports io.undertow.server.handlers;
    exports io.undertow.server.handlers.resource;
    exports io.undertow.server.handlers.proxy;
    exports io.undertow.server.handlers.proxy.mod_cluster;
    exports io.undertow.server.handlers.cache;
    exports io.undertow.server.handlers.accesslog;
    exports io.undertow.server.handlers.builder;
    exports io.undertow.server.handlers.encoding;
    exports io.undertow.server.handlers.error;
    exports io.undertow.server.handlers.form;
    exports io.undertow.server.handlers.sse;
    exports io.undertow.server.protocol;
    exports io.undertow.server.protocol.proxy;
    exports io.undertow.server.protocol.ajp;
    exports io.undertow.server.protocol.framed;
    exports io.undertow.server.protocol.http;
    exports io.undertow.server.protocol.http2;
    exports io.undertow.server.session;

    exports io.undertow.websockets.spi;
    exports io.undertow.websockets.client;
    exports io.undertow.websockets.core;
    exports io.undertow.websockets.core.function;
    exports io.undertow.websockets.core.protocol;
    exports io.undertow.websockets;
    exports io.undertow.websockets.core.protocol.version08;
    exports io.undertow.websockets.core.protocol.version13;
    exports io.undertow.websockets.core.protocol.version07;
    exports io.undertow.websockets.extensions;


    uses io.undertow.attribute.ExchangeAttributeBuilder;
    uses io.undertow.predicate.PredicateBuilder;
    uses io.undertow.server.handlers.builder.HandlerBuilder;
    uses io.undertow.client.ClientProvider;
    uses io.undertow.protocols.alpn.ALPNProvider;

    uses org.xnio.XnioProvider;

    uses io.undertow.protocols.alpn.ALPNEngineManager;

    opens io.undertow to org.jboss.logging;

    provides io.undertow.attribute.ExchangeAttributeBuilder with io.undertow.attribute.RelativePathAttribute.Builder,
            io.undertow.attribute.RemoteIPAttribute.Builder,
            io.undertow.attribute.LocalIPAttribute.Builder,
            io.undertow.attribute.RequestProtocolAttribute.Builder,
            io.undertow.attribute.LocalPortAttribute.Builder,
            io.undertow.attribute.IdentUsernameAttribute.Builder,
            io.undertow.attribute.RequestMethodAttribute.Builder,
            io.undertow.attribute.QueryStringAttribute.Builder,
            io.undertow.attribute.RequestLineAttribute.Builder,
            io.undertow.attribute.BytesSentAttribute.Builder,
            io.undertow.attribute.DateTimeAttribute.Builder,
            io.undertow.attribute.RemoteUserAttribute.Builder,
            io.undertow.attribute.RequestURLAttribute.Builder,
            io.undertow.attribute.ThreadNameAttribute.Builder,
            io.undertow.attribute.LocalServerNameAttribute.Builder,
            io.undertow.attribute.RequestHeaderAttribute.Builder,
            io.undertow.attribute.ResponseHeaderAttribute.Builder,
            io.undertow.attribute.CookieAttribute.Builder,
            io.undertow.attribute.RequestCookieAttribute.Builder,
            io.undertow.attribute.ResponseCookieAttribute.Builder,
            io.undertow.attribute.ResponseCodeAttribute.Builder,
            io.undertow.attribute.PredicateContextAttribute.Builder,
            io.undertow.attribute.QueryParameterAttribute.Builder,
            io.undertow.attribute.SslClientCertAttribute.Builder,
            io.undertow.attribute.SslCipherAttribute.Builder,
            io.undertow.attribute.SslSessionIdAttribute.Builder,
            io.undertow.attribute.ResponseTimeAttribute.Builder,
            io.undertow.attribute.PathParameterAttribute.Builder,
            io.undertow.attribute.TransportProtocolAttribute.Builder,
            io.undertow.attribute.RequestSchemeAttribute.Builder,
            io.undertow.attribute.HostAndPortAttribute.Builder,
            io.undertow.attribute.AuthenticationTypeExchangeAttribute.Builder,
            io.undertow.attribute.SecureExchangeAttribute.Builder,
            io.undertow.attribute.RemoteHostAttribute.Builder,
            io.undertow.attribute.RequestPathAttribute.Builder,
            io.undertow.attribute.ResolvedPathAttribute.Builder,
            io.undertow.attribute.NullAttribute.Builder,
            io.undertow.attribute.StoredResponse.Builder,
            io.undertow.attribute.ResponseReasonPhraseAttribute.Builder,
            io.undertow.attribute.RemoteObfuscatedIPAttribute.Builder;

    provides io.undertow.client.ClientProvider with io.undertow.client.http.HttpClientProvider,
            io.undertow.client.ajp.AjpClientProvider,
            io.undertow.client.http2.Http2ClientProvider,
            io.undertow.client.http2.Http2ClearClientProvider,
            io.undertow.client.http2.Http2PriorKnowledgeClientProvider;

    provides io.undertow.predicate.PredicateBuilder with io.undertow.predicate.PathMatchPredicate.Builder,
            io.undertow.predicate.PathPrefixPredicate.Builder,
            io.undertow.predicate.ContainsPredicate.Builder,
            io.undertow.predicate.ExistsPredicate.Builder,
            io.undertow.predicate.RegularExpressionPredicate.Builder,
            io.undertow.predicate.PathSuffixPredicate.Builder,
            io.undertow.predicate.EqualsPredicate.Builder,
            io.undertow.predicate.PathTemplatePredicate.Builder,
            io.undertow.predicate.MethodPredicate.Builder,
            io.undertow.predicate.AuthenticationRequiredPredicate.Builder,
            io.undertow.predicate.MaxContentSizePredicate.Builder,
            io.undertow.predicate.MinContentSizePredicate.Builder,
            io.undertow.predicate.SecurePredicate.Builder,
            io.undertow.predicate.IdempotentPredicate.Builder,
            io.undertow.predicate.RequestLargerThanPredicate.Builder,
            io.undertow.predicate.RequestSmallerThanPredicate.Builder;

    provides io.undertow.protocols.alpn.ALPNEngineManager with io.undertow.protocols.ssl.SNIAlpnEngineManager,
            io.undertow.protocols.alpn.DefaultAlpnEngineManager;

    provides io.undertow.protocols.alpn.ALPNProvider with
		    io.undertow.protocols.alpn.ModularJdkAlpnProvider;

    provides io.undertow.server.handlers.builder.HandlerBuilder with io.undertow.server.handlers.builder.RewriteHandlerBuilder,
            io.undertow.server.handlers.SetAttributeHandler.Builder,
            io.undertow.server.handlers.SetAttributeHandler.ClearBuilder,
            io.undertow.server.handlers.builder.ResponseCodeHandlerBuilder,
            io.undertow.server.handlers.DisableCacheHandler.Builder,
            io.undertow.server.handlers.ProxyPeerAddressHandler.Builder,
            io.undertow.server.handlers.proxy.ProxyHandlerBuilder,
            io.undertow.server.handlers.RedirectHandler.Builder,
            io.undertow.server.handlers.accesslog.AccessLogHandler.Builder,
            io.undertow.server.handlers.AllowedMethodsHandler.Builder,
            io.undertow.server.handlers.BlockingHandler.Builder,
            io.undertow.server.handlers.CanonicalPathHandler.Builder,
            io.undertow.server.handlers.DisallowedMethodsHandler.Builder,
            io.undertow.server.handlers.error.FileErrorPageHandler.Builder,
            io.undertow.server.handlers.HttpTraceHandler.Builder,
            io.undertow.server.JvmRouteHandler.Builder,
            io.undertow.server.handlers.PeerNameResolvingHandler.Builder,
            io.undertow.server.handlers.RequestDumpingHandler.Builder,
            io.undertow.server.handlers.RequestLimitingHandler.Builder,
            io.undertow.server.handlers.resource.ResourceHandler.Builder,
            io.undertow.server.handlers.SSLHeaderHandler.Builder,
            io.undertow.server.handlers.ResponseRateLimitingHandler.Builder,
            io.undertow.server.handlers.URLDecodingHandler.Builder,
            io.undertow.server.handlers.PathSeparatorHandler.Builder,
            io.undertow.server.handlers.IPAddressAccessControlHandler.Builder,
            io.undertow.server.handlers.ByteRangeHandler.Builder,
            io.undertow.server.handlers.encoding.EncodingHandler.Builder,
            io.undertow.server.handlers.encoding.RequestEncodingHandler.Builder,
            io.undertow.server.handlers.LearningPushHandler.Builder,
            io.undertow.server.handlers.SetHeaderHandler.Builder,
            io.undertow.predicate.PredicatesHandler.DoneHandlerBuilder,
            io.undertow.predicate.PredicatesHandler.RestartHandlerBuilder,
            io.undertow.server.handlers.RequestBufferingHandler.Builder,
            io.undertow.server.handlers.StuckThreadDetectionHandler.Builder,
            io.undertow.server.handlers.AccessControlListHandler.Builder,
            io.undertow.server.handlers.JDBCLogHandler.Builder,
            io.undertow.server.handlers.LocalNameResolvingHandler.Builder,
            io.undertow.server.handlers.StoredResponseHandler.Builder,
            io.undertow.server.handlers.SecureCookieHandler.Builder,
            io.undertow.server.handlers.ForwardedHandler.Builder,
            io.undertow.server.handlers.HttpContinueAcceptingHandler.Builder,
            io.undertow.server.handlers.form.EagerFormParsingHandler.Builder,
            io.undertow.server.handlers.SameSiteCookieHandler.Builder,
            io.undertow.server.handlers.SetErrorHandler.Builder;

    provides org.xnio.XnioProvider with org.xnio.nio.NioXnioProvider;

}
