/*******************************************************************************
 * Copyright 2012 André Rouél
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector.datastore;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.FileUtil;
import net.sf.uadetector.internal.util.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * Implementation of a {@link DataStore} which is able to recover <em>UAS data</em> in XML format from a cache file. If
 * the cache file is empty, the data will be read from the given data URL.<br>
 * <br>
 * You can also update the data of the store at any time if you trigger {@link CachingXmlDataStore#refresh()}.
 *
 * @author André Rouél
 */
public final class CachingXmlDataStore
		extends AbstractRefreshableDataStore
{
	/**
	 * The default temporary-file directory
	 */
	private static final String CACHE_DIR = System.getProperty("java.io.tmpdir");
	/**
	 * Corresponding default logger of this class
	 */
	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(CachingXmlDataStore.class.toString());
	/**
	 * Message for the log if the cache file is filled
	 */
	private static final String MSG_CACHE_FILE_IS_EMPTY = "The cache file is empty. The given UAS data source will be imported.";
	/**
	 * Message for the log if the cache file is empty
	 */
	private static final String MSG_CACHE_FILE_IS_FILLED = "The cache file is filled and will be imported.";
	/**
	 * Message if the cache file contains unexpected data and must be deleted manually
	 */
	private static final String MSG_CACHE_FILE_IS_DAMAGED = "The cache file '%s' is damaged and must be removed manually.";
	/**
	 * Message if the cache file contains unexpected data and has been removed
	 */
	private static final String MSG_CACHE_FILE_IS_DAMAGED_AND_DELETED = "The cache file '%s' is damaged and has been deleted.";
	/**
	 * The prefix string to be used in generating the cache file's name; must be at least three characters long
	 */
	private static final String PREFIX = "uas";
	/**
	 * The suffix string to be used in generating the cache file's name; may be {@code null}, in which case the suffix "
	 * {@code .tmp}" will be used
	 */
	private static final String SUFFIX = ".xml";

	/**
	 * Constructs an {@code CachingXmlDataStore} with the given arguments.
	 *
	 * @param reader
	 * 		data reader to read the given {@code dataUrl}
	 * @param dataUrl
	 * 		URL to <em>UAS data</em>
	 * @param versionUrl
	 * 		URL to version information about the given <em>UAS data</em>
	 * @param charset
	 * 		the character set in which the data should be read
	 * @param cacheFile
	 * 		file with cached <em>UAS data</em> in XML format or an empty file
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 */
	private CachingXmlDataStore(@javax.validation.constraints.NotNull DataReader reader, @javax.validation.constraints.NotNull URL dataUrl, @javax.validation.constraints.NotNull URL versionUrl,
	                            @javax.validation.constraints.NotNull Charset charset, @javax.validation.constraints.NotNull File cacheFile, @javax.validation.constraints.NotNull DataStore fallback)
	{
		super(reader, dataUrl, versionUrl, charset, fallback);
		setUpdateOperation(new UpdateOperationWithCacheFileTask(this, cacheFile));
	}

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The given {@code cacheFile}
	 * can be empty or filled with previously cached data in XML format. The file must be writable otherwise an
	 * exception will be thrown.
	 *
	 * @param dataUrl
	 * 		URL for online version of <em>UAS data</em>
	 * @param versionURL
	 * 		URL for version information of online <em>UAS data</em>
	 * @param fallback
	 * 		<em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 *
	 * @return new instance of {@link CachingXmlDataStore}
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the given cache file can not be read
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if no URL can be resolved to the given given file
	 */
	@javax.validation.constraints.NotNull
	public static CachingXmlDataStore createCachingXmlDataStore(@javax.validation.constraints.NotNull URL dataUrl, @javax.validation.constraints.NotNull URL versionURL, @javax.validation.constraints.NotNull DataStore fallback)
	{
		return createCachingXmlDataStore(findOrCreateCacheFile(), dataUrl, versionURL, DEFAULT_CHARSET,
		                                 fallback);
	}

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The given {@code cacheFile}
	 * can be empty or filled with previously cached data in XML format. The file must be writable otherwise an
	 * exception will be thrown.
	 *
	 * @param cacheFile
	 * 		file with cached <em>UAS data</em> in XML format or empty file
	 * @param dataUrl
	 * 		URL to <em>UAS data</em>
	 * @param versionUrl
	 * 		URL to version information about the given <em>UAS data</em>
	 * @param charset
	 * 		the character set in which the data should be read
	 * @param fallback
	 * 		<em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 *
	 * @return new instance of {@link CachingXmlDataStore}
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 * 		<p>
	 * 		if the given cache file can not be read
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if no URL can be resolved to the given given file
	 */
	@javax.validation.constraints.NotNull
	public static CachingXmlDataStore createCachingXmlDataStore(@javax.validation.constraints.NotNull File cacheFile, @javax.validation.constraints.NotNull URL dataUrl,
	                                                            @javax.validation.constraints.NotNull URL versionUrl, @javax.validation.constraints.NotNull Charset charset, @javax.validation.constraints.NotNull DataStore fallback)
	{
		Check.notNull(cacheFile, "cacheFile");
		Check.notNull(charset, "charset");
		Check.notNull(dataUrl, "dataUrl");
		Check.notNull(fallback, "fallback");
		Check.notNull(versionUrl, "versionUrl");

		DataReader reader = new XmlDataReader();
		DataStore fallbackDataStore = readCacheFileAsFallback(reader, cacheFile, charset, fallback);
		return new CachingXmlDataStore(reader, dataUrl, versionUrl, charset, cacheFile, fallbackDataStore);
	}

	/**
	 * Gets the cache file for <em>UAS data</em> in the default temporary-file directory. If no cache file exists, a new
	 * empty file in the default temporary-file directory will be created, using the default prefix and suffix to
	 * generate its name.
	 *
	 * @return file to cache read in <em>UAS data</em>
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the cache file can not be created
	 */
	@javax.validation.constraints.NotNull
	public static File findOrCreateCacheFile()
	{
		File file = new File(CACHE_DIR, PREFIX + SUFFIX);
		if (!file.exists())
		{
			try
			{
				if (!file.createNewFile())
				{
					throw new IOException("Can't create new file");
				}
			}
			catch (IOException e)
			{
				throw new IllegalStateOfArgumentException("Can not create a cache file.", e);
			}
		}
		return file;
	}

	/**
	 * Tries to read the content of specified cache file and returns them as fallback data store. If the cache file
	 * contains unexpected data the given fallback data store will be returned instead.
	 *
	 * @param reader
	 * 		data reader to read the given {@code dataUrl}
	 * @param cacheFile
	 * 		file with cached <em>UAS data</em> in XML format or empty file
	 * @param charset
	 * 		the character set in which the data should be read
	 * @param fallback
	 * 		<em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 *
	 * @return a fallback data store
	 */
	private static DataStore readCacheFileAsFallback(@javax.validation.constraints.NotNull DataReader reader, @javax.validation.constraints.NotNull File cacheFile,
	                                                 @javax.validation.constraints.NotNull Charset charset, @javax.validation.constraints.NotNull DataStore fallback)
	{
		DataStore fallbackDataStore;
		if (!isEmpty(cacheFile, charset))
		{
			URL cacheFileUrl = UrlUtil.toUrl(cacheFile);
			try
			{
				fallbackDataStore = new CacheFileDataStore(reader.read(cacheFileUrl, charset), reader, cacheFileUrl, charset);
				log.finer(MSG_CACHE_FILE_IS_FILLED);
			}
			catch (RuntimeException e)
			{
				fallbackDataStore = fallback;
				deleteCacheFile(cacheFile);
				log.log(Level.FINEST, "Runtime in fallback", e);
			}
		}
		else
		{
			fallbackDataStore = fallback;
			log.finer(MSG_CACHE_FILE_IS_EMPTY);
		}
		return fallbackDataStore;
	}

	/**
	 * Checks if the given file is empty.
	 *
	 * @param file
	 * 		the file that could be empty
	 *
	 * @return {@code true} when the file is accessible and empty otherwise {@code false}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if an I/O error occurs
	 */
	private static boolean isEmpty(@javax.validation.constraints.NotNull File file, @javax.validation.constraints.NotNull Charset charset)
	{
		try
		{
			return FileUtil.isEmpty(file, charset);
		}
		catch (IOException e)
		{
			throw new IllegalStateOfArgumentException("The given file could not be read.", e);
		}
	}

	/**
	 * Removes the given cache file because it contains damaged content.
	 *
	 * @param cacheFile
	 * 		cache file to delete
	 */
	private static void deleteCacheFile(File cacheFile)
	{
		try
		{
			if (cacheFile.delete())
			{
				log.warning(String.format(MSG_CACHE_FILE_IS_DAMAGED_AND_DELETED, cacheFile.getPath()));
			}
			else
			{
				log.warning(String.format(MSG_CACHE_FILE_IS_DAMAGED, cacheFile.getPath()));
			}
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, String.format(MSG_CACHE_FILE_IS_DAMAGED, cacheFile.getPath()), e);
		}
	}

	/**
	 * @param fallback
	 *
	 * @return
	 *
	 * @deprecated
	 */
	@Deprecated
	public static CachingXmlDataStore createCachingXmlDataStore(@javax.validation.constraints.NotNull DataStore fallback)
	{
		return createCachingXmlDataStore(findOrCreateCacheFile(), fallback);
	}

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The given {@code cacheFile}
	 * can be empty or filled with previously cached data in XML format. The file must be writable otherwise an
	 * exception will be thrown.
	 *
	 * @param cacheFile
	 * 		file with cached <em>UAS data</em> in XML format or empty file
	 * @param fallback
	 * 		<em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 *
	 * @return new instance of {@link CachingXmlDataStore}
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the given cache file can not be read
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if no URL can be resolved to the given given file
	 * @deprecated
	 */
	@javax.validation.constraints.NotNull
	@Deprecated
	public static CachingXmlDataStore createCachingXmlDataStore(@javax.validation.constraints.NotNull File cacheFile, @javax.validation.constraints.NotNull DataStore fallback)
	{
		return createCachingXmlDataStore(cacheFile, UrlUtil.build(DEFAULT_DATA_URL), UrlUtil.build(DEFAULT_VERSION_URL), DEFAULT_CHARSET,
		                                 fallback);
	}

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The file used to cache the
	 * read in <em>UAS data</em> will be called from {@link CachingXmlDataStore#findOrCreateCacheFile()}. This file may
	 * be empty or filled with previously cached data in XML format. The file must be writable otherwise an exception
	 * will be thrown.
	 *
	 * @param dataUrl
	 * 		URL to <em>UAS data</em>
	 * @param versionUrl
	 * 		URL to version information about the given <em>UAS data</em>
	 * @param charset
	 * 		the character set in which the data should be read
	 * @param fallback
	 * 		<em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 *
	 * @return new instance of {@link CachingXmlDataStore}
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the given cache file can not be read
	 */
	@javax.validation.constraints.NotNull
	public static CachingXmlDataStore createCachingXmlDataStore(@javax.validation.constraints.NotNull URL dataUrl, @javax.validation.constraints.NotNull URL versionUrl,
	                                                            @javax.validation.constraints.NotNull Charset charset, @javax.validation.constraints.NotNull DataStore fallback)
	{
		return createCachingXmlDataStore(findOrCreateCacheFile(), dataUrl, versionUrl, charset, fallback);
	}

	/**
	 * Internal data store which will be used to load previously saved <em>UAS data</em> from a cache file.
	 */
	private static class CacheFileDataStore
			extends AbstractDataStore
	{
		protected CacheFileDataStore(Data data, DataReader reader, URL dataUrl, Charset charset)
		{
			super(data, reader, dataUrl, dataUrl, charset);
		}
	}

}
