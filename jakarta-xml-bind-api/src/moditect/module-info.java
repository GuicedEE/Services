/*
 * Copyright (c) 2005, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Jakarta XML Binding API.
 *
 * <p>
 * References in this document to JAXB refer to the Jakarta XML Binding unless otherwise noted.
 */
module java.xml.bind {
	requires transitive jakarta.activation;
	requires transitive java.xml;

	requires transitive java.logging;
	requires java.desktop;

	requires transitive java.validation;

	exports javax.xml.bind;
	exports javax.xml.bind.annotation;
	exports javax.xml.bind.annotation.adapters;
	exports javax.xml.bind.attachment;
	exports javax.xml.bind.helpers;
	exports javax.xml.bind.util;

	uses javax.xml.bind.JAXBContextFactory;

	requires java.compiler;

	requires org.codehaus.stax2;

	exports com.sun.xml.bind;
	exports com.sun.xml.bind.annotation;
	exports com.sun.xml.bind.api;
	exports com.sun.xml.bind.api.impl;
	exports com.sun.xml.bind.marshaller;
	exports com.sun.xml.bind.unmarshaller;
	exports com.sun.xml.bind.util;
	exports com.sun.xml.bind.v2;
	exports com.sun.xml.bind.v2.model.annotation;
	exports com.sun.xml.bind.v2.model.core;
	exports com.sun.xml.bind.v2.model.impl;
	exports com.sun.xml.bind.v2.model.nav;
	exports com.sun.xml.bind.v2.model.runtime;
	exports com.sun.xml.bind.v2.model.util;
	exports com.sun.xml.bind.v2.runtime;
	exports com.sun.xml.bind.v2.runtime.unmarshaller;
	exports com.sun.xml.bind.v2.schemagen;
	exports com.sun.xml.bind.v2.schemagen.episode;
	exports com.sun.xml.bind.v2.schemagen.xmlschema;
	exports com.sun.xml.bind.v2.util;

	exports com.sun.xml.txw2;

	opens com.sun.xml.bind.v2.model.nav to com.sun.tools.xjc;

	provides javax.xml.bind.JAXBContextFactory with com.sun.xml.bind.v2.JAXBContextFactory;
	provides javax.xml.bind.JAXBContext with com.sun.xml.bind.v2.ContextFactory;
}
