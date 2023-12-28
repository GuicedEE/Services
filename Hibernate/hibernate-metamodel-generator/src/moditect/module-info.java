module org.hibernate.orm.jpamodelgen {

	requires java.compiler;
	requires java.xml;
	requires java.sql;
	requires jakarta.xml.bind;

	requires java.desktop;

	provides javax.annotation.processing.Processor with org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor;
}
