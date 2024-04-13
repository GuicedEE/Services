package com.guicedee.guicedinjection.interfaces;

public interface IGuiceConfig<J extends IGuiceConfig<J>> {
    /**
     * Enable classpath scanning for service sets loaded via GuiceContext.getLoader()
     * It's a great way to enable testing in jdk 12 where test classes using service loading and jdk no longer reads service loaders from meta-inf/services
     * <p>
     * Try to only use in test to load test modules. otherwise it may be a bad design
     */
    boolean isServiceLoadWithClassPath();

    /**
     * Enable classpath scanning for service sets loaded via GuiceContext.getLoader()
     * It's a great way to enable testing in jdk 12 where test classes using service loading and jdk no longer reads service loaders from meta-inf/services
     * <p>
     * Try to only use in test to load test modules. otherwise it may be a bad design
     *
     * @param serviceLoadWithClassPath Should scanning with classpath instead of SPI be used?
     * @return this
     */
    @SuppressWarnings("unchecked")
    J setServiceLoadWithClassPath(boolean serviceLoadWithClassPath);

    /**
     * If field scanning should be enabled
     *
     * @return mandatory result
     */
    boolean isFieldScanning();

    /**
     * Enables scanning of fields
     *
     * @param fieldScanning If field scanning should happen
     * @return Mandatory field scanning
     */
    @SuppressWarnings("unchecked")
    
    J setFieldScanning(boolean fieldScanning);

    /**
     * Enables scanning of field annotations
     *
     * @return not null
     */
    boolean isAnnotationScanning();

    /**
     * Enables scanning of field annotations
     *
     * @param annotationScanning if the field annotation scanning
     * @return the field annotation scanning
     */
    @SuppressWarnings("unchecked")
    
    J setAnnotationScanning(boolean annotationScanning);

    /**
     * If method info should be kept
     *
     * @return always this
     */
    boolean isMethodInfo();

    /**
     * Sets if method info should be kept
     *
     * @param methodInfo if method information should be collected
     * @return always this
     */
    @SuppressWarnings("unchecked")
    
    J setMethodInfo(boolean methodInfo);

    /**
     * Sets to ignore field visibility
     *
     * @return if field visibility is being used
     */
    boolean isIgnoreFieldVisibility();

    /**
     * Sets to ignore field visibility
     *
     * @param ignoreFieldVisibility if the field should be visible
     * @return always this
     */
    @SuppressWarnings("unchecked")
    
    J setIgnoreFieldVisibility(boolean ignoreFieldVisibility);

    /**
     * Sets to ignore method visibility
     *
     * @return if method is visibility ignored
     */
    boolean isIgnoreMethodVisibility();

    /**
     * Sets to ignore method visibility
     *
     * @param ignoreMethodVisibility the ignore method
     * @return always This
     */
    @SuppressWarnings("unchecked")
    
    J setIgnoreMethodVisibility(boolean ignoreMethodVisibility);

    /**
     * Sets if packages must be white listed.
     * <p>
     * Use META-INF/services/com.guicedee.guiceinjection.scanners.IPackageContentsScanner to register your packages
     *
     * @return if whitelisting is enabled
     */
    boolean isIncludePackages();

    /**
     * Sets if packages must be white listed.
     * * <p>
     * * Use META-INF/services/com.guicedee.guiceinjection.scanners.IPackageContentsScanner to register your packages
     *
     * @param includePackages if packages should be white listed
     * @return Always this
     */
    @SuppressWarnings("unchecked")
    
    J setIncludePackages(boolean includePackages);

    /**
     * Returns the field information included in the scan result
     *
     * @return if field info is included
     */
    boolean isFieldInfo();

    /**
     * Sets if the field info should be in the field result
     *
     * @param fieldInfo if field info should be scanned
     * @return always this object
     */
    @SuppressWarnings("unchecked")
    
    J setFieldInfo(boolean fieldInfo);

    /**
     * Method isVerbose returns the verbose of this GuiceConfig object.
     * <p>
     * Whether or not to log very verbose
     *
     * @return the verbose (type boolean) of this GuiceConfig object.
     */
    boolean isVerbose();

    /**
     * Method setVerbose sets the verbose of this GuiceConfig object.
     * <p>
     * Whether or not to log very verbose
     *
     * @param verbose the verbose of this GuiceConfig object.
     * @return J
     */
    @SuppressWarnings("unchecked")
    
    J setVerbose(boolean verbose);

