
open module org.apache.poi.ooxml {
	requires org.apache.poi.poi;
	requires org.apache.xmlbeans;
	requires org.apache.fop;
	//requires poi.ooxml.schemas;
	requires org.apache.commons.collections4;
	requires org.apache.commons.codec;
	requires org.apache.commons.math3;
	//requires SparseBitSet;
	requires org.slf4j;
	requires java.logging;
	requires java.desktop;
	requires static java.security.jgss;
	requires static java.xml.crypto;
	requires static org.bouncycastle.provider;
	requires static  org.bouncycastle.pkix;
	
	requires org.apache.commons.compress;
	
	exports org.apache.poi.xwpf.extractor;
	exports org.apache.poi.xwpf.usermodel;
	exports org.apache.poi.xwpf.model;
	exports org.apache.poi.xdgf.extractor;
	exports org.apache.poi.xdgf.exceptions;
	exports org.apache.poi.xdgf.usermodel;
	exports org.apache.poi.xdgf.usermodel.section;
	exports org.apache.poi.xdgf.usermodel.section.geometry;
	exports org.apache.poi.xdgf.usermodel.shape;
	exports org.apache.poi.xdgf.usermodel.shape.exceptions;
	exports org.apache.poi.xdgf.xml;
	exports org.apache.poi.xdgf.util;
	exports org.apache.poi.xdgf.geom;
	exports org.apache.poi.ooxml;
	exports org.apache.poi.ooxml.dev;
	exports org.apache.poi.ooxml.extractor;
	exports org.apache.poi.ooxml.util;
	exports org.apache.poi.xddf.usermodel;
	exports org.apache.poi.xddf.usermodel.text;
	exports org.apache.poi.xddf.usermodel.chart;
	exports org.apache.poi.openxml4j.exceptions;
	exports org.apache.poi.openxml4j.opc;
	exports org.apache.poi.openxml4j.opc.internal;
	exports org.apache.poi.openxml4j.opc.internal.marshallers;
	exports org.apache.poi.openxml4j.opc.internal.unmarshallers;
	exports org.apache.poi.openxml4j.util;
	exports org.apache.poi.xssf;
	exports org.apache.poi.xssf.extractor;
	exports org.apache.poi.xssf.eventusermodel;
	exports org.apache.poi.xssf.usermodel;
	exports org.apache.poi.xssf.usermodel.helpers;
	exports org.apache.poi.xssf.usermodel.extensions;
	exports org.apache.poi.xssf.binary;
	exports org.apache.poi.xssf.model;
	exports org.apache.poi.xssf.streaming;
	exports org.apache.poi.xssf.util;
	exports org.apache.poi.xslf.draw;
	exports org.apache.poi.xslf.usermodel;
	exports org.apache.poi.xslf.model;
	exports org.apache.poi.xslf.util;
	exports org.apache.poi.poifs.crypt.dsig;
	exports org.apache.poi.poifs.crypt.dsig.facets;
	exports org.apache.poi.poifs.crypt.dsig.services;
	exports org.apache.poi.poifs.crypt.temp;
	
	exports org.apache.poi.schemas.ooxml.system.ooxml;
	
	exports org.apache.poi.xwpf.converter.pdf;
	exports org.apache.poi.xwpf.converter.pdf.internal;
	exports org.apache.poi.xwpf.converter.pdf.internal.elements;
	exports org.apache.poi.xwpf.converter.core;
	exports org.apache.poi.xwpf.converter.core.openxmlformats;
	exports org.apache.poi.xwpf.converter.core.openxmlformats.styles;
	exports org.apache.poi.xwpf.converter.core.openxmlformats.styles.paragraph;
	exports org.apache.poi.xwpf.converter.core.openxmlformats.styles.run;
	exports org.apache.poi.xwpf.converter.core.openxmlformats.styles.table;
	exports org.apache.poi.xwpf.converter.core.openxmlformats.styles.table.row;
	exports org.apache.poi.xwpf.converter.core.openxmlformats.styles.table.cell;
	exports org.apache.poi.xwpf.converter.core.registry;
	exports org.apache.poi.xwpf.converter.core.styles;
	exports org.apache.poi.xwpf.converter.core.styles.paragraph;
	exports org.apache.poi.xwpf.converter.core.styles.run;
	exports org.apache.poi.xwpf.converter.core.styles.table;
	exports org.apache.poi.xwpf.converter.core.styles.table.row;
	exports org.apache.poi.xwpf.converter.core.styles.table.cell;
	exports org.apache.poi.xwpf.converter.core.utils;
	
	
	
	/*opens org.apache.poi.openxml4j.opc to
			org.apache.poi.poi;*/
	
//	opens org.apache.poi.schemas.ooxml.system.ooxml;
	
	provides org.apache.poi.extractor.ExtractorProvider with
			org.apache.poi.ooxml.extractor.POIXMLExtractorFactory;
	provides org.apache.poi.ss.usermodel.WorkbookProvider with
			org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
	provides org.apache.poi.sl.usermodel.SlideShowProvider with
			org.apache.poi.xslf.usermodel.XSLFSlideShowFactory;
	provides org.apache.poi.sl.draw.ImageRenderer with
			org.apache.poi.xslf.draw.SVGImageRenderer;
}
