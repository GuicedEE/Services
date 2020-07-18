module com.github.librepdf.openpdf {
	requires java.desktop;
	requires java.annotation;

	requires static org.bouncycastle.pkix;
	requires static org.bouncycastle.provider;

	requires static com.github.albfernandez.juniversalchardet;
	requires static imageio.tiff;

	requires static fop;

}

