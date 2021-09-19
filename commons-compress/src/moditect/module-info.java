module org.apache.commons.compress {
	
	requires static org.objectweb.asm;
	requires static com.github.luben.zstd_jni;
	requires static java.logging;
	requires static java.desktop;
	
	exports org.apache.commons.compress;
	exports org.apache.commons.compress.archivers;
	exports org.apache.commons.compress.archivers.ar;
	exports org.apache.commons.compress.archivers.arj;
	exports org.apache.commons.compress.archivers.cpio;
	exports org.apache.commons.compress.archivers.dump;
	exports org.apache.commons.compress.archivers.jar;
	exports org.apache.commons.compress.archivers.sevenz;
	exports org.apache.commons.compress.archivers.tar;
	exports org.apache.commons.compress.archivers.zip;
	exports org.apache.commons.compress.changes;
	exports org.apache.commons.compress.compressors;
	exports org.apache.commons.compress.compressors.brotli;
	exports org.apache.commons.compress.compressors.bzip2;
	exports org.apache.commons.compress.compressors.deflate;
	exports org.apache.commons.compress.compressors.deflate64;
	exports org.apache.commons.compress.compressors.gzip;
	exports org.apache.commons.compress.compressors.lz4;
	exports org.apache.commons.compress.compressors.lz77support;
	exports org.apache.commons.compress.compressors.lzma;
	exports org.apache.commons.compress.compressors.lzw;
	exports org.apache.commons.compress.compressors.pack200;
	exports org.apache.commons.compress.compressors.snappy;
	exports org.apache.commons.compress.compressors.xz;
	exports org.apache.commons.compress.compressors.z;
	exports org.apache.commons.compress.compressors.zstandard;
	exports org.apache.commons.compress.utils;
}