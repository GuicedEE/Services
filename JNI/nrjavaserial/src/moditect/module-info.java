module com.neuronrobotics.nrjavaserial {
	exports gnu.io;
	
	requires com.fasterxml.jackson.core;
	requires transitive org.jspecify;
	
	requires com.fasterxml.jackson.annotation;

	requires com.sun.jna;
	
	opens gnu.io to com.google.guice, com.fasterxml.jackson.databind;
}

