package com.guicedee.guicedinjection.interfaces;

import java.util.Set;

/**
 * Marks JAR files referenced from libraries to be excluded from all scans
 */
@FunctionalInterface
public interface IGuiceScanModuleExclusions<J extends IGuiceScanModuleExclusions<J>>
		extends IDefaultService<J> {
	/**
	 * Excludes the given jars for scanning
	 *
	 * @return A set
	 */
	Set<String> excludeModules();


}
