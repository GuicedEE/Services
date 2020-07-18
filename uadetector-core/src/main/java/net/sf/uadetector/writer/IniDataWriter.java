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
package net.sf.uadetector.writer;

import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.data.BrowserOperatingSystemMappingComparator;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.IdentifiableComparator;
import net.sf.uadetector.internal.data.OrderedPatternComparator;
import net.sf.uadetector.internal.data.domain.*;
import net.sf.uadetector.internal.util.RegularExpressionConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedSet;

/**
 * This utility is intended to transform an instance of {@code Data} into an <i>UAS data</i> conform XML document and
 * allows us to recreate an <code>uas.xml</code>.
 *
 * @author André Rouél
 */

public final class IniDataWriter
{

	private static final String EMPTY = "";

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private IniDataWriter()
	{
		// This class is not intended to create objects from it.
	}

	private static void createBrowser(Browser browser, StringBuilder builder)
	{
		createKeyValuePair(browser, String.valueOf(browser.getType()
		                                                  .getId()), builder);
		createKeyValuePair(browser, browser.getFamilyName(), builder);
		createKeyValuePair(browser, browser.getUrl(), builder);
		createKeyValuePair(browser, browser.getProducer(), builder);
		createKeyValuePair(browser, browser.getProducerUrl(), builder);
		createKeyValuePair(browser, browser.getIcon(), builder);
		createKeyValuePair(browser, browser.getInfoUrl(), builder);
	}

	private static void createBrowserOperatingSystemMappings(Data data, StringBuilder builder)
	{
		List<BrowserOperatingSystemMapping> mappings = new ArrayList<>(
				data.getBrowserToOperatingSystemMappings());
		Collections.sort(mappings, BrowserOperatingSystemMappingComparator.INSTANCE);
		createCategory(Tag.BROWSER_OS, builder);
		createComment("browser_id[] = \"OS id\"", builder);
		for (BrowserOperatingSystemMapping mapping : mappings)
		{
			createKeyValuePair(mapping.getBrowserId(), String.valueOf(mapping.getOperatingSystemId()), builder);
		}
	}

	private static void createBrowserPatterns(Data data, StringBuilder builder)
	{
		List<BrowserPattern> patterns = new ArrayList<>(data.getBrowserPatterns()
		                                                    .size());
		for (Entry<Integer, SortedSet<BrowserPattern>> entry : data.getBrowserPatterns()
		                                                           .entrySet())
		{
			patterns.addAll(entry.getValue());
		}
		Collections.sort(patterns, new OrderedPatternComparator<>());
		createCategory(Tag.BROWSER_REG, builder);
		createComment("browser_reg_id[] = \"Browser regstring\"", builder);
		createComment("browser_reg_id[] = \"Browser id\"", builder);
		for (BrowserPattern pattern : patterns)
		{
			String regex = RegularExpressionConverter.convertPatternToPerlRegex(pattern.getPattern());
			createKeyValuePair(pattern.getPosition(), regex, builder);
			createKeyValuePair(pattern.getPosition(), String.valueOf(pattern.getId()), builder);
		}
	}

	private static void createBrowsers(Data data, StringBuilder builder)
	{
		createCategory(Tag.BROWSER, builder);
		createComment("browser_id[] = \"Browser type\"", builder);
		createComment("browser_id[] = \"Browser Name\"", builder);
		createComment("browser_id[] = \"Browser URL\"", builder);
		createComment("browser_id[] = \"Browser Company\"", builder);
		createComment("browser_id[] = \"Browser Company URL\"", builder);
		createComment("browser_id[] = \"Browser ico\"", builder);
		createComment("browser_id[] = \"Browser info URL\"", builder);
		List<Browser> browsers = new ArrayList<>(data.getBrowsers());
		Collections.sort(browsers, IdentifiableComparator.INSTANCE);
		for (Browser browser : browsers)
		{
			createBrowser(browser, builder);
		}
	}

	private static void createBrowserTypes(Data data, StringBuilder builder)
	{
		createCategory(Tag.BROWSER_TYPE, builder);
		createComment("browser_type_id[] = \"Browser type\"", builder);
		List<BrowserType> browserTypes = new ArrayList<>(data.getBrowserTypes()
		                                                     .values());
		Collections.sort(browserTypes, IdentifiableComparator.INSTANCE);
		for (BrowserType browserType : browserTypes)
		{
			createKeyValuePair(browserType, browserType.getName(), builder);
		}
	}

	private static void createCategory(@javax.validation.constraints.NotNull String category, @javax.validation.constraints.NotNull StringBuilder builder)
	{
		builder.append(Char.SQUARE_BRACKET_OPEN);
		builder.append(category);
		builder.append(Char.SQUARE_BRACKET_CLOSE);
		builder.append(Char.NEWLINE);
	}

