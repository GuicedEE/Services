module xalan {
    requires java.xml;

    exports org.apache.bcel;
    exports org.apache.bcel.classfile;
    exports org.apache.bcel.generic;
    exports org.apache.bcel.util;
    exports org.apache.bcel.verifier;

    exports org.apache.regexp;

    exports org.apache.xalan;
    exports org.apache.xalan.client;
    exports org.apache.xalan.extensions;
    exports org.apache.xalan.lib;
    exports org.apache.xalan.processor;
    exports org.apache.xalan.res;
    exports org.apache.xalan.serialize;
    exports org.apache.xalan.templates;
    exports org.apache.xalan.trace;
    exports org.apache.xalan.transformer;
    exports org.apache.xalan.xslt;
    exports org.apache.xalan.xsltc;

    exports org.apache.xml.dtm;
    exports org.apache.xml.res;
    exports org.apache.xml.serializer;
    exports org.apache.xml.utils;

    exports org.apache.xpath;

    provides javax.xml.transform.TransformerFactory with org.apache.xalan.processor.TransformerFactoryImpl;
    provides javax.xml.xpath.XPathFactory with org.apache.xpath.jaxp.XPathFactoryImpl;

    uses org.apache.xalan.extensions.bsf.BSFManager;
    provides org.apache.xalan.extensions.bsf.BSFManager with org.apache.bsf.BSFManager;

    uses  org.apache.xml.dtm.DTMManager;
    provides org.apache.xml.dtm.DTMManager with org.apache.xml.dtm.ref.DTMManagerDefault;

}
