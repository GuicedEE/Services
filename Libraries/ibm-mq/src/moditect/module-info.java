module com.ibm.mq.jakarta {
	
	requires org.json;
	requires static org.bouncycastle.provider;
	requires static org.bouncycastle.pkix;
	
	
	exports com.ibm.jakarta.jms;
	exports com.ibm.mq;
	exports com.ibm.mq.constants;
	exports com.ibm.mq.ese.config;
	exports com.ibm.mq.ese.conv;
	exports com.ibm.mq.ese.core;
	exports com.ibm.mq.ese.intercept;
	exports com.ibm.mq.ese.jmqi;
	exports com.ibm.mq.ese.nls;
	exports com.ibm.mq.ese.pki;
	exports com.ibm.mq.ese.prot;
	exports com.ibm.mq.ese.service;
	exports com.ibm.mq.ese.util;
	exports com.ibm.mq.exits;
	exports com.ibm.mq.headers;
	exports com.ibm.mq.internal;
	exports com.ibm.mq.jakarta.jms;
	exports com.ibm.mq.jakarta.jms.admin;
	exports com.ibm.mq.jakarta.jms.resources;
	exports com.ibm.mq.jmqi;
	exports com.ibm.mq.jmqi.handles;
	exports com.ibm.mq.jmqi.internal;
	exports com.ibm.mq.jmqi.internal.charset;
	exports com.ibm.mq.jmqi.local;
	exports com.ibm.mq.jmqi.local.internal;
	exports com.ibm.mq.jmqi.local.internal.adapters;
	exports com.ibm.mq.jmqi.local.internal.base;
	exports com.ibm.mq.jmqi.monitoring;
	exports com.ibm.mq.jmqi.remote.api;
	exports com.ibm.mq.jmqi.remote.exit;
	exports com.ibm.mq.jmqi.remote.impl;
	exports com.ibm.mq.jmqi.remote.rfp;
	exports com.ibm.mq.jmqi.remote.rfp.spi;
	exports com.ibm.mq.jmqi.remote.util;
	exports com.ibm.mq.jmqi.samples;
	exports com.ibm.mq.jmqi.system;
	exports com.ibm.mq.jmqi.system.internal;
	exports com.ibm.mq.jmqi.system.zrfp;
	exports com.ibm.mq.pcf;
	exports com.ibm.msg.client.commonservices;
	exports com.ibm.msg.client.commonservices.commandmanager;
	exports com.ibm.msg.client.commonservices.componentmanager;
	exports com.ibm.msg.client.commonservices.cssystem;
	exports com.ibm.msg.client.commonservices.j2se;
	exports com.ibm.msg.client.commonservices.j2se.commandmanager;
	exports com.ibm.msg.client.commonservices.j2se.log;
	exports com.ibm.msg.client.commonservices.j2se.propertystore;
	exports com.ibm.msg.client.commonservices.j2se.trace;
	exports com.ibm.msg.client.commonservices.j2se.wmqsupport;
	exports com.ibm.msg.client.commonservices.j2se.workqueue;
	exports com.ibm.msg.client.commonservices.locking;
	exports com.ibm.msg.client.commonservices.Log;
	exports com.ibm.msg.client.commonservices.monitor;
	exports com.ibm.msg.client.commonservices.nls;
	exports com.ibm.msg.client.commonservices.passwordprotection;
	exports com.ibm.msg.client.commonservices.passwordprotection.algorithms;
	exports com.ibm.msg.client.commonservices.passwordprotection.passwordencodings;
	exports com.ibm.msg.client.commonservices.propertystore;
	exports com.ibm.msg.client.commonservices.provider;
	exports com.ibm.msg.client.commonservices.provider.commandmanager;
	exports com.ibm.msg.client.commonservices.provider.log;
	exports com.ibm.msg.client.commonservices.provider.nls;
	exports com.ibm.msg.client.commonservices.provider.propertystore;
	exports com.ibm.msg.client.commonservices.provider.trace;
	exports com.ibm.msg.client.commonservices.provider.workqueue;
	exports com.ibm.msg.client.commonservices.resources;
	opens com.ibm.msg.client.commonservices.resources;
	exports com.ibm.msg.client.commonservices.tools;
	opens com.ibm.msg.client.commonservices.tools;
	exports com.ibm.msg.client.commonservices.trace;
	opens com.ibm.msg.client.commonservices.trace;
	export com.ibm.msg.client.commonservices.util;
	export com.ibm.msg.client.commonservices.workspace;
	exports com.ibm.msg.client.jakarta.jms;
	exports com.ibm.msg.client.jakarta.jms.admin;
	exports com.ibm.msg.client.jakarta.jms.admin.internal;
	exports com.ibm.msg.client.jakarta.jms.admin.internal.resources;
	opens com.ibm.msg.client.jakarta.jms.admin.internal.resources;
	exports com.ibm.msg.client.jakarta.provider;
	exports com.ibm.msg.client.jakarta.services;
	exports com.ibm.msg.client.jakarta.wmq;
	exports com.ibm.msg.client.jakarta.wmq.common;
	exports com.ibm.msg.client.jakarta.wmq.compat;
	exports com.ibm.msg.client.jakarta.wmq.compat.base.internal;
	exports com.ibm.msg.client.jakarta.wmq.compat.base.internal.resources;
	opens com.ibm.msg.client.jakarta.wmq.compat.base.internal.resources;
	exports com.ibm.msg.client.jakarta.wmq.compat.jms.internal;
	exports com.ibm.msg.client.jakarta.wmq.compat.jms.internal.services;
	exports com.ibm.msg.client.jakarta.wmq.compat.network;
	exports com.ibm.msg.client.jakarta.wmq.factories;
	exports com.ibm.msg.client.jakarta.wmq.factories.admin;
	exports com.ibm.msg.client.jakarta.wmq.factories.resources;
	opens com.ibm.msg.client.jakarta.wmq.factories.resources;
	exports com.ibm.msg.client.jakarta.wmq.internal;
	exports com.ibm.msg.client.jakarta.wmq.internal.resources;
	opens com.ibm.msg.client.jakarta.wmq.internal.resources;
	exports com.ibm.msg.client.jakarta.wmq.messages;
}