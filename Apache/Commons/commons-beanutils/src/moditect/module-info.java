module org.apache.commons.beanutils {
	exports org.apache.commons.beanutils;

	requires java.xml;
	requires java.desktop;
	requires java.sql;

	requires org.apache.commons.logging;
	requires org.apache.commons.collections4;

	exports org.apache.commons.beanutils.converters;
	exports org.apache.commons.beanutils.expression;
	exports org.apache.commons.beanutils.locale;
	exports org.apache.commons.beanutils.locale.converters;
}
