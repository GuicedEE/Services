package com.commercetools.discoveryfixture;

import com.commercetools.api.RegisterSimpleModuleMixin;
import com.commercetools.api.SimpleModuleMixin;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vrap.rmf.base.client.utils.json.modules.ModuleOptions;
import tools.jackson.databind.module.SimpleModule;

@RegisterSimpleModuleMixin
public class ExtensionMixin implements SimpleModuleMixin {
    public static class Value { public String getValue() { return "extension"; } }
    public abstract static class Renamed { @JsonProperty("custom") public abstract String getValue(); }
    public void mixin(SimpleModule module, ModuleOptions options) {
        if ("true".equals(options.getOption("customMixin"))) module.setMixInAnnotation(Value.class, Renamed.class);
    }
}
