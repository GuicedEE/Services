module org.apache.commons.fileupload {
	exports org.apache.commons.fileupload;
	exports org.apache.commons.fileupload.disk;
	exports org.apache.commons.fileupload.servlet;

	requires transitive jakarta.servlet;

	requires transitive org.apache.commons.io;

}
