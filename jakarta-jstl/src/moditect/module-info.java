open module javax.servlet.jsp.jstl {
	requires transitive javax.servlet.jsp;
	requires java.xml;
	requires static java.sql;

	requires static java.xml.bind;

	requires java.desktop;
	requires java.naming;
	requires jdk.xml.dom;

	exports javax.servlet.jsp.jstl.core;
	exports javax.servlet.jsp.jstl.fmt;
	exports javax.servlet.jsp.jstl.sql;
	exports javax.servlet.jsp.jstl.tlv;
}
