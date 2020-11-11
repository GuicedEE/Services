module org.apache.fop {

    requires static java.desktop;

    requires static org.apache.commons.logging;
    requires static org.apache.commons.io;

    requires java.xml;

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
            org.apache.fop.fo.pagination.PageProductionException.PageProductionExceptionFactory,
            org.apache.fop.events.ValidationExceptionFactory,
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
            org.apache.fop.render.pdf.extensions.PDFElementMapping,
            org.apache.fop.fo.FOElementMapping,
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
    provides org.apache.fop.fo.FOEventHandler with org.apache.fop.render.rtf.RTFFOEventHandlerMaker,
            org.apache.fop.render.rtf.RTFFOEventHandlerMaker;

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
            org.apache.fop.render.afp.AFPImageHandlerSVG,
            org.apache.fop.render.pdf.PDFImageHandlerGraphics2D,
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
            org.apache.fop.render.intermediate.IFSerializerMaker,
            org.apache.fop.render.pdf.PDFDocumentHandlerMaker,
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
            org.apache.fop.render.print.PrintRendererMaker,
            org.apache.fop.render.txt.TXTRendererMaker,
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
            org.apache.fop.render.afp.AFPSVGHandler,
            org.apache.fop.render.pdf.PDFSVGHandler,
            org.apache.fop.render.ps.PSSVGHandler,
            org.apache.fop.render.java2d.Java2DSVGHandler,
            org.apache.fop.render.pcl.PCLSVGHandler,
            org.apache.fop.render.afp.AFPSVGHandler;

    uses org.apache.fop.util.ContentHandlerFactory;
    provides org.apache.fop.util.ContentHandlerFactory with org.apache.fop.render.afp.extensions.AFPExtensionHandlerFactory,
            org.apache.fop.render.pdf.extensions.PDFExtensionHandlerFactory,
            org.apache.fop.render.ps.extensions.PSExtensionHandlerFactory,
            org.apache.fop.fo.extensions.xmp.XMPContentHandlerFactory,
            org.apache.fop.render.afp.extensions.AFPExtensionHandlerFactory,
            org.apache.fop.render.pdf.extensions.PDFExtensionHandlerFactory,
            org.apache.fop.render.ps.extensions.PSExtensionHandlerFactory,
            org.apache.fop.fo.extensions.xmp.XMPContentHandlerFactory;

    uses org.apache.fop.util.text.AdvancedMessageFormat.Function;
    provides org.apache.fop.util.text.AdvancedMessageFormat.Function with org.apache.fop.fo.FONode.GatherContextInfoFunction,
            org.apache.fop.fo.FONode.GatherContextInfoFunction;

    uses org.apache.fop.util.text.AdvancedMessageFormat.ObjectFormatter;
    provides org.apache.fop.util.text.AdvancedMessageFormat.ObjectFormatter with org.apache.fop.util.text.LocatorFormatter,
            org.apache.fop.util.text.LocatorFormatter;

    uses org.apache.fop.util.text.AdvancedMessageFormat.PartFactory;
    provides org.apache.fop.util.text.AdvancedMessageFormat.PartFactory with org.apache.fop.util.text.IfFieldPart.Factory,
            org.apache.fop.util.text.EqualsFieldPart.Factory,
            org.apache.fop.util.text.ChoiceFieldPart.Factory,
            org.apache.fop.util.text.HexFieldPart.Factory,
            org.apache.fop.util.text.GlyphNameFieldPart.Factory,
            org.apache.fop.events.EventFormatter.LookupFieldPartFactory,
            org.apache.fop.util.text.IfFieldPart.Factory,
            org.apache.fop.util.text.EqualsFieldPart.Factory,
            org.apache.fop.util.text.ChoiceFieldPart.Factory,
            org.apache.fop.util.text.HexFieldPart.Factory,
            org.apache.fop.util.text.GlyphNameFieldPart.Factory,
            org.apache.fop.events.EventFormatter.LookupFieldPartFactory;

    uses org.apache.xmlgraphics.image.loader.spi.ImageConverter;
    provides org.apache.xmlgraphics.image.loader.spi.ImageConverter with org.apache.fop.image.loader.batik.ImageConverterSVG2G2D,
            org.apache.fop.image.loader.batik.ImageConverterG2D2SVG,
            org.apache.fop.image.loader.batik.ImageConverterWMF2G2D,
            org.apache.xmlgraphics.image.loader.impl.ImageConverterBuffered2Rendered,
            org.apache.xmlgraphics.image.loader.impl.ImageConverterG2D2Bitmap,
            org.apache.xmlgraphics.image.loader.impl.ImageConverterBitmap2G2D,
            org.apache.xmlgraphics.image.loader.impl.ImageConverterRendered2PNG,
            org.apache.fop.image.loader.batik.ImageConverterSVG2G2D,
            org.apache.fop.image.loader.batik.ImageConverterG2D2SVG,
            org.apache.fop.image.loader.batik.ImageConverterWMF2G2D;

    uses org.apache.xmlgraphics.image.loader.spi.ImageLoaderFactory;
    provides org.apache.xmlgraphics.image.loader.spi.ImageLoaderFactory with org.apache.fop.image.loader.batik.ImageLoaderFactorySVG,
            org.apache.fop.image.loader.batik.ImageLoaderFactoryWMF,
            org.apache.xmlgraphics.image.loader.impl.imageio.ImageLoaderFactoryImageIO,
            org.apache.xmlgraphics.image.loader.impl.ImageLoaderFactoryRaw,
            org.apache.xmlgraphics.image.loader.impl.ImageLoaderFactoryRawCCITTFax,
            org.apache.xmlgraphics.image.loader.impl.ImageLoaderFactoryEPS,
            org.apache.xmlgraphics.image.loader.impl.ImageLoaderFactoryInternalTIFF,
            org.apache.xmlgraphics.image.loader.impl.ImageLoaderFactoryPNG,
            org.apache.fop.image.loader.batik.ImageLoaderFactorySVG,
            org.apache.fop.image.loader.batik.ImageLoaderFactoryWMF;

    uses org.apache.xmlgraphics.image.loader.spi.ImagePreloader;
    provides org.apache.xmlgraphics.image.loader.spi.ImagePreloader with org.apache.fop.image.loader.batik.PreloaderWMF,
            org.apache.fop.image.loader.batik.PreloaderSVG,
            org.apache.xmlgraphics.image.loader.impl.PreloaderTIFF,
            org.apache.xmlgraphics.image.loader.impl.PreloaderGIF,
            org.apache.xmlgraphics.image.loader.impl.PreloaderJPEG,
            org.apache.xmlgraphics.image.loader.impl.PreloaderBMP,
            org.apache.xmlgraphics.image.loader.impl.PreloaderEMF,
            org.apache.xmlgraphics.image.loader.impl.PreloaderEPS,
            org.apache.xmlgraphics.image.loader.impl.imageio.PreloaderImageIO,
            org.apache.xmlgraphics.image.loader.impl.PreloaderRawPNG,
            org.apache.fop.image.loader.batik.PreloaderWMF,
            org.apache.fop.image.loader.batik.PreloaderSVG;

    uses org.apache.xmlgraphics.image.writer.ImageWriter;
    provides org.apache.xmlgraphics.image.writer.ImageWriter with org.apache.xmlgraphics.image.writer.internal.PNGImageWriter,
            org.apache.xmlgraphics.image.writer.internal.TIFFImageWriter,
            org.apache.xmlgraphics.image.writer.imageio.ImageIOPNGImageWriter,
            org.apache.xmlgraphics.image.writer.imageio.ImageIOTIFFImageWriter,
            org.apache.xmlgraphics.image.writer.imageio.ImageIOJPEGImageWriter;


}
