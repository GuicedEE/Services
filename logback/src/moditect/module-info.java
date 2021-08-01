module ch.qos.logback.core {
	requires static java.sql;
	requires java.naming;
	requires static jakarta.mail;
	requires static jakarta.servlet;
	
	
	exports ch.qos.logback.core;
	exports ch.qos.logback.core.boolex;
	exports ch.qos.logback.core.db;
	exports ch.qos.logback.core.db.dialect;
	exports ch.qos.logback.core.encoder;
	exports ch.qos.logback.core.filter;
	exports ch.qos.logback.core.helpers;
	exports ch.qos.logback.core.html;
	exports ch.qos.logback.core.joran;
	exports ch.qos.logback.core.layout;
	exports ch.qos.logback.core.pattern;
	exports ch.qos.logback.core.property;
	exports ch.qos.logback.core.read;
	exports ch.qos.logback.core.recovery;
	exports ch.qos.logback.core.sift;
	exports ch.qos.logback.core.spi;
	exports ch.qos.logback.core.status;
	exports ch.qos.logback.core.subst;
	exports ch.qos.logback.core.util;
	
	
}