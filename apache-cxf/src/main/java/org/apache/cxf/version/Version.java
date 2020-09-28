//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.apache.cxf.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Version {
	//	private static final String VERSION_BASE = "/org/apache/cxf/version/";
	private static String version;
	private static String name;
	private static String fullVersion;
	private static String buildNumber;

	private Version() {
	}

	public static void main(String[] args) {
		new Version().loadProperties();
	}

	private static synchronized void loadProperties() {
		if (version == null) {
			Properties p = new Properties();

			try {
				InputStream ins = Version.class.getResourceAsStream("version.properties");
				Throwable var2 = null;

				try {
					p.load(ins);
				} catch (Throwable var12) {
					var2 = var12;
					throw var12;
				} finally {
					if (ins != null) {
						if (var2 != null) {
							try {
								ins.close();
							} catch (Throwable var11) {
								var2.addSuppressed(var11);
							}
						} else {
							ins.close();
						}
					}

				}
			} catch (Exception var14) {
			}

			version = p.getProperty("product.version", "3.4.0");
			name = p.getProperty("product.name", "Apache CXF");
			buildNumber = p.getProperty("build.number", "<unknown>");
			if (!version.contains("SNAPSHOT") && !version.contains("<unknown>")) {
				fullVersion = name + " " + version;
			} else {
				fullVersion = name + " " + version + "-" + buildNumber;
			}
		}

	}

	private static InputStream getResourceAsStream(String resource) {
		ClassLoader cl = Version.class.getClassLoader();
		InputStream ins = cl.getResourceAsStream(resource);
		if (ins == null && resource.startsWith("/")) {
			ins = cl.getResourceAsStream(resource.substring(1));
		}

		return ins;
	}

	public static String getCurrentVersion() {
		loadProperties();
		return version;
	}

	public static String getName() {
		loadProperties();
		return name;
	}

	public static String getCompleteVersionString() {
		loadProperties();
		return fullVersion;
	}
}
