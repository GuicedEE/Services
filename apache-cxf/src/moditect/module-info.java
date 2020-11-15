module org.apache.cxf {

	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.jaxrs.json;
	requires transitive java.servlet;
	requires transitive org.apache.commons.io;

	requires transitive com.google.common;

	requires jakarta.annotation;
	requires static java.management;
	requires java.desktop;
	requires jakarta.validation;

	requires java.rmi;

	requires org.slf4j;

	requires jakarta.xml.soap;

	//requires org.apache.xerces;

	requires jakarta.ws.rs;
	requires jakarta.xml.ws;
	requires jakarta.jws;

	requires org.codehaus.stax2;

	exports org.apache.cxf.transports.http.configuration;
	exports org.apache.cxf.transport.http;
	exports org.apache.cxf.configuration.jsse;

	exports org.apache.cxf.annotations;
	exports org.apache.cxf.phase;
	exports org.apache.cxf.interceptor;
	exports org.apache.cxf.message;
	exports org.apache.cxf.jaxws;
	exports org.apache.cxf.service;
	exports org.apache.cxf.binding.soap.saaj;
	exports org.apache.wss4j.common;
	exports org.apache.cxf.endpoint;
	exports org.apache.cxf.service.invoker;
	exports org.apache.cxf.jaxrs.ext;
	exports org.apache.cxf.jaxrs;
	exports org.apache.cxf.jaxrs.utils;
	exports org.apache.cxf;
	exports org.apache.cxf.frontend;
	exports org.apache.cxf.binding;
	exports org.apache.cxf.jaxrs.client;
	exports org.apache.cxf.jaxrs.lifecycle;
	exports org.apache.cxf.transport.servlet;
	exports org.apache.cxf.jaxrs.servlet;
	exports org.apache.cxf.jaxrs.impl;
	exports org.apache.cxf.jaxrs.validation;
	exports org.apache.cxf.binding.soap;
	exports org.apache.cxf.headers;
	exports org.apache.cxf.ws.security.wss4j;
	exports org.apache.wss4j.dom.handler;
	exports org.apache.wss4j.common.ext;

	exports org.apache.cxf.transport.servlet.servicelist;

	exports org.apache.cxf.rs.security.jose.common;
	exports org.apache.cxf.rs.security.jose.jwa;
	exports org.apache.cxf.rs.security.jose.jwk;
	exports org.apache.cxf.rs.security.jose.jws;
	exports org.apache.cxf.rs.security.jose.jwt;

	opens org.apache.cxf.rs.security.oauth2.services;
	opens org.apache.cxf.rs.security.oauth2.provider to com.google.guice;
	opens org.apache.cxf.rs.security.oauth2.filters to com.google.guice;
	opens org.apache.cxf.rs.security.oauth2.common;
	opens org.apache.cxf.rs.security.oauth2.client;

	opens org.apache.cxf.rs.security.oauth.services;
	opens org.apache.cxf.rs.security.oauth.filters to com.google.guice;

//	exports org.apache.cxf.transport.http_undertow;
//	exports org.glassfish.jersey.internal to java.ws.rs;

	//Filters and Providers
	opens org.apache.cxf.jaxrs.provider.json to com.google.guice;
	//opens org.apache.cxf.jaxrs.provider.xmlbeans to com.google.guice;
	opens org.apache.cxf.jaxrs.provider to com.google.guice;
	opens org.apache.cxf.jaxrs.ext.search to com.google.guice;
	opens org.apache.cxf.jaxrs.validation to com.google.guice;

	opens org.apache.cxf.jaxrs.provider.aegis to com.google.guice;

	uses jakarta.ws.rs.ext.MessageBodyWriter;
	uses jakarta.ws.rs.ext.MessageBodyReader;
	uses jakarta.ws.rs.ext.ExceptionMapper;
	uses jakarta.ws.rs.ext.ContextResolver;
	uses jakarta.ws.rs.ext.ReaderInterceptor;
	uses jakarta.ws.rs.ext.WriterInterceptor;
	uses jakarta.ws.rs.ext.ParamConverterProvider;
	uses jakarta.ws.rs.container.ContainerRequestFilter;
	uses jakarta.ws.rs.container.ContainerResponseFilter;
	uses jakarta.ws.rs.container.DynamicFeature;
	uses org.apache.cxf.jaxrs.ext.ContextResolver;

//	opens org.glassfish.jersey.server.wadl.internal  to java.ws.rs;

	opens org.apache.cxf.ws.addressing to jakarta.xml.bind;

	provides jakarta.xml.ws.spi.Provider with org.apache.cxf.jaxws22.spi.ProviderImpl;

	//provides org.apache.cxf.jaxrs.openapi.JaxRs2Extension with org.apache.cxf.jaxrs.openapi.JaxRs2Extension;
	provides jakarta.ws.rs.sse.SseEventSource.Builder with org.apache.cxf.jaxrs.sse.client.SseEventSourceBuilderImpl;

	uses org.apache.cxf.jaxrs.ext.JAXRSServerFactoryCustomizationExtension;
	provides org.apache.cxf.jaxrs.ext.JAXRSServerFactoryCustomizationExtension with org.apache.cxf.jaxrs.sse.ext.SseTransportCustomizationExtension;

	provides com.sun.xml.ws.spi.db.BindingContextFactory with com.sun.xml.ws.db.glassfish.JAXBRIContextFactory;
	provides jakarta.ws.rs.client.ClientBuilder with org.apache.cxf.jaxrs.client.spec.ClientBuilderImpl;

	provides jakarta.ws.rs.ext.RuntimeDelegate with org.apache.cxf.jaxrs.impl.RuntimeDelegateImpl;

	provides jakarta.xml.soap.MessageFactory with com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl,
			                                       com.sun.xml.messaging.saaj.soap.ver1_2.SOAPMessageFactory1_2Impl;
	provides jakarta.xml.soap.SAAJMetaFactory with com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl;
	provides jakarta.xml.soap.SOAPConnectionFactory with com.sun.xml.messaging.saaj.client.p2p.HttpSOAPConnectionFactory;
	provides jakarta.xml.soap.SOAPFactory with com.sun.xml.messaging.saaj.soap.ver1_1.SOAPFactory1_1Impl,
			                                    com.sun.xml.messaging.saaj.soap.ver1_2.SOAPFactory1_2Impl;

	//uses org.eclipse.jetty.http.HttpFieldPreEncoder;
	//provides org.eclipse.jetty.http.HttpFieldPreEncoder with org.eclipse.jetty.http.Http1FieldPreEncoder;

	uses org.opensaml.core.config.Initializer;
	provides org.opensaml.core.config.Initializer with org.opensaml.saml.config.XMLObjectProviderInitializer,
			org.opensaml.saml.config.SAMLConfigurationInitializer,
			org.opensaml.security.config.impl.ClientTLSValidationConfiguratonInitializer,
			org.opensaml.xmlsec.config.JavaCryptoValidationInitializer,
			org.opensaml.xmlsec.config.XMLObjectProviderInitializer,
			org.opensaml.xmlsec.config.ApacheXMLSecurityInitializer,
			org.opensaml.xmlsec.config.GlobalSecurityConfigurationInitializer,
			org.opensaml.xacml.config.impl.XMLObjectProviderInitializer,
			org.opensaml.xacml.profile.saml.config.impl.XMLObjectProviderInitializer,
			org.opensaml.xmlsec.config.GlobalAlgorithmRegistryInitializer,
			org.opensaml.xmlsec.config.DecryptionParserPoolInitializer,
			org.opensaml.core.xml.config.XMLObjectProviderInitializer,
			org.opensaml.core.xml.config.GlobalParserPoolInitializer,
			org.opensaml.core.metrics.impl.MetricRegistryInitializer;

	uses org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
	provides org.opensaml.xmlsec.algorithm.AlgorithmDescriptor with org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES128CBC,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES128GCM,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES192CBC,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES192GCM,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES256CBC,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES256GCM,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionDESede,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.DigestMD5,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.DigestRIPEMD160,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.DigestSHA1,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.DigestSHA224,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.DigestSHA256,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.DigestSHA384,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.DigestSHA512,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.HMACMD5,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.HMACRIPEMD160,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.HMACSHA1,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.HMACSHA224,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.HMACSHA256,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.HMACSHA384,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.HMACSHA512,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.KeyTransportRSA15,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.KeyTransportRSAOAEP,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.KeyTransportRSAOAEPMGF1P,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureDSASHA1,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureDSASHA256,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA1,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA224,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA256,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA384,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA512,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureRSAMD5,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureRSARIPEMD160,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA1,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA224,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA256,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA384,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA512,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapAES128,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapAES192,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapAES256,
			                                                           org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapDESede;
	uses org.opensaml.xmlsec.signature.support.SignatureValidationProvider;
	provides org.opensaml.xmlsec.signature.support.SignatureValidationProvider with org.opensaml.xmlsec.signature.support.impl.provider.ApacheSantuarioSignatureValidationProviderImpl;

	uses org.opensaml.xmlsec.signature.support.SignerProvider;
	provides org.opensaml.xmlsec.signature.support.SignerProvider with org.opensaml.xmlsec.signature.support.impl.provider.ApacheSantuarioSignerProviderImpl;

}

