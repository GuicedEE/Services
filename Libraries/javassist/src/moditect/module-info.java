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
	// javassist 3.32.0-GA removed javassist.tools.reflect, javassist.tools.rmi and
	// javassist.tools.web (3.31.0-GA still shipped all three). Exporting a package the
	// jar does not contain makes the module unreadable at runtime:
	//   InvalidModuleDescriptorException: Package javassist.tools.reflect not found in module
	// Re-add these only if a future upstream release restores those packages.

}
