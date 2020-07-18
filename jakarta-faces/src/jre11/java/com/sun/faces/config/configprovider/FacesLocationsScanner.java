package com.sun.faces.config.configprovider;

import com.guicedee.guicedinjection.interfaces.IPathContentsScanner;

import java.util.HashSet;
import java.util.Set;

public class FacesLocationsScanner
		implements IPathContentsScanner
{
	@Override
	public Set<String> searchFor()
	{
		Set<String> strings = new HashSet<>();
		strings.add("/");
		strings.add("");
		strings.add("META-INF");
		strings.add("WEB-INF");
		strings.add("WEB-INF/classes/META-INF");
		return strings;
	}
}