	private static void createComment(@javax.validation.constraints.NotNull String comment, @javax.validation.constraints.NotNull StringBuilder builder)
	{
		builder.append(Char.SEMICOLON);
		builder.append(Char.WHITESPACE);
		builder.append(comment);
		builder.append(Char.NEWLINE);
	}

	private static void createDescription(@javax.validation.constraints.NotNull Data data, @javax.validation.constraints.NotNull StringBuilder builder)
	{
		createComment("Data (format ini) for UASparser - http://user-agent-string.info/download/UASparser", builder);
		createComment("Version: " + data.getVersion(), builder);
		createComment("Checksum:", builder);
		createComment("MD5 - http://user-agent-string.info/rpc/get_data.php?format=ini&md5=y", builder);
		createComment("SHA1 - http://user-agent-string.info/rpc/get_data.php?format=ini&sha1=y", builder);
		builder.append(Char.SEMICOLON);
		builder.append(Char.NEWLINE);
	}

	private static void createDevice(Device device, StringBuilder builder)
	{
		createKeyValuePair(device, device.getName(), builder);
		createKeyValuePair(device, device.getIcon(), builder);
		createKeyValuePair(device, device.getInfoUrl(), builder);
	}

	private static void createDevicePatterns(Data data, StringBuilder builder)
	{
		List<DevicePattern> patterns = new ArrayList<>(data.getDevicePatterns()
		                                                   .size());
		for (Entry<Integer, SortedSet<DevicePattern>> entry : data.getDevicePatterns()
		                                                          .entrySet())
		{
			patterns.addAll(entry.getValue());
		}
		Collections.sort(patterns, new OrderedPatternComparator<>());
		createCategory(Tag.DEVICE_REG, builder);
		createComment("device_reg_id[] = \"Device regstring\"", builder);
		createComment("device_reg_id[] = \"Device id\"", builder);
		for (DevicePattern pattern : patterns)
		{
			String regex = RegularExpressionConverter.convertPatternToPerlRegex(pattern.getPattern());
			createKeyValuePair(pattern.getPosition(), regex, builder);
			createKeyValuePair(pattern.getPosition(), String.valueOf(pattern.getId()), builder);
		}
	}

	private static void createDevices(Data data, StringBuilder builder)
	{
		createCategory(Tag.DEVICE, builder);
		createComment("device_id[] = \"Device type\"", builder);
		createComment("device_id[] = \"Device ico\"", builder);
		createComment("device_id[] = \"Device info URL\"", builder);
		List<Device> devices = new ArrayList<>(data.getDevices());
		Collections.sort(devices, IdentifiableComparator.INSTANCE);
		for (Device device : devices)
		{
			createDevice(device, builder);
		}
	}

	private static void createKeyValuePair(@javax.validation.constraints.NotNull Identifiable identifiable, @javax.validation.constraints.NotNull String value,
	                                       @javax.validation.constraints.NotNull StringBuilder builder)
	{
		createKeyValuePair(identifiable.getId(), value, builder);
	}

	private static void createKeyValuePair(@javax.validation.constraints.NotNull int id, @javax.validation.constraints.NotNull String value, @javax.validation.constraints.NotNull StringBuilder builder)
	{
		builder.append(id);
		builder.append(Char.SQUARE_BRACKET_OPEN);
		builder.append(Char.SQUARE_BRACKET_CLOSE);
		builder.append(Char.WHITESPACE);
		builder.append(Char.EQUALS);
		builder.append(Char.WHITESPACE);
		builder.append(Char.QUOTE);
		builder.append(value);
		builder.append(Char.QUOTE);
		builder.append(Char.NEWLINE);
	}

	private static void createOperatingSystem(OperatingSystem operatingSystem, StringBuilder builder)
	{
		createKeyValuePair(operatingSystem, operatingSystem.getFamily(), builder);
		createKeyValuePair(operatingSystem, operatingSystem.getName(), builder);
		createKeyValuePair(operatingSystem, operatingSystem.getUrl(), builder);
		createKeyValuePair(operatingSystem, operatingSystem.getProducer(), builder);
		createKeyValuePair(operatingSystem, operatingSystem.getProducerUrl(), builder);
		createKeyValuePair(operatingSystem, operatingSystem.getIcon(), builder);
	}

