module com.github.librepdf.openpdf {
	requires java.desktop;
	requires java.annotation;
	exports com.lowagie.text;
	exports com.lowagie.text.alignment;
	exports com.lowagie.text.error_messages;
	exports com.lowagie.text.exceptions;
	exports com.lowagie.text.factories;
	exports com.lowagie.text.html;
	exports com.lowagie.text.html.simpleparser;
	exports com.lowagie.text.pdf;
	exports com.lowagie.text.pdf.codec;
	exports com.lowagie.text.pdf.codec.wmf;
	exports com.lowagie.text.pdf.collection;
	exports com.lowagie.text.pdf.crypto;
	exports com.lowagie.text.pdf.draw;
	exports com.lowagie.text.pdf.events;
	exports com.lowagie.text.pdf.fonts;
	exports com.lowagie.text.pdf.fonts.cmaps;
	exports com.lowagie.text.pdf.hyphenation;
	exports com.lowagie.text.pdf.interfaces;
	exports com.lowagie.text.pdf.internal;
	exports com.lowagie.text.pdf.parser;

	provides javax.imageio.spi.ImageReaderSpi with com.twelvemonkeys.imageio.plugins.tiff.TIFFImageReaderSpi,
			                                          com.twelvemonkeys.imageio.plugins.tiff.BigTIFFImageReaderSpi;
	provides javax.imageio.spi.ImageWriterSpi with com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriterSpi;
	provides javax.xml.transform.URIResolver with org.apache.xmlgraphics.util.uri.DataURIResolver;

