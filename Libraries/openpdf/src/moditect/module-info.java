module com.github.librepdf.openpdf {
	requires static java.desktop;
	requires jakarta.annotation;

	requires static org.bouncycastle.pkix;
	//requires static org.bouncycastle.provider;
	requires static com.github.spotbugs.spotbugs;
	requires static imageio.tiff;
	requires static org.apache.fop;

	// OpenPDF renamed its packages from com.lowagie.text.* to org.openpdf.text.* (the jar
	// now ships org.openpdf.*, nothing under com.lowagie). Exporting the old names made
	// every export stale at once:
	//   InvalidModuleDescriptorException: Package com.lowagie.text not found in module
	// The list below mirrors the previous API surface 1:1 under the new names. Note the jar
	// also contains org.openpdf.text.utils / .xml / .xml.simpleparser / .xml.xmp,
	// org.openpdf.bouncycastle and org.mozilla.universalchardet.* which stay unexported,
	// as they were before the rename.
	exports org.openpdf.text;
	exports org.openpdf.text.alignment;
	exports org.openpdf.text.error_messages;
	exports org.openpdf.text.exceptions;
	exports org.openpdf.text.factories;
	exports org.openpdf.text.html;
	exports org.openpdf.text.html.simpleparser;
	exports org.openpdf.text.pdf;
	exports org.openpdf.text.pdf.codec;
	exports org.openpdf.text.pdf.codec.wmf;
	exports org.openpdf.text.pdf.collection;
	exports org.openpdf.text.pdf.crypto;
	exports org.openpdf.text.pdf.draw;
	exports org.openpdf.text.pdf.events;
	exports org.openpdf.text.pdf.fonts;
	exports org.openpdf.text.pdf.fonts.cmaps;
	exports org.openpdf.text.pdf.hyphenation;
	exports org.openpdf.text.pdf.interfaces;
	exports org.openpdf.text.pdf.internal;
	exports org.openpdf.text.pdf.parser;
}

