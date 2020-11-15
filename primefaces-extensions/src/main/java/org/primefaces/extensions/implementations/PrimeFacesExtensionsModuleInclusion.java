package org.primefaces.extensions.implementations;

import com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions;

import java.util.HashSet;
import java.util.Set;

public class PrimeFacesExtensionsModuleInclusion implements IGuiceScanModuleInclusions<PrimeFacesExtensionsModuleInclusion> {
    @Override
    public Set<String> includeModules() {
        Set<String> set = new HashSet<>();
        set.add("org.primefaces.extensions");
        return set;
    }
}
