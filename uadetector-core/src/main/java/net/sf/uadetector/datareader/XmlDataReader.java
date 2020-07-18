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
package net.sf.uadetector.datareader;

import net.sf.uadetector.exception.CanNotOpenStreamException;
import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBuilder;
import net.sf.uadetector.internal.data.XmlDataHandler;
import net.sf.uadetector.internal.util.Closeables;
import net.sf.uadetector.internal.util.UrlUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reader for the XML data for UASparser from <a
 * href="http://user-agent-string.info/">http://user-agent-string.info</a>.<br>
 * <br>
 * This reader is safe when used concurrently by multiple threads.
 *
 * @author André Rouél
 */
public final class XmlDataReader
		implements DataReader
{

	/**
	 * Default character set to read UAS data
	 */
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(XmlDataReader.class.toString());

	/**
	 * Reads the <em>UAS data</em> in XML format based on the given URL.
	 *
	 * @param url
	 * 		{@code URL} to User-Agent informations
	 * @param charset
	 * 		the character set in which the data should be read
	 *
	 * @return read in User-Agent data as {@code Data} instance otherwise {@link Data#EMPTY}
	 * 		<p>
	 * 		<p>
	 * 		if any of the given arguments is {@code null}
	 */
	@Override
	public Data read(@javax.validation.constraints.NotNull URL url, @javax.validation.constraints.NotNull Charset charset)
	{
		Check.notNull(url, "url");
		Check.notNull(charset, "charset");

		Data data = Data.EMPTY;
		try
		{
			data = readXml(UrlUtil.open(url), charset);
		}
		catch (CanNotOpenStreamException e)
		{
			LOG.log(Level.WARNING, e.getLocalizedMessage(), e);
		}

		return data;
	}

	/**
	 * Reads the <em>UAS data</em> in XML format from the given string.
	 *
	 * @param data
	 * 		<em>UAS data</em> as string
	 *
	 * @return read in User-Agent data as {@code Data} instance otherwise {@link Data#EMPTY}
	 * 		<p>
	 * 		<p>
	 * 		if any of the given argument is {@code null}
	 */
	@Override
	public Data read(@javax.validation.constraints.NotNull String data)
	{
		Check.notNull(data, "data");

		return readXml(new ByteArrayInputStream(data.getBytes(DEFAULT_CHARSET)), DEFAULT_CHARSET);
	}

	/**
	 * Reads the <em>UAS data</em> in XML format based on the given URL.<br>
	 * <br>
	 * When during the reading errors occur which lead to a termination of the read operation, the information will be
	 * written to a log. The termination of the read operation will not lead to a program termination and in this case
	 * this method returns {@link Data#EMPTY}.
	 *
	 * @param inputStream
	 * 		an input stream for reading <em>UAS data</em>
	 * @param charset
	 * 		the character set in which the data should be read
	 *
	 * @return read in <em>UAS data</em> as {@code Data} instance
	 * 		<p>
	 * 		<p>
	 * 		if any of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.CanNotOpenStreamException
	 * 		if no stream to the given {@code URL} can be established
	 */
	protected static Data readXml(@javax.validation.constraints.NotNull InputStream inputStream, @javax.validation.constraints.NotNull Charset charset)
	{
		Check.notNull(inputStream, "inputStream");
		Check.notNull(charset, "charset");

		DataBuilder builder = new DataBuilder();
		boolean hasErrors = false;
		try
		{
			XmlParser.parse(inputStream, builder);
		}
		catch (Exception e)
		{
			hasErrors = true;
			LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		finally
		{
			Closeables.closeAndConvert(inputStream, true);
		}

		return hasErrors ? Data.EMPTY : builder.build();
	}

	protected static final class XmlParser
	{

		private static final String MSG_NOT_PARSED_AS_EXPECTED = "The UAS data has not been parsed as expected.";

		private XmlParser()
		{
			// This class is not intended to create objects from it.
		}

		public static void parse(@javax.validation.constraints.NotNull InputStream stream, @javax.validation.constraints.NotNull DataBuilder builder)
				throws ParserConfigurationException, SAXException, IOException
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
			XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(stream, handler);
			validate(handler);
		}

		protected static void validate(@javax.validation.constraints.NotNull XmlDataHandler handler)
		{
			if (handler.hasError())
			{
				throw new IllegalStateException(MSG_NOT_PARSED_AS_EXPECTED);
			}
		}

	}

}
