module com.neuronrobotics.nrjavaserial {
	exports gnu.io;
	
	requires tools.jackson.core;
	requires transitive org.jspecify;
	
	requires com.fasterxml.jackson.annotation;

	requires com.sun.jna;
	
	opens gnu.io to com.google.guice, tools.jackson.databind;
}

