open module javax.servlet.jsp {

	exports javax.servlet.jsp;

	requires transitive java.logging;
	requires java.xml;

	requires transitive javax.el;
	requires transitive java.servlet;

	requires java.desktop;
	requires java.compiler;
}
