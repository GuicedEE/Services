/*******************************************************************************
 * Copyright 2013 André Rouél
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

import net.sf.uadetector.exception.CanNotOpenStreamException;
import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.FileUtil;
import net.sf.uadetector.internal.util.UrlUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;

final class UpdateOperationWithCacheFileTask
		extends AbstractUpdateOperation
{

	/**
	 * Corresponding default logger of this class
	 */
	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(UpdateOperationWithCacheFileTask.class.toString());

	/**
	 * Message for the log when issues occur during reading of or writing to the cache file.
	 */
	private static final String MSG_CACHE_FILE_ISSUES = "Issues occured during reading of or writing to the cache file: %s";

	/**
	 * Message for the log if the passed resources are the same and an update makes no sense
	 */
	private static final String MSG_SAME_RESOURCES = "The passed URL and file resources are the same. An update was not performed.";
	/**
	 * File to cache read in <em>UAS data</em>
	 */
	private final File cacheFile;
	/**
	 * The data store for instances that implements {@link net.sf.uadetector.internal.data.Data}
	 */
	private final AbstractRefreshableDataStore store;

	public UpdateOperationWithCacheFileTask(@javax.validation.constraints.NotNull AbstractRefreshableDataStore dataStore, @javax.validation.constraints.NotNull File cacheFile)
	{
		super(dataStore);
		Check.notNull(dataStore, "dataStore");
		Check.notNull(cacheFile, "cacheFile");
		store = dataStore;
		this.cacheFile = cacheFile;
	}

	/**
	 * Creates a temporary file near the passed file. The name of the given one will be used and the suffix ".temp" will
	 * be added.
	 *
	 * @param file
	 * 		file in which the entire contents from the given URL can be saved
	 *
	 * @throws IllegalStateException
	 * 		if the file can not be deleted
	 */
	protected static File createTemporaryFile(@javax.validation.constraints.NotNull File file)
	{
		Check.notNull(file, "file");

		File tempFile = new File(file.getParent(), file.getName() + ".temp");

		// remove orphaned temporary file
		deleteFile(tempFile);

		return tempFile;
	}

	/**
	 * Removes the given file.
	 *
	 * @param file
	 * 		a file which should be deleted
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the file can not be deleted
	 */
	protected static void deleteFile(@javax.validation.constraints.NotNull File file)
	{
		Check.notNull(file, "file");
		Check.stateIsTrue(!file.exists() || file.delete(), "Cannot delete file '%s'.", file.getPath());
	}

	/**
	 * Checks if the given file is empty.
	 *
	 * @param file
	 * 		the file that could be empty
	 *
	 * @return {@code true} when the file is accessible and empty otherwise {@code false}
	 *
	 * @throws IllegalStateException
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
			throw new IllegalStateException("The given file could not be read.", e);
		}
	}

	/**
	 * Checks that {@code older} {@link Data} has a lower version number than the {@code newer} one.
	 *
	 * @param older
	 * 		possibly older {@code Data}
	 * @param newer
	 * 		possibly newer {@code Data}
	 *
	 * @return {@code true} if the {@code newer} Data is really newer, otherwise {@code false}
	 */
	protected static boolean isNewerData(@javax.validation.constraints.NotNull Data older, @javax.validation.constraints.NotNull Data newer)
	{
		return newer.getVersion()
		            .compareTo(older.getVersion()) > 0;
	}

	/**
	 * Reads the content from the given {@link URL} and saves it to the passed file.
	 *
	 * @param file
	 * 		file in which the entire contents from the given URL can be saved
	 * @param store
	 * 		a data store for <em>UAS data</em>
	 * 		<p>
	 * 		<p>
	 * 		if any of the passed arguments is {@code null}
	 *
	 * @throws IOException
	 * 		if an I/O error occurs
	 */
	protected static void readAndSave(@javax.validation.constraints.NotNull File file, @javax.validation.constraints.NotNull DataStore store) throws IOException
	{
		Check.notNull(file, "file");
		Check.notNull(store, "store");

		URL url = store.getDataUrl();
		Charset charset = store.getCharset();

		boolean isEqual = url.toExternalForm()
		                     .equals(UrlUtil.toUrl(file)
		                                    .toExternalForm());
		if (!isEqual)
		{

			// check if the data can be read in successfully
			String data = UrlUtil.read(url, charset);
			if (Data.EMPTY.equals(store.getDataReader()
			                           .read(data)))
			{
				throw new IllegalStateException("The read in content can not be transformed to an instance of 'Data'.");
			}

			File tempFile = createTemporaryFile(file);
			try (FileOutputStream outputStream = new FileOutputStream(tempFile))
			{
				// write data to temporary file
				outputStream.write(data.getBytes(charset));
				// delete the original file
				deleteFile(file);
			}
			// rename the new file to the original one
			renameFile(tempFile, file);
		}
		else
		{
			LOG.finer(MSG_SAME_RESOURCES);
		}
	}

	/**
	 * Renames the given file {@code from} to the new file {@code to}.
	 *
	 * @param from
	 * 		an existing file
	 * @param to
	 * 		a new file
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the file can not be renamed
	 */
	protected static void renameFile(@javax.validation.constraints.NotNull File from, @javax.validation.constraints.NotNull File to)
	{
		Check.notNull(from, "from");
		Check.stateIsTrue(from.exists(), "Argument 'from' must not be an existing file.");
		Check.notNull(to, "to");
		Check.stateIsTrue(from.renameTo(to), "Renaming file from '%s' to '%s' failed.", from.getAbsolutePath(), to.getAbsolutePath());
	}

	@Override
	public void call()
	{
		readDataIfNewerAvailable();
	}

	private boolean isCacheFileEmpty()
	{
		return isEmpty(cacheFile, store.getCharset());
	}

	private void readDataIfNewerAvailable()
	{
		try
		{
			if (isUpdateAvailable() || isCacheFileEmpty())
			{
				readAndSave(cacheFile, store);
				store.setData(store.getDataReader()
				                   .read(cacheFile.toURI()
				                                  .toURL(), store.getCharset()));
			}
		}
		catch (CanNotOpenStreamException e)
		{
			LOG.log(Level.WARNING, String.format(RefreshableDataStore.MSG_URL_NOT_READABLE, e.getLocalizedMessage()), e);
			readFallbackData();
		}
		catch (RuntimeException e)
		{
			LOG.log(Level.WARNING, RefreshableDataStore.MSG_FAULTY_CONTENT, e);
			readFallbackData();
		}
		catch (IOException e)
		{
			LOG.log(Level.WARNING, String.format(MSG_CACHE_FILE_ISSUES, e.getLocalizedMessage()), e);
			readFallbackData();
		}
	}

	private void readFallbackData()
	{
		LOG.info("Reading fallback data...");
		try
		{
			if (isCacheFileEmpty())
			{
				readAndSave(cacheFile, store.getFallback());
				Data data = store.getDataReader()
				                 .read(cacheFile.toURI()
				                                .toURL(), store.getCharset());
				if (isNewerData(store.getData(), data))
				{
					store.setData(data);
				}
			}
		}
		catch (CanNotOpenStreamException e)
		{
			LOG.log(Level.WARNING, String.format(RefreshableDataStore.MSG_URL_NOT_READABLE, e.getLocalizedMessage()), e);
		}
		catch (RuntimeException e)
		{
			LOG.log(Level.WARNING, RefreshableDataStore.MSG_FAULTY_CONTENT, e);
		}
		catch (IOException e)
		{
			LOG.log(Level.WARNING, String.format(MSG_CACHE_FILE_ISSUES, e.getLocalizedMessage()), e);
		}
	}

}
