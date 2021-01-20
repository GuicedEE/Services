module com.neuronrobotics.nrjavaserial {
	exports gnu.io;
	
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.annotation;
	
	opens gnu.io to com.google.guice, com.fasterxml.jackson.databind;
}

