package com.sun.faces.facelets.util;

import com.guicedee.guicedinjection.GuiceContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Jacob Hookom
 * @author Roland Huss
 * @author Ales Justin (ales.justin@jboss.org)
 */
public final class Classpath
{
	/**
	 *
	 */
	public Classpath()
	{
		super();
	}

	public static URL[] search(String prefix, String suffix)
			throws IOException
	{
		return search(Thread.currentThread()
		                    .getContextClassLoader(), prefix,
		              suffix, SearchAdvice.AllMatches);
	}

	public static URL[] search(ClassLoader cl, String prefix, String suffix,
	                           SearchAdvice advice)
			throws IOException
	{
		if (suffix.startsWith("."))
		{
			suffix = suffix.substring(1);
		}
		final Set<URL> all = new LinkedHashSet<>();
		GuiceContext.instance()
		            .getScanResult()
		            .getResourcesMatchingPattern(Pattern.compile(".*\\b(" + prefix + ")\\b.*\\b(" + suffix + ")\\b"))
		            .forEach(a -> all.add(cleanURL(a.getURL())));
		return all.toArray(new URL[0]);
	}

	public static URL cleanURL(URL url)
	{
		try
		{
			return new URL(cleanURL(url.toString()));
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String cleanURL(String url)
	{
		if (url.contains("jrt:/"))
		{
			return url.replace("jar:jrt:/", "jrt:/")
			          .replace("!", "");
		}
		return url;
	}

	public static URI cleanURI(URI uri)
	{
		try
		{
			return URI.create(cleanURL(uri.toURL()).toString());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static URL[] search(ClassLoader cl, String prefix, String suffix)
			throws IOException
	{
		return search(cl, prefix, suffix, SearchAdvice.AllMatches);
	}

	public enum SearchAdvice
	{
		FirstMatchOnly,
		AllMatches
	}

}
