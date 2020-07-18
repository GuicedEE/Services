module org.apache.poi {
	requires java.desktop;
	requires java.xml.bind;
	requires static org.apache.commons.math3;
	requires static org.apache.commons.codec;
	requires static org.apache.commons.collections4;

	requires org.apache.commons.logging;

	exports org.apache.poi.hssf.usermodel;
	exports org.apache.poi.ss.usermodel;
	exports org.apache.poi.ss.formula;
	exports org.apache.poi.hssf.util;
	exports org.apache.poi.ss.util;

	exports org.apache.poi.common;

	exports org.apache.poi;
	exports org.apache.poi.common.usermodel;
	exports org.apache.poi.common.usermodel.fonts;
	exports org.apache.poi.util;
	exports org.apache.poi.poifs.filesystem;
	exports org.apache.poi.ss;
	exports org.apache.poi.ss.formula.udf;
	exports org.apache.poi.ss.usermodel.charts;
}
