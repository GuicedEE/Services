package com.guicedee.service.primefaces;

import com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions;

import java.util.HashSet;
import java.util.Set;

public class PrimeFacesScannerInclusion implements IGuiceScanModuleInclusions<PrimeFacesScannerInclusion> {
    @Override
    public Set<String> includeModules() {
        Set<String> moduleScanningAllowed = new HashSet<>();
        moduleScanningAllowed.add("primefaces");
        return moduleScanningAllowed;
    }
}
