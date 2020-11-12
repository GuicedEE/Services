package com.guicedee.faces.implementations;

import com.guicedee.guicedinjection.GuiceConfig;
import com.guicedee.guicedinjection.interfaces.IGuiceConfigurator;
import com.guicedee.guicedinjection.interfaces.IGuiceScanJarInclusions;
import com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class JakartaFacesModuleInclusion implements IGuiceScanModuleInclusions<JakartaFacesModuleInclusion>, IGuiceScanJarInclusions<JakartaFacesModuleInclusion> {

    @Override
    public @NotNull Set<String> includeModules() {
        Set<String> moduleScanningAllowed = new HashSet<>();
        moduleScanningAllowed.add("jakarta.faces");
        return moduleScanningAllowed;
    }

    @Override
    public @NotNull Set<String> includeJars() {
        Set<String> moduleScanningAllowed = new HashSet<>();
        moduleScanningAllowed.add("javax.faces-*");
        return moduleScanningAllowed;
    }
}
