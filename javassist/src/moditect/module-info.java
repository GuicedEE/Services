module javassist {
	requires java.instrument;
	requires java.management;
	requires static jdk.attach;
	requires static jdk.jdi;
	requires java.desktop;

	exports javassist;
	exports javassist.util;
	exports javassist.util.proxy;
	exports javassist.bytecode;
	exports javassist.bytecode.analysis;
	exports javassist.bytecode.annotation;
	exports javassist.bytecode.stackmap;
	exports javassist.compiler;
	exports javassist.compiler.ast;
	exports javassist.convert;
	exports javassist.expr;
	exports javassist.runtime;
	exports javassist.scopedpool;
	exports javassist.tools;
	exports javassist.tools.reflect;
	exports javassist.tools.rmi;
	exports javassist.tools.web;

}
