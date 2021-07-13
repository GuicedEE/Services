//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

module net.bytebuddy {
	requires java.instrument;
	requires jdk.unsupported;
	//requires net.bytebuddy.agent;
	
	requires com.sun.jna;
	//requires com.sun.jna.platform;
	
	exports net.bytebuddy.utility.dispatcher;
	
	exports net.bytebuddy;
	exports net.bytebuddy.agent.builder;
	exports net.bytebuddy.asm;
	exports net.bytebuddy.build;
	exports net.bytebuddy.description;
	exports net.bytebuddy.description.annotation;
	exports net.bytebuddy.description.enumeration;
	exports net.bytebuddy.description.field;
	exports net.bytebuddy.description.method;
	exports net.bytebuddy.description.modifier;
	exports net.bytebuddy.description.type;
	exports net.bytebuddy.dynamic;
	exports net.bytebuddy.dynamic.loading;
	exports net.bytebuddy.dynamic.scaffold;
	exports net.bytebuddy.dynamic.scaffold.inline;
	exports net.bytebuddy.dynamic.scaffold.subclass;
	exports net.bytebuddy.implementation;
	exports net.bytebuddy.implementation.attribute;
	exports net.bytebuddy.implementation.auxiliary;
	exports net.bytebuddy.implementation.bind;
	exports net.bytebuddy.implementation.bind.annotation;
	exports net.bytebuddy.implementation.bytecode;
	exports net.bytebuddy.implementation.bytecode.assign;
	exports net.bytebuddy.implementation.bytecode.assign.primitive;
	exports net.bytebuddy.implementation.bytecode.assign.reference;
	exports net.bytebuddy.implementation.bytecode.collection;
	exports net.bytebuddy.implementation.bytecode.constant;
	exports net.bytebuddy.implementation.bytecode.member;
	exports net.bytebuddy.matcher;
	exports net.bytebuddy.pool;
	exports net.bytebuddy.utility;
	exports net.bytebuddy.utility.privilege;
	exports net.bytebuddy.utility.visitor;
	exports net.bytebuddy.jar.asm;
	exports net.bytebuddy.jar.asm.signature;
	exports net.bytebuddy.jar.asm.commons;
}
