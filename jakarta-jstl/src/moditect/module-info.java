open module jakarta.servlet.jsp.jstl {
	requires transitive jakarta.servlet.jsp;
	requires java.xml;
	requires static java.sql;

	requires static java.xml.bind;

	requires java.desktop;
	requires java.naming;
	requires jdk.xml.dom;

	exports jakarta.servlet.jsp.jstl.core;
	exports jakarta.servlet.jsp.jstl.fmt;
	exports jakarta.servlet.jsp.jstl.sql;
	exports jakarta.servlet.jsp.jstl.tlv;
}
