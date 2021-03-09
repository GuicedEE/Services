module com.azure.identity {

    exports com.azure.identity;
    exports com.microsoft.aad.adal4j;

    exports com.nimbusds.openid.connect.sdk;
    exports com.nimbusds.jwt;
    exports com.nimbusds.oauth2.sdk;
    exports com.microsoft.aad.msal4j;

    requires jakarta.mail;
    requires jakarta.servlet;
    requires jakarta.xml.bind;

    requires com.google.gson;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    requires org.json;

    requires transitive java.naming;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.commons.lang3;

    uses com.azure.core.http.HttpClientProvider;
    provides com.azure.core.http.HttpClientProvider with com.azure.core.http.netty.NettyAsyncHttpClientProvider;
  //  uses reactor.blockhound.integration.BlockHoundIntegration;
 //   provides reactor.blockhound.integration.BlockHoundIntegration with io.netty.util.internal.Hidden.NettyBlockHoundIntegration;

    opens com.azure.identity.implementation to com.fasterxml.jackson.databind;

    opens com.microsoft.aad.msal4j to  com.fasterxml.jackson.databind;
}