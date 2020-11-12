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
module jakarta.xml.bind {
	requires transitive jakarta.activation;
	requires transitive java.xml;
	requires transitive java.validation;
	requires transitive java.logging;
	requires java.desktop;
	requires java.compiler;
	requires org.codehaus.stax2;

	exports jakarta.xml.bind;
	exports jakarta.xml.bind.annotation;
	exports jakarta.xml.bind.annotation.adapters;
	exports jakarta.xml.bind.attachment;
	exports jakarta.xml.bind.helpers;
	exports jakarta.xml.bind.util;
	exports com.sun.xml.txw2;

	uses jakarta.xml.bind.JAXBContextFactory;


	provides jakarta.xml.bind.JAXBContextFactory with org.glassfish.jaxb.runtime.v2.JAXBContextFactory;
	provides jakarta.xml.bind.JAXBContext with org.glassfish.jaxb.runtime.v2.ContextFactory;
}
