module io.undertow.parser.generator {
	exports io.undertow.annotationprocessor;

	requires java.compiler;
	requires jdk.unsupported;

	provides javax.annotation.processing.Processor with io.undertow.annotationprocessor.HttpParserAnnotationProcessor;
}
