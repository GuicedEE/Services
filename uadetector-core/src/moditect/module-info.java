module net.sf.uadetector.core {
	exports net.sf.uadetector;

	exports net.sf.uadetector.datastore to net.sf.uadetector.resources;
	exports net.sf.uadetector.datareader to net.sf.uadetector.resources;
	exports net.sf.uadetector.parser to net.sf.uadetector.resources;

	requires transitive jakarta.activation;

	requires java.validation;
	requires java.xml;

	requires transitive java.logging;
}
