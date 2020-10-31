module javax.el {
	requires java.desktop;
	exports javax.el;

	exports com.sun.el to javax.faces;
	uses javax.el.ExpressionFactory;
	provides javax.el.ExpressionFactory with com.sun.el.ExpressionFactoryImpl;
}
