package com.commercetools.discoveryfixture;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "kind")
public interface ExtensionType { String getValue(); }
