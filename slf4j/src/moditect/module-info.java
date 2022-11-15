module org.slf4j {
	exports org.slf4j;
	exports org.slf4j.event;
	exports org.slf4j.helpers;
	//exports org.slf4j.impl;
	exports org.slf4j.spi;
	exports org.slf4j.bridge;
	
	requires java.logging;
	
	requires org.apache.logging.log4j.core;
	
	uses org.slf4j.spi.SLF4JServiceProvider;
}
