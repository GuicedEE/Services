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

import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.util.ExecutorServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Provides a basic implementation to update UAS data in the background when calling {@link #run()}.
 *
 * @author André Rouél
 */
public abstract class AbstractUpdateOperation
		implements UpdateOperation
{

	/**
	 * Defines an empty version string
	 */
	private static final String EMPTY_VERSION = "";

	/**
	 * Corresponding default logger for this class
	 */
	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(AbstractUpdateOperation.class.toString());

	/**
	 * Message for the log when no update is available.<br>
	 * <br>
	 * <b>Message sample</b>: No update available. Current version is '<em>20120301-01</em>'.<br>
	 * <b>First placeholder</b>: current version
	 */
	private static final String MSG_NO_UPDATE_AVAILABLE = "No update available. Current version is '%s'.";

	/**
	 * Message for the log when an online update check is not possible.<br>
	 * <br>
	 * <b>Message sample</b>: Can not check for an updated version. Are you sure you have an established internet
	 * connection?
	 */
	private static final String MSG_NO_UPDATE_CHECK_POSSIBLE = "Can not check for an updated version. Are you sure you have an established internet connection?";

	/**
	 * Message for the log when an exception occur during the update check.<br>
	 * <br>
	 * <b>Message sample</b>: Can not check for an updated version: <em>java.net.ConnectException</em>:
	 * <em>Connection refused</em><br>
	 * <b>First placeholder</b>: class name of exception<br>
	 * <b>Second placeholder</b>: exception message
	 */
	private static final String MSG_NO_UPDATE_CHECK_POSSIBLE__DEBUG = "Can not check for an updated version: %s: %s";

	/**
	 * Message for the log when an update is available.<br>
	 * <br>
	 * <b>Message sample</b>: An update is available. Current version is '<em>20120301-01</em>' and remote version is '
	 * <em>20120401-01</em>'.<br>
	 * <b>First placeholder</b>: current version<br>
	 * <b>Second placeholder</b>: new remote version
	 */
	private static final String MSG_UPDATE_AVAILABLE = "An update is available. Current version is '%s' and remote version is '%s'.";

	/**
	 * Pattern of a typical version of <i>UAS data</i>
	 */
	private static final Pattern VERSION_PATTERN = Pattern.compile("\\d{8}\\-\\d{2}");
	/**
	 * {@link ExecutorService} to run the update operation of the UAS data in background
	 */
	private final ExecutorService executorService = ExecutorServices.createBackgroundExecutor();
	/**
	 * The data store for instances that implements {@link net.sf.uadetector.internal.data.Data}
	 */
	private final RefreshableDataStore store;
	/**
	 * Time of last update check in milliseconds
	 */
	private long lastUpdateCheck = 0;

	public AbstractUpdateOperation(@javax.validation.constraints.NotNull RefreshableDataStore dataStore)
	{
		Check.notNull(dataStore, "dataStore");
		store = dataStore;
	}

	/**
	 * Gets the time of the last update check in milliseconds.
	 *
	 * @return time of the last update check in milliseconds
	 */
	@Override
	public long getLastUpdateCheck()
	{
		return lastUpdateCheck;
	}

	/**
	 * Shuts down the corresponding background executor as soon as possible, but at the latest specified default time.
	 *
	 * @see ExecutorServices#shutdown(ExecutorService)
	 */
	@Override
	public void shutdown()
	{
		ExecutorServices.shutdown(executorService);
	}

	/**
	 * Fetches the current version information over HTTP and compares it with the last version of the most recently
	 * imported data.
	 *
	 * @return {@code true} if an update exists, otherwise {@code false}
	 */
	protected boolean isUpdateAvailable()
	{
		boolean result = false;
		String version = EMPTY_VERSION;
		try
		{
			version = retrieveRemoteVersion(store.getVersionUrl(), store.getCharset());
		}
		catch (IOException e)
		{
			LOG.info(MSG_NO_UPDATE_CHECK_POSSIBLE);
			LOG.log(Level.FINER, String.format(MSG_NO_UPDATE_CHECK_POSSIBLE__DEBUG, e.getClass()
			                                                                         .getName(), e.getLocalizedMessage()), e);
		}
		if (hasUpdate(version, getCurrentVersion()))
		{
			LOG.finer(String.format(MSG_UPDATE_AVAILABLE, getCurrentVersion(), version));
			result = true;
		}
		else
		{
			LOG.finer(String.format(MSG_NO_UPDATE_AVAILABLE, getCurrentVersion()));
		}
		lastUpdateCheck = System.currentTimeMillis();
		return result;
	}

	/**
	 * Reads the current User-Agent data version from <a
	 * href="http://data.udger.com">http://data.udger.com</a>.
	 *
	 * @param url
	 * 		a URL which the version information can be loaded
	 *
	 * @return a version string or {@code null}
	 *
	 * @throws IOException
	 * 		if an I/O exception occurs
	 */

	private static String retrieveRemoteVersion(@javax.validation.constraints.NotNull URL url, @javax.validation.constraints.NotNull Charset charset) throws IOException
	{
		try (InputStream stream = url.openStream();
		     InputStreamReader reader = new InputStreamReader(stream, charset);
		     LineNumberReader lnr = new LineNumberReader(reader))
		{
			return lnr.readLine();
		}
	}

	/**
	 * Checks a given newer version against an older one.
	 *
	 * @param newer
	 * 		possible newer version
	 * @param older
	 * 		possible older version
	 *
	 * @return {@code true} if the first argument is newer than the second argument, otherwise {@code false}
	 */
	static boolean hasUpdate(String newer, String older)
	{
		return (VERSION_PATTERN.matcher(newer)
		                       .matches() && VERSION_PATTERN.matcher(older)
		                                                    .matches()) && newer.compareTo(older) > 0;
	}

	/**
	 * Shortcut to get the current version of the UAS data in the {@link net.sf.uadetector.datastore.DataStore}.
	 *
	 * @return current version of UAS data
	 */
	@javax.validation.constraints.NotNull
	private String getCurrentVersion()
	{
		return store.getData()
		            .getVersion();
	}

	/**
	 * Executes the update at some time in the future (as soon as possible) within a new thread.
	 */
	@Override
	public void run()
	{
		executorService.execute(this::call);
	}


}
