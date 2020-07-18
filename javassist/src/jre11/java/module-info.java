module javassist {
	requires java.instrument;
	requires java.management;
	requires static jdk.attach;
	requires static jdk.jdi;
	requires java.desktop;

	requires static java.sql;
}
