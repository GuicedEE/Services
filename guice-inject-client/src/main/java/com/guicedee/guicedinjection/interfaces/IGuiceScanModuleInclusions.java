package com.guicedee.guicedinjection.interfaces;

import java.util.Set;

/**
 * Marks JAR files referenced from libraries to be excluded from all scans
 */
@FunctionalInterface
public interface IGuiceScanModuleInclusions<J extends IGuiceScanModuleInclusions<J>>
		extends IDefaultService<J> {
	/**
	 * Excludes the given jars for scanning
	 *
	 * @return A set
	 */
	Set<String> includeModules();

}
