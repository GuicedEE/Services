/**
 * Azure Identity requires this JPMS name as well as com.sun.jna. GuicedEE's
 * shaded JNA artifact contains both sets of packages in com.sun.jna; this
 * empty bridge makes the second name resolvable without adding raw JNA jars.
 */
module com.sun.jna.platform {
    requires transitive com.sun.jna;
}
