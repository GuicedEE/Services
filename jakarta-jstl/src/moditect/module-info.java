open module jakarta.servlet.jsp.jstl {
	requires transitive jakarta.servlet.jsp;

	requires java.xml;

	requires static java.sql;

	requires static jakarta.xml.bind;

	requires xalan;

	requires java.desktop;
	requires java.naming;
	requires jdk.xml.dom;

	exports jakarta.servlet.jsp.jstl.core;
	exports jakarta.servlet.jsp.jstl.fmt;
	exports jakarta.servlet.jsp.jstl.sql;
	exports jakarta.servlet.jsp.jstl.tlv;

	exports org.apache.taglibs.standard;
	exports org.apache.taglibs.standard.extra;
	exports org.apache.taglibs.standard.functions;
	exports org.apache.taglibs.standard.lang;
	exports org.apache.taglibs.standard.resources;
	exports org.apache.taglibs.standard.tag;
	exports org.apache.taglibs.standard.tag.el.core;
	exports org.apache.taglibs.standard.tag.el.fmt;
	exports org.apache.taglibs.standard.tag.el.sql;
	exports org.apache.taglibs.standard.tag.el.xml;
	exports org.apache.taglibs.standard.tag.rt.core;
	exports org.apache.taglibs.standard.tag.rt.fmt;
	exports org.apache.taglibs.standard.tag.rt.sql;
	exports org.apache.taglibs.standard.tag.rt.xml;

	exports org.apache.taglibs.standard.tag.common.core;
	exports org.apache.taglibs.standard.tag.common.fmt;
	exports org.apache.taglibs.standard.tag.common.sql;
	exports org.apache.taglibs.standard.tag.common.xml;
	exports org.apache.taglibs.standard.tei;
	exports org.apache.taglibs.standard.tlv;


}
