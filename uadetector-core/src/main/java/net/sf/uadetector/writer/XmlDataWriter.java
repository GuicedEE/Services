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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

public final class XmlDataWriter
{

	private static final String INDENT_AMOUNT = "4";
	private static final String INDENT_OPTION = "yes";
	private static final String SCHEMA_URL = "http://data.udger.com/uasxmldata_old.dtd";

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private XmlDataWriter()
	{
		// This class is not intended to create objects from it.
	}

	/**
	 * Transforms a given {@code Data} instance into XML and writes it to the passed in {@code OutputStream}.
	 *
	 * @param data
	 * 		{@code Data} to transform into XML
	 * @param outputStream
	 * 		output stream to write
	 *
	 * @throws ParserConfigurationException
	 * 		If a DocumentBuilder cannot be created which satisfies the configuration requested.
	 * @throws TransformerException
	 * 		If an unrecoverable error occurs during the course of the transformation.
	 */
	public static void write(@javax.validation.constraints.NotNull Data data, @javax.validation.constraints.NotNull OutputStream outputStream) throws ParserConfigurationException,
			                                                                                                                                                  TransformerException
	{
		Check.notNull(data, "data");
		Check.notNull(outputStream, "outputStream");

		Document doc = newDocumentBuilder().newDocument();

		// root element
		Element uasdataElement = doc.createElement(Tag.UASDATA);
		doc.appendChild(uasdataElement);

		// description element
		uasdataElement.appendChild(createDescription(data, doc));

		// data element
		Element dataElement = doc.createElement(Tag.DATA);
		uasdataElement.appendChild(dataElement);

		dataElement.appendChild(createRobots(data, doc));
		dataElement.appendChild(createOperatingSystems(data, doc));
		dataElement.appendChild(createBrowsers(data, doc));
		dataElement.appendChild(createBrowserTypes(data, doc));
		dataElement.appendChild(createBrowserPatterns(data, doc));
		dataElement.appendChild(createBrowserOperatingSystemMappings(data, doc));
		dataElement.appendChild(createOperatingSystemPatterns(data, doc));
		dataElement.appendChild(createDevices(data, doc));
		dataElement.appendChild(createDevicePatterns(data, doc));

		// write the content to output stream
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(outputStream);
		transform(source, result);
	}

