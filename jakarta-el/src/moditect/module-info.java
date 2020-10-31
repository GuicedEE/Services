module javax.el {
	requires java.desktop;
	exports javax.el;

	uses javax.el.ExpressionFactory;
	provides javax.el.ExpressionFactory with com.sun.el.ExpressionFactoryImpl;
}
