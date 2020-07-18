module org.apache.poi.ooxml {
	requires transitive org.apache.poi;
	requires static org.apache.xmlbeans;

	requires static poi.ooxml.schemas;
	requires java.desktop;

	requires org.apache.commons.compress;

	requires org.apache.commons.collections4;

	exports org.apache.poi.xssf.usermodel;
	exports org.apache.poi.xslf.usermodel;
	exports org.apache.poi.xdgf.usermodel;
	exports org.apache.poi.xddf.usermodel;
	exports org.apache.poi.xwpf.usermodel;

	exports org.apache.poi.xssf.streaming;
}
