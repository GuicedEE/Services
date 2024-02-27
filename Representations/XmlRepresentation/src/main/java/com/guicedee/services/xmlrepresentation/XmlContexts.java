package com.guicedee.services.xmlrepresentation;

import jakarta.xml.bind.JAXBContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class XmlContexts {
    public static final Map<Class<?>, JAXBContext> JAXB = new HashMap<>();

    private XmlContexts(){}
}
