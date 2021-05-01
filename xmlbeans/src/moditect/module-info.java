module org.apache.xmlbeans {
	requires java.xml;
	requires static jdk.javadoc;
	requires jdk.xml.dom;

	exports org.apache.xmlbeans;
    exports org.apache.xmlbeans.soap;
    exports org.apache.xmlbeans.impl.xpathgen;
    exports org.apache.xmlbeans.impl.validator;
    exports org.apache.xmlbeans.impl.repackage;
    exports org.apache.xmlbeans.impl.common;
    exports org.apache.xmlbeans.impl.config;
    exports org.apache.xmlbeans.impl.richParser;
    exports org.apache.xmlbeans.impl.soap;
    exports org.apache.xmlbeans.impl.xpath;
    exports org.apache.xmlbeans.impl.xpath.saxon;
    exports org.apache.xmlbeans.impl.xpath.xmlbeans;
    exports org.apache.xmlbeans.impl.regex;
    exports org.apache.xmlbeans.impl.tool;
    exports org.apache.xmlbeans.impl.schema;
    exports org.apache.xmlbeans.impl.xsd2inst;
    exports org.apache.xmlbeans.impl.values;
    exports org.apache.xmlbeans.impl.inst2xsd;
    exports org.apache.xmlbeans.impl.inst2xsd.util;
    exports org.apache.xmlbeans.impl.store;
    exports org.apache.xmlbeans.impl.util;
    exports org.apache.xmlbeans.xml.stream;
    exports org.apache.xmlbeans.xml.stream.events;
    exports org.apache.xmlbeans.impl.xb.xmlconfig;
    exports org.apache.xmlbeans.impl.xb.xmlschema;
    exports org.apache.xmlbeans.impl.xb.xsdschema;
    exports org.apache.xmlbeans.impl.xb.xsdownload;

    opens org.apache.xmlbeans.metadata.system.sXMLCONFIG;
    opens org.apache.xmlbeans.metadata.system.sXMLLANG;
    opens org.apache.xmlbeans.metadata.system.sXMLSCHEMA;
    opens org.apache.xmlbeans.metadata.system.sXMLTOOLS;
}
