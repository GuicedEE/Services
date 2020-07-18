module org.apache.xmlbeans {
	requires transitive java.xml;
	requires static jdk.javadoc;

	exports org.apache.xmlbeans;
	exports org.apache.xmlbeans.impl.common;

	provides javax.xml.transform.TransformerFactory with net.sf.saxon.TransformerFactoryImpl;
}
