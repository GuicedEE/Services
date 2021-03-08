module org.apache.commons.logging {
	requires transitive java.logging;
	requires static jakarta.servlet;
	exports org.apache.commons.logging;
}

