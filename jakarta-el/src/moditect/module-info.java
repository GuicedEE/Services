module jakarta.el {
	requires java.desktop;
	exports jakarta.el;

	//exports com.sun.el to jakarta.faces;
	uses jakarta.el.ExpressionFactory;
	provides jakarta.el.ExpressionFactory with com.sun.el.ExpressionFactoryImpl;
}