    /**
     * Method isClasspathScanning returns the classpathScanning of this GuiceConfig object.
     * <p>
     * If classpath scanning is enabled.
     *
     * @return the classpathScanning (type boolean) of this GuiceConfig object.
     */
    boolean isClasspathScanning();

    /**
     * Method setClasspathScanning sets the classpathScanning of this GuiceConfig object.
     * <p>
     * If classpath scanning is enabled.
     *
     * @param classpathScanning the classpathScanning of this GuiceConfig object.
     * @return J
     */
    @SuppressWarnings("unchecked")
    
    J setClasspathScanning(boolean classpathScanning);

    /**
     * Excludes modules and jars from scanning - may and may not make it faster depending on your pc
     *
     * @return is modules/jars are excluded from scans
     */
    boolean isExcludeModulesAndJars();

    /**
     * Excludes modules and jars from scanning - may and may not make it faster depending on your pc
     *
     * @param excludeModulesAndJars to exclude them
     * @return J
     */
    @SuppressWarnings("unchecked")
    
    J setExcludeModulesAndJars(boolean excludeModulesAndJars);

    /**
     * Excludes paths from scanning - excellent for minizing path scanning on web application
     *
     * @return boolean
     */
    boolean isExcludePaths();

    /**
     * Excludes paths from scanning - excellent for minizing path scanning on web application
     *
     * @param excludePaths If the default paths must be automatically excluded
     * @return J
     */
    @SuppressWarnings("unchecked")
    
    J setExcludePaths(boolean excludePaths);

    /**
     * Method isAllowPaths returns the allowed Paths of this GuiceConfig object.
     * <p>
     * Excludes paths from scanning - excellent for minizing path scanning on web application
     *
     * @return the whitelistPaths (type boolean) of this GuiceConfig object.
     */
    boolean isAllowPaths();

    /**
     * Method setAllowPaths sets the allowed Paths of this GuiceConfig object.
     * <p>
     * Excludes paths from scanning - excellent for minizing path scanning on web application
     *
     * @param allowedPaths the allowedPaths of this GuiceConfig object.
     * @return GuiceConfig J
     */
    J setAllowPaths(boolean allowedPaths);

    boolean isIgnoreClassVisibility();

    @SuppressWarnings("unchecked")
    J setIgnoreClassVisibility(boolean ignoreClassVisibility);

    /**
     * Include module/jars from being loaded - uses ModuleInclusions for jdk9 and JarInclusions for jdk8
     *
     * @return
     */
    boolean isIncludeModuleAndJars();

    /**
     * Include module/jars from being loaded - uses ModuleInclusions for jdk9 and JarInclusions for jdk8
     *
     * @param includeModuleAndJars
     * @return
     */
    J setIncludeModuleAndJars(boolean includeModuleAndJars);

    /**
     * Method isPathScanning returns the pathScanning of this GuiceConfig object.
     * <p>
     * If the path should be scanned
     *
     * @return the pathScanning (type boolean) of this GuiceConfig object.
     */
    boolean isPathScanning();

    /**
     * Method setPathScanning sets the pathScanning of this GuiceConfig object.
     * <p>
     * If the path should be scanned
     *
     * @param pathScanning the pathScanning of this GuiceConfig object.
     * @return GuiceConfig J
     */
    J setPathScanning(boolean pathScanning);

    /**
     * Method isExcludeParentModules returns the excludeParentModules of this GuiceConfig object.
     * <p>
     * Property to use when everything is found in the boot module
     *
     * @return the excludeParentModules (type boolean) of this GuiceConfig object.
     */
    @SuppressWarnings("unused")
    boolean isExcludeParentModules();

    /**
     * Method setExcludeParentModules sets the excludeParentModules of this GuiceConfig object.
     * <p>
     * Property to use when everything is found in the boot module
     *
     * @param excludeParentModules the excludeParentModules of this GuiceConfig object.
     * @return GuiceConfig J
     */
    @SuppressWarnings("unused")
    J setExcludeParentModules(boolean excludeParentModules);

    /**
     * Method isRejectPackages returns the excludePackages of this GuiceConfig object.
     * <p>
     * Excludes packages from scanning - excellent for minimizing path scanning on web application
     *
     * @return the excludePackages (type boolean) of this GuiceConfig object.
     */
    boolean isRejectPackages();

    /**
     * Method setExcludePackages sets the excludePackages of this GuiceConfig object.
     * <p>
     * Excludes packages from scanning - excellent for minimizing path scanning on web application
     *
     * @param excludePackages the excludePackages of this GuiceConfig object.
     * @return GuiceConfig J
     */
    J setExcludePackages(boolean excludePackages);
}