	@javax.validation.constraints.NotNull
	static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		return docFactory.newDocumentBuilder();
	}

	private static Element createDescription(@javax.validation.constraints.NotNull Data data, @javax.validation.constraints.NotNull Document doc)
	{
		Element description = doc.createElement(Tag.DESCRIPTION);
		Element label = doc.createElement(Tag.LABEL);
		description.appendChild(label)
		           .appendChild(
				           doc.createTextNode("Data (format xml) for UASparser - http://user-agent-string.info/download/UASparser"));
		Element version = doc.createElement("version");
		description.appendChild(version)
		           .appendChild(doc.createTextNode(data.getVersion()));
		Element md5Checksum = doc.createElement("checksum");
		md5Checksum.setAttribute(Tag.TYPE, "MD5");
		description.appendChild(md5Checksum)
		           .appendChild(
				           doc.createTextNode("http://user-agent-string.info/rpc/get_data.php?format=xml&md5=y"));
		Element shaChecksum = doc.createElement("checksum");
		shaChecksum.setAttribute(Tag.TYPE, "SHA1");
		description.appendChild(shaChecksum)
		           .appendChild(
				           doc.createTextNode("http://user-agent-string.info/rpc/get_data.php?format=xml&sha1=y"));
		return description;
	}

	private static Element createRobots(Data data, Document doc)
	{
		Element robotsElement = doc.createElement(Tag.ROBOTS);
		for (Robot robot : data.getRobots())
		{
			robotsElement.appendChild(createRobots(robot, doc));
		}
		return robotsElement;
	}

	private static Element createOperatingSystems(Data data, Document doc)
	{
		Element operatingSystemsElement = doc.createElement(Tag.OPERATING_SYSTEMS);
		List<OperatingSystem> operatingSystems = new ArrayList<>(data.getOperatingSystems());
		Collections.sort(operatingSystems, IdentifiableComparator.INSTANCE);
		for (OperatingSystem operatingSystem : operatingSystems)
		{
			operatingSystemsElement.appendChild(createOperatingSystem(operatingSystem, doc));
		}
		return operatingSystemsElement;
	}

	private static Element createBrowsers(Data data, Document doc)
	{
		Element browsersElement = doc.createElement(Tag.BROWSERS);
		List<Browser> browsers = new ArrayList<>(data.getBrowsers());
		Collections.sort(browsers, IdentifiableComparator.INSTANCE);
		for (Browser browser : browsers)
		{
			browsersElement.appendChild(createBrowser(browser, doc));
		}
		return browsersElement;
	}

	private static Element createBrowserTypes(Data data, Document doc)
	{
		Element browserTypesElement = doc.createElement(Tag.BROWSER_TYPES);
		List<BrowserType> browserTypes = new ArrayList<>(data.getBrowserTypes()
		                                                     .values());
		Collections.sort(browserTypes, IdentifiableComparator.INSTANCE);
		for (BrowserType browserType : browserTypes)
		{
			Element t = doc.createElement(Tag.BROWSER_TYPE);
			Element id = doc.createElement(Tag.ID);
			id.appendChild(doc.createTextNode(String.valueOf(browserType.getId())));
			t.appendChild(id);
			Element family = doc.createElement(Tag.TYPE);
			family.appendChild(doc.createTextNode(String.valueOf(browserType.getName())));
			t.appendChild(family);
			browserTypesElement.appendChild(t);
		}
		return browserTypesElement;
	}

	private static Element createBrowserPatterns(Data data, Document doc)
	{
		List<BrowserPattern> patterns = new ArrayList<>(data.getBrowserPatterns()
		                                                    .size());
		for (Entry<Integer, SortedSet<BrowserPattern>> entry : data.getBrowserPatterns()
		                                                           .entrySet())
		{
			patterns.addAll(entry.getValue());
		}
		Collections.sort(patterns, new OrderedPatternComparator<>());

		Element browserTypesElement = doc.createElement(Tag.BROWSERS_REG);
		for (BrowserPattern pattern : patterns)
		{
			Element t = doc.createElement(Tag.BROWSER_REG);
			Element order = doc.createElement(Tag.ORDER);
			order.appendChild(doc.createTextNode(String.valueOf(pattern.getPosition())));
			t.appendChild(order);
			Element id = doc.createElement(Tag.BROWSER_ID);
			id.appendChild(doc.createTextNode(String.valueOf(pattern.getId())));
			t.appendChild(id);
			Element family = doc.createElement(Tag.REGSTRING);
			family.appendChild(doc.createTextNode(RegularExpressionConverter.convertPatternToPerlRegex(pattern.getPattern())));
			t.appendChild(family);
			browserTypesElement.appendChild(t);
		}
		return browserTypesElement;
	}

	private static Element createBrowserOperatingSystemMappings(Data data, Document doc)
	{
		List<BrowserOperatingSystemMapping> mappings = new ArrayList<>(
				data.getBrowserToOperatingSystemMappings());
		Collections.sort(mappings, BrowserOperatingSystemMappingComparator.INSTANCE);

		Element browserTypesElement = doc.createElement(Tag.BROWSERS_OS);
		for (BrowserOperatingSystemMapping mapping : mappings)
		{
			Element t = doc.createElement(Tag.BROWSER_OS);
			Element browserId = doc.createElement(Tag.BROWSER_ID);
			browserId.appendChild(doc.createTextNode(String.valueOf(mapping.getBrowserId())));
			t.appendChild(browserId);
			Element osId = doc.createElement(Tag.OS_ID);
			osId.appendChild(doc.createTextNode(String.valueOf(mapping.getOperatingSystemId())));
			t.appendChild(osId);
			browserTypesElement.appendChild(t);
		}
		return browserTypesElement;
	}

	private static Element createOperatingSystemPatterns(Data data, Document doc)
	{
		List<OperatingSystemPattern> patterns = new ArrayList<>(data.getOperatingSystemPatterns()
		                                                            .size());
		for (Entry<Integer, SortedSet<OperatingSystemPattern>> entry : data.getOperatingSystemPatterns()
		                                                                   .entrySet())
		{
			patterns.addAll(entry.getValue());
		}
		Collections.sort(patterns, new OrderedPatternComparator<>());

		Element browserTypesElement = doc.createElement(Tag.OPERATING_SYSTEMS_REG);
		for (OperatingSystemPattern pattern : patterns)
		{
			Element t = doc.createElement(Tag.OPERATING_SYSTEM_REG);
			Element order = doc.createElement(Tag.ORDER);
			order.appendChild(doc.createTextNode(String.valueOf(pattern.getPosition())));
			t.appendChild(order);
			Element id = doc.createElement(Tag.OS_ID);
			id.appendChild(doc.createTextNode(String.valueOf(pattern.getId())));
			t.appendChild(id);
			Element family = doc.createElement(Tag.REGSTRING);
			family.appendChild(doc.createTextNode(RegularExpressionConverter.convertPatternToPerlRegex(pattern.getPattern())));
			t.appendChild(family);
			browserTypesElement.appendChild(t);
		}
		return browserTypesElement;
	}

	private static Element createDevices(Data data, Document doc)
	{
		Element devicesElement = doc.createElement(Tag.DEVICES);
		List<Device> devices = new ArrayList<>(data.getDevices());
		Collections.sort(devices, IdentifiableComparator.INSTANCE);
		for (Device device : devices)
		{
			devicesElement.appendChild(createDevice(device, doc));
		}
		return devicesElement;
	}

	private static Element createDevicePatterns(Data data, Document doc)
	{
		List<DevicePattern> patterns = new ArrayList<>(data.getDevicePatterns()
		                                                   .size());
		for (Entry<Integer, SortedSet<DevicePattern>> entry : data.getDevicePatterns()
		                                                          .entrySet())
		{
			patterns.addAll(entry.getValue());
		}
		Collections.sort(patterns, new OrderedPatternComparator<>());

		Element deviceTypesElement = doc.createElement(Tag.DEVICES_REG);
		for (DevicePattern pattern : patterns)
		{
			Element t = doc.createElement(Tag.DEVICE_REG);
			Element order = doc.createElement(Tag.ORDER);
			order.appendChild(doc.createTextNode(String.valueOf(pattern.getPosition())));
			t.appendChild(order);
			Element id = doc.createElement(Tag.DEVICE_ID);
			id.appendChild(doc.createTextNode(String.valueOf(pattern.getId())));
			t.appendChild(id);
			Element family = doc.createElement(Tag.REGSTRING);
			family.appendChild(doc.createTextNode(RegularExpressionConverter.convertPatternToPerlRegex(pattern.getPattern())));
			t.appendChild(family);
			deviceTypesElement.appendChild(t);
		}
		return deviceTypesElement;
	}

	static void transform(@javax.validation.constraints.NotNull Source xmlInput, @javax.validation.constraints.NotNull Result xmlOutput) throws TransformerException
	{
		Check.notNull(xmlInput, "xmlInput");
		Check.notNull(xmlOutput, "xmlOutput");

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, INDENT_OPTION);
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, SCHEMA_URL);
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", INDENT_AMOUNT);
		transformer.transform(xmlInput, xmlOutput);
	}

	private static Element createRobots(Robot robot, Document doc)
	{
		Element r = doc.createElement(Tag.ROBOT);
		Element id = doc.createElement(Tag.ID);
		id.appendChild(doc.createTextNode(String.valueOf(robot.getId())));
		r.appendChild(id);
		Element useragent = doc.createElement(Tag.USERAGENT);
		useragent.appendChild(doc.createCDATASection(robot.getUserAgentString()));
		r.appendChild(useragent);
		Element family = doc.createElement(Tag.FAMILY);
		family.appendChild(doc.createTextNode(robot.getFamilyName()));
		r.appendChild(family);
		Element name = doc.createElement(Tag.NAME);
		name.appendChild(doc.createTextNode(robot.getName()));
		r.appendChild(name);
		Element company = doc.createElement(Tag.COMPANY);
		company.appendChild(doc.createCDATASection(robot.getProducer()));
		r.appendChild(company);
		Element companyUrl = doc.createElement(Tag.URL_COMPANY);
		companyUrl.appendChild(doc.createCDATASection(robot.getProducerUrl()));
		r.appendChild(companyUrl);
		Element icon = doc.createElement(Tag.ICON);
		icon.appendChild(doc.createTextNode(robot.getIcon()));
		r.appendChild(icon);
		Element botInfoUrl = doc.createElement(Tag.BOT_INFO_URL);
		botInfoUrl.appendChild(doc.createTextNode(robot.getInfoUrl()));
		r.appendChild(botInfoUrl);
		return r;
	}

	private static Element createOperatingSystem(OperatingSystem operatingSystem, Document doc)
	{
		Element os = doc.createElement(Tag.OS);
		Element id = doc.createElement("id");
		id.appendChild(doc.createTextNode(String.valueOf(operatingSystem.getId())));
		os.appendChild(id);
		Element family = doc.createElement(Tag.FAMILY);
		family.appendChild(doc.createTextNode(operatingSystem.getFamily()));
		os.appendChild(family);
		Element name = doc.createElement(Tag.NAME);
		name.appendChild(doc.createTextNode(operatingSystem.getName()));
		os.appendChild(name);
		Element url = doc.createElement(Tag.URL);
		url.appendChild(doc.createCDATASection(operatingSystem.getUrl()));
		os.appendChild(url);
		Element company = doc.createElement(Tag.COMPANY);
		company.appendChild(doc.createCDATASection(operatingSystem.getProducer()));
		os.appendChild(company);
		Element companyUrl = doc.createElement(Tag.URL_COMPANY);
		companyUrl.appendChild(doc.createCDATASection(operatingSystem.getProducerUrl()));
		os.appendChild(companyUrl);
		Element icon = doc.createElement(Tag.ICON);
		icon.appendChild(doc.createTextNode(operatingSystem.getIcon()));
		os.appendChild(icon);
		Element botInfoUrl = doc.createElement(Tag.OS_INFO_URL);
		botInfoUrl.appendChild(doc.createTextNode(operatingSystem.getInfoUrl()));
		os.appendChild(botInfoUrl);
		return os;
	}

	private static Element createBrowser(Browser browser, Document doc)
	{
		Element b = doc.createElement(Tag.BROWSER);
		Element id = doc.createElement(Tag.ID);
		id.appendChild(doc.createTextNode(String.valueOf(browser.getId())));
		b.appendChild(id);
		Element family = doc.createElement(Tag.TYPE);
		family.appendChild(doc.createTextNode(String.valueOf(browser.getType()
		                                                            .getId())));
		b.appendChild(family);
		Element name = doc.createElement(Tag.NAME);
		name.appendChild(doc.createTextNode(browser.getFamilyName()));
		b.appendChild(name);
		Element url = doc.createElement(Tag.URL);
		url.appendChild(doc.createCDATASection(browser.getUrl()));
		b.appendChild(url);
		Element company = doc.createElement(Tag.COMPANY);
		company.appendChild(doc.createCDATASection(browser.getProducer()));
		b.appendChild(company);
		Element companyUrl = doc.createElement(Tag.URL_COMPANY);
		companyUrl.appendChild(doc.createCDATASection(browser.getProducerUrl()));
		b.appendChild(companyUrl);
		Element icon = doc.createElement(Tag.ICON);
		icon.appendChild(doc.createTextNode(browser.getIcon()));
		b.appendChild(icon);
		Element botInfoUrl = doc.createElement(Tag.BROWSER_INFO_URL);
		botInfoUrl.appendChild(doc.createTextNode(browser.getInfoUrl()));
		b.appendChild(botInfoUrl);
		return b;
	}

	private static Element createDevice(Device device, Document doc)
	{
		Element b = doc.createElement(Tag.DEVICE);
		Element id = doc.createElement(Tag.ID);
		id.appendChild(doc.createTextNode(String.valueOf(device.getId())));
		b.appendChild(id);
		Element name = doc.createElement(Tag.NAME);
		name.appendChild(doc.createTextNode(device.getName()));
		b.appendChild(name);
		Element icon = doc.createElement(Tag.ICON);
		icon.appendChild(doc.createTextNode(device.getIcon()));
		b.appendChild(icon);
		Element botInfoUrl = doc.createElement(Tag.DEVICE_INFO_URL);
		botInfoUrl.appendChild(doc.createTextNode(device.getInfoUrl()));
		b.appendChild(botInfoUrl);
		return b;
	}

	interface Tag
	{
		String BOT_INFO_URL = "bot_info_url";
		String BROWSER = "browser";
		String BROWSER_ID = "browser_id";
		String BROWSER_INFO_URL = "browser_info_url";
		String BROWSER_OS = "browser_os";
		String BROWSER_REG = "browser_reg";
		String BROWSER_TYPE = "browser_type";
		String BROWSER_TYPES = "browser_types";
		String BROWSERS = "browsers";
		String BROWSERS_OS = "browsers_os";
		String BROWSERS_REG = "browsers_reg";
		String COMPANY = "company";
		String DATA = "data";
		String DESCRIPTION = "description";
		String DEVICE = "device";
		String DEVICE_ID = "device_id";
		String DEVICE_INFO_URL = "device_info_url";
		String DEVICE_REG = "device_reg";
		String DEVICES = "devices";
		String DEVICES_REG = "devices_reg";
		String FAMILY = "family";
		String ICON = "icon";
		String ID = "id";
		String LABEL = "label";
		String NAME = "name";
		String OPERATING_SYSTEM_REG = "operating_system_reg";
		String OPERATING_SYSTEMS = "operating_systems";
		String OPERATING_SYSTEMS_REG = "operating_systems_reg";
		String ORDER = "order";
		String OS = "os";
		String OS_ID = "os_id";
		String OS_INFO_URL = "os_info_url";
		String REGSTRING = "regstring";
		String ROBOT = "robot";
		String ROBOTS = "robots";
		String TYPE = "type";
		String UASDATA = "uasdata";
		String URL = "url";
		String URL_COMPANY = "url_company";
		String USERAGENT = "useragent";
	}

}