	private static void createOperatingSystemPatterns(Data data, StringBuilder builder)
	{
		createCategory(Tag.OS_REG, builder);
		createComment("os_reg_id[] = \"OS regstring\"", builder);
		createComment("os_reg_id[] = \"OS id\"", builder);
		List<OperatingSystemPattern> patterns = new ArrayList<>(data.getOperatingSystemPatterns()
		                                                            .size());
		for (Entry<Integer, SortedSet<OperatingSystemPattern>> entry : data.getOperatingSystemPatterns()
		                                                                   .entrySet())
		{
			patterns.addAll(entry.getValue());
		}
		Collections.sort(patterns, new OrderedPatternComparator<>());

		for (OperatingSystemPattern pattern : patterns)
		{
			String regex = RegularExpressionConverter.convertPatternToPerlRegex(pattern.getPattern());
			createKeyValuePair(pattern.getPosition(), regex, builder);
			createKeyValuePair(pattern.getPosition(), String.valueOf(pattern.getId()), builder);
		}
	}

	private static void createOperatingSystems(Data data, StringBuilder builder)
	{
		createCategory(Tag.OS, builder);
		createComment("os_id[] = \"OS Family\"", builder);
		createComment("os_id[] = \"OS Name\"", builder);
		createComment("os_id[] = \"OS URL\"", builder);
		createComment("os_id[] = \"OS Company\"", builder);
		createComment("os_id[] = \"OS Company URL\"", builder);
		createComment("os_id[] = \"OS ico\"", builder);
		List<OperatingSystem> operatingSystems = new ArrayList<>(data.getOperatingSystems());
		Collections.sort(operatingSystems, IdentifiableComparator.INSTANCE);
		for (OperatingSystem operatingSystem : operatingSystems)
		{
			createOperatingSystem(operatingSystem, builder);
		}
	}

	private static void createRobot(Robot robot, StringBuilder builder)
	{
		createKeyValuePair(robot, robot.getUserAgentString(), builder);
		createKeyValuePair(robot, robot.getFamilyName(), builder);
		createKeyValuePair(robot, robot.getName(), builder);
		createKeyValuePair(robot, EMPTY, builder);
		createKeyValuePair(robot, robot.getProducer(), builder);
		createKeyValuePair(robot, robot.getProducerUrl(), builder);
		createKeyValuePair(robot, robot.getIcon(), builder);
		createKeyValuePair(robot, EMPTY, builder);
		createKeyValuePair(robot, robot.getInfoUrl(), builder);
	}

	private static void createRobots(Data data, StringBuilder builder)
	{
		createCategory(Tag.ROBOTS, builder);
		createComment("bot_id[] = \"bot useragentstring\"", builder);
		createComment("bot_id[] = \"bot Family\"", builder);
		createComment("bot_id[] = \"bot Name\"", builder);
		createComment("bot_id[] = \"bot URL\"", builder);
		createComment("bot_id[] = \"bot Company\"", builder);
		createComment("bot_id[] = \"bot Company URL\"", builder);
		createComment("bot_id[] = \"bot ico\"", builder);
		createComment("bot_id[] = \"bot OS id\"", builder);
		createComment("bot_id[] = \"bot info URL\"", builder);
		for (Robot robot : data.getRobots())
		{
			createRobot(robot, builder);
		}
	}

	/**
	 * Transforms a given {@code Data} instance into XML and writes it to the passed in {@code OutputStream}.
	 *
	 * @param data
	 * 		{@code Data} to transform into XML
	 * @param outputStream
	 * 		output stream to write
	 *
	 * @throws IOException
	 * 		if the given output stream can not be written
	 */
	public static void write(@javax.validation.constraints.NotNull Data data, @javax.validation.constraints.NotNull OutputStream outputStream) throws IOException
	{
		Check.notNull(data, "data");
		Check.notNull(outputStream, "outputStream");

		StringBuilder doc = new StringBuilder(10000);

		// description element
		createDescription(data, doc);

		// data
		createRobots(data, doc);
		createOperatingSystems(data, doc);
		createBrowsers(data, doc);
		createBrowserTypes(data, doc);
		createBrowserPatterns(data, doc);
		createBrowserOperatingSystemMappings(data, doc);
		createOperatingSystemPatterns(data, doc);
		createDevices(data, doc);
		createDevicePatterns(data, doc);

		// write the content to output stream
		outputStream.write(doc.toString()
		                      .getBytes("UTF-8"));
	}

	interface Char
	{
		char EQUALS = '=';
		char NEWLINE = '\n';
		char QUOTE = '"';
		char SEMICOLON = ';';
		char SQUARE_BRACKET_CLOSE = ']';
		char SQUARE_BRACKET_OPEN = '[';
		char WHITESPACE = ' ';
	}

	interface Tag
	{
		String BROWSER = "browser";
		String BROWSER_OS = "browser_os";
		String BROWSER_REG = "browser_reg";
		String BROWSER_TYPE = "browser_type";
		String DEVICE = "device";
		String DEVICE_REG = "device_reg";
		String OS = "os";
		String OS_REG = "os_reg";
		String ROBOTS = "robots";
	}

}
