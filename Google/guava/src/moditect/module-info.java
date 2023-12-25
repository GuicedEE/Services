module com.google.common {
	requires transitive java.logging;

	requires java.compiler;

	exports com.google.common.annotations;
	exports com.google.common.base;
	exports com.google.common.base.internal;
	exports com.google.common.cache;
	exports com.google.common.collect;
	exports com.google.common.escape;
	exports com.google.common.eventbus;
	exports com.google.common.graph;
	exports com.google.common.hash;
	exports com.google.common.html;
	exports com.google.common.io;
	exports com.google.common.math;
	exports com.google.common.net;
	exports com.google.common.primitives;
	exports com.google.common.reflect;
	exports com.google.common.util.concurrent;
	exports com.google.common.xml;

	exports com.google.protobuf;

	exports com.google.zxing;
	exports com.google.zxing.common;
	exports com.google.zxing.oned;
	exports com.google.zxing.client.j2se;
	exports com.google.zxing.qrcode.decoder;

	exports com.google.errorprone.annotations;
	
	requires transitive jakarta.activation;
	
	
	requires java.desktop;
	requires jdk.unsupported;
}