	uses org.apache.batik.bridge.BridgeExtension;
	provides org.apache.batik.bridge.BridgeExtension with org.apache.batik.extension.svg.BatikBridgeExtension;
	uses org.apache.batik.dom.DomExtension;
	provides org.apache.batik.dom.DomExtension with org.apache.batik.extension.svg.BatikDomExtension;
	uses org.apache.batik.script.InterpreterFactory;
	provides org.apache.batik.script.InterpreterFactory with org.apache.batik.bridge.RhinoInterpreterFactory;
	uses org.apache.fop.events.EventExceptionManager.ExceptionFactory;
	provides org.apache.fop.events.EventExceptionManager.ExceptionFactory with org.apache.fop.events.ValidationExceptionFactory,
			                                                                      org.apache.fop.events.PropertyExceptionFactory,
			                                                                      org.apache.fop.events.UnsupportedOperationExceptionFactory,
			                                                                      org.apache.fop.layoutmgr.LayoutException.LayoutExceptionFactory,
			                                                                      org.apache.fop.fo.pagination.PageProductionException.PageProductionExceptionFactory;
	uses org.apache.fop.fo.ElementMapping;
	provides org.apache.fop.fo.ElementMapping with org.apache.fop.fo.FOElementMapping,
			                                          org.apache.fop.fo.extensions.svg.SVGElementMapping,
			                                          org.apache.fop.fo.extensions.svg.BatikExtensionElementMapping,
			                                          org.apache.fop.fo.extensions.ExtensionElementMapping,
			                                          org.apache.fop.fo.extensions.InternalElementMapping,
			                                          org.apache.fop.fo.extensions.OldExtensionElementMapping,
			                                          org.apache.fop.fo.extensions.xmp.XMPElementMapping,
			                                          org.apache.fop.fo.extensions.xmp.RDFElementMapping,
			                                          org.apache.fop.render.ps.extensions.PSExtensionElementMapping,
			                                          org.apache.fop.render.afp.extensions.AFPElementMapping,
			                                          org.apache.fop.render.pcl.extensions.PCLElementMapping,
			                                          org.apache.fop.render.pdf.extensions.PDFElementMapping;
	uses org.apache.fop.fo.FOEventHandler;
	provides org.apache.fop.fo.FOEventHandler with org.apache.fop.render.rtf.RTFFOEventHandlerMaker;
	uses org.apache.fop.render.ImageHandler;
	provides org.apache.fop.render.ImageHandler with org.apache.fop.render.pdf.PDFImageHandlerGraphics2D,
			                                            org.apache.fop.render.pdf.PDFImageHandlerRenderedImage,
			                                            org.apache.fop.render.pdf.PDFImageHandlerRawJPEG,
			                                            org.apache.fop.render.pdf.PDFImageHandlerRawPNG,
			                                            org.apache.fop.render.pdf.PDFImageHandlerRawCCITTFax,
			                                            org.apache.fop.render.pdf.PDFImageHandlerSVG,
			                                            org.apache.fop.render.java2d.Java2DImageHandlerRenderedImage,
			                                            org.apache.fop.render.java2d.Java2DImageHandlerGraphics2D,
			                                            org.apache.fop.render.pcl.PCLImageHandlerRenderedImage,
			                                            org.apache.fop.render.pcl.PCLImageHandlerGraphics2D,
			                                            org.apache.fop.render.ps.PSImageHandlerRenderedImage,
			                                            org.apache.fop.render.ps.PSImageHandlerEPS,
			                                            org.apache.fop.render.ps.PSImageHandlerRawCCITTFax,
			                                            org.apache.fop.render.ps.PSImageHandlerRawJPEG,
			                                            org.apache.fop.render.ps.PSImageHandlerRawPNG,
			                                            org.apache.fop.render.ps.PSImageHandlerGraphics2D,
			                                            org.apache.fop.render.ps.PSImageHandlerSVG,
			                                            org.apache.fop.render.afp.AFPImageHandlerRenderedImage,
			                                            org.apache.fop.render.afp.AFPImageHandlerGraphics2D,
			                                            org.apache.fop.render.afp.AFPImageHandlerRawStream,
			                                            org.apache.fop.render.afp.AFPImageHandlerRawCCITTFax,
			                                            org.apache.fop.render.afp.AFPImageHandlerRawJPEG,
			                                            org.apache.fop.render.afp.AFPImageHandlerSVG;
	uses org.apache.fop.render.intermediate.IFDocumentHandler;
	provides org.apache.fop.render.intermediate.IFDocumentHandler with org.apache.fop.render.pdf.PDFDocumentHandlerMaker,
			                                                              org.apache.fop.render.pcl.PCLDocumentHandlerMaker,
			                                                              org.apache.fop.render.bitmap.TIFFDocumentHandlerMaker,
			                                                              org.apache.fop.render.bitmap.PNGDocumentHandlerMaker,
			                                                              org.apache.fop.render.ps.PSDocumentHandlerMaker,
			                                                              org.apache.fop.render.afp.AFPDocumentHandlerMaker,
			                                                              org.apache.fop.render.intermediate.IFSerializerMaker;
	uses org.apache.fop.render.Renderer;
	provides org.apache.fop.render.Renderer with org.apache.fop.render.txt.TXTRendererMaker,
			                                        org.apache.fop.render.bitmap.PNGRendererMaker,
			                                        org.apache.fop.render.bitmap.TIFFRendererMaker,
			                                        org.apache.fop.render.xml.XMLRendererMaker,
			                                        org.apache.fop.render.awt.AWTRendererMaker,
			                                        org.apache.fop.render.print.PrintRendererMaker;
	uses org.apache.fop.render.XMLHandler;
	provides org.apache.fop.render.XMLHandler with org.apache.fop.render.pdf.PDFSVGHandler,
			                                          org.apache.fop.render.ps.PSSVGHandler,
			                                          org.apache.fop.render.java2d.Java2DSVGHandler,
			                                          org.apache.fop.render.pcl.PCLSVGHandler,
			                                          org.apache.fop.render.afp.AFPSVGHandler;
	uses org.apache.fop.util.ContentHandlerFactory;
	provides org.apache.fop.util.ContentHandlerFactory with org.apache.fop.render.afp.extensions.AFPExtensionHandlerFactory,
			                                                   org.apache.fop.render.pdf.extensions.PDFExtensionHandlerFactory,
			                                                   org.apache.fop.render.ps.extensions.PSExtensionHandlerFactory,
			                                                   org.apache.fop.fo.extensions.xmp.XMPContentHandlerFactory;
	uses org.apache.fop.util.text.AdvancedMessageFormat.Function;
	provides org.apache.fop.util.text.AdvancedMessageFormat.Function with org.apache.fop.fo.FONode.GatherContextInfoFunction;
	uses org.apache.fop.util.text.AdvancedMessageFormat.ObjectFormatter;
	provides org.apache.fop.util.text.AdvancedMessageFormat.ObjectFormatter with org.apache.fop.util.text.LocatorFormatter;
	uses org.apache.fop.util.text.AdvancedMessageFormat.PartFactory;
	provides org.apache.fop.util.text.AdvancedMessageFormat.PartFactory with org.apache.fop.util.text.IfFieldPart.Factory,
			                                                                    org.apache.fop.util.text.EqualsFieldPart.Factory,
			                                                                    org.apache.fop.util.text.ChoiceFieldPart.Factory,
			                                                                    org.apache.fop.util.text.HexFieldPart.Factory,
			                                                                    org.apache.fop.util.text.GlyphNameFieldPart.Factory,
			                                                                    org.apache.fop.events.EventFormatter.LookupFieldPartFactory;
	uses org.apache.xmlgraphics.image.loader.spi.ImageConverter;
	provides org.apache.xmlgraphics.image.loader.spi.ImageConverter with org.apache.fop.image.loader.batik.ImageConverterSVG2G2D,
			                                                                org.apache.fop.image.loader.batik.ImageConverterG2D2SVG,
			                                                                org.apache.fop.image.loader.batik.ImageConverterWMF2G2D;
	uses org.apache.xmlgraphics.image.loader.spi.ImageLoaderFactory;
	provides org.apache.xmlgraphics.image.loader.spi.ImageLoaderFactory with org.apache.fop.image.loader.batik.ImageLoaderFactorySVG,
			                                                                    org.apache.fop.image.loader.batik.ImageLoaderFactoryWMF;
	uses org.apache.xmlgraphics.image.loader.spi.ImagePreloader;
	provides org.apache.xmlgraphics.image.loader.spi.ImagePreloader with org.apache.fop.image.loader.batik.PreloaderWMF,
			                                                                org.apache.fop.image.loader.batik.PreloaderSVG;
	uses org.apache.xmlgraphics.image.writer.ImageWriter;
	provides org.apache.xmlgraphics.image.writer.ImageWriter with org.apache.xmlgraphics.image.writer.internal.PNGImageWriter,
			                                                         org.apache.xmlgraphics.image.writer.internal.TIFFImageWriter,
			                                                         org.apache.xmlgraphics.image.writer.imageio.ImageIOPNGImageWriter,
			                                                         org.apache.xmlgraphics.image.writer.imageio.ImageIOTIFFImageWriter,
			                                                         org.apache.xmlgraphics.image.writer.imageio.ImageIOJPEGImageWriter;

}

