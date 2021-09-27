package com.guicedee.services.hibernate;

import com.guicedee.guicedinjection.interfaces.IPathContentsRejectListScanner;

import java.util.HashSet;
import java.util.Set;

public class GuiceInjectionMetaInfScannerExclusions
		implements IPathContentsRejectListScanner
{
	@Override
	public Set<String> searchFor()
	{
		Set<String> strings = new HashSet<>();
		strings.add("META-INF/services");
		strings.add("META-INF/maven");
		strings.add("META-INF/versions");
		return strings;
	}
}
