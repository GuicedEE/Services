module com.azure.identity {

    exports com.azure.identity;
    exports com.microsoft.aad.adal4j;

    requires jakarta.mail;

    requires java.naming;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.commons.lang3;

    uses com.azure.core.http.HttpClientProvider;
    provides com.azure.core.http.HttpClientProvider with com.azure.core.http.netty.NettyAsyncHttpClientProvider;
  //  uses reactor.blockhound.integration.BlockHoundIntegration;
 //   provides reactor.blockhound.integration.BlockHoundIntegration with io.netty.util.internal.Hidden.NettyBlockHoundIntegration;

    opens com.azure.identity.implementation to com.fasterxml.jackson.databind;
}