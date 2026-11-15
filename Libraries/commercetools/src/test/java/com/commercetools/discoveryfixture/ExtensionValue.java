package com.commercetools.discoveryfixture;

import io.vrap.rmf.base.client.utils.json.SubType;

@SubType("extension")
public class ExtensionValue implements ExtensionType {
    public String value;
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
