module org.slf4j {
	exports org.slf4j;
	exports org.slf4j.event;
	exports org.slf4j.helpers;
	//exports org.slf4j.impl;
	exports org.slf4j.spi;
	exports org.slf4j.bridge;
	
	requires java.logging;
	
	requires static org.apache.logging.log4j.core;
}
