module org.slf4j {

	requires static org.apache.logging.log4j.core;

	exports org.slf4j;
	exports org.slf4j.event;
	exports org.slf4j.helpers;
	exports org.slf4j.impl;
	exports org.slf4j.spi;
}
