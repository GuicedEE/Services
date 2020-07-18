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
package net.sf.uadetector.internal.data;

import net.sf.uadetector.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.data.domain.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 * This class is intended to create instances of {@code Data}.
 *
 * @author André Rouél
 */

public class DataBuilder
{

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(DataBuilder.class.toString());
	private static final OrderedPatternComparator<BrowserPattern> BROWSER_PATTERN_COMPARATOR = new OrderedPatternComparator<>();
	private static final OrderedPatternComparator<DevicePattern> DEVICE_PATTERN_COMPARATOR = new OrderedPatternComparator<>();
	private static final OrderedPatternComparator<OperatingSystemPattern> OS_PATTERN_COMPARATOR = new OrderedPatternComparator<>();
	@javax.validation.constraints.NotNull
	private final Map<Integer, BrowserType> browserTypes = new HashMap<>();
	@javax.validation.constraints.NotNull
	private final Map<Integer, SortedSet<BrowserPattern>> browserPatterns = new HashMap<>();
	@javax.validation.constraints.NotNull
	private final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = new HashMap<>();
	@javax.validation.constraints.NotNull
	private final Map<Integer, Browser.Builder> browserBuilders = new HashMap<>();
	@javax.validation.constraints.NotNull
	private final Set<Browser> browsers = new HashSet<>();
	@javax.validation.constraints.NotNull
	private final Set<Device> devices = new HashSet<>();
	@javax.validation.constraints.NotNull
	private final Map<Integer, Device.Builder> deviceBuilders = new HashMap<>();
	@javax.validation.constraints.NotNull
	private final Map<Integer, SortedSet<DevicePattern>> devicePatterns = new HashMap<>();
	@javax.validation.constraints.NotNull
	private final Map<Integer, OperatingSystem.Builder> operatingSystemBuilders = new HashMap<>();
	@javax.validation.constraints.NotNull
	private final Set<OperatingSystem> operatingSystems = new HashSet<>();
	@javax.validation.constraints.NotNull
	private final List<Robot> robots = new ArrayList<>();
	@javax.validation.constraints.NotNull
	private final Set<BrowserOperatingSystemMapping> browserToOperatingSystemMap = new HashSet<>();
	private String version;

	public DataBuilder appendBrowser(@javax.validation.constraints.NotNull Browser browser)
	{
		Check.notNull(browser, "browser");

		browsers.add(browser);
		return this;
	}

	/**
	 * Appends a copy of the given {@code Browser.Builder} to the internal data structure.
	 *
	 * @param browserBuilder
	 * 		{@code Browser.Builder} to be copied and appended
	 *
	 * @return this {@code Builder}, for chaining
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the ID of the given builder is invalid
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if a builder with the same ID already exists
	 */
	@javax.validation.constraints.NotNull
	public DataBuilder appendBrowserBuilder(@javax.validation.constraints.NotNull Browser.Builder browserBuilder)
	{
		Check.notNull(browserBuilder, "browserBuilder");
		Check.notNegative(browserBuilder.getId(), "browserBuilder.getId()");
		if (browserBuilder.getType() == null && browserBuilder.getTypeId() < 0)
		{
			throw new IllegalStateOfArgumentException("A Type or Type-ID of argument 'browserBuilder' must be set.");
		}
		if (browserBuilders.containsKey(browserBuilder.getId()))
		{
			throw new IllegalStateOfArgumentException("The browser builder '" + browserBuilder.getProducer() + " "
			                                          + browserBuilder.getFamily() + "' is already in the map.");
		}

		Browser.Builder builder = browserBuilder.copy();
		browserBuilders.put(builder.getId(), builder);
		return this;
	}

	@javax.validation.constraints.NotNull
	public DataBuilder appendBrowserOperatingSystemMapping(@javax.validation.constraints.NotNull BrowserOperatingSystemMapping browserOsMapping)
	{
		Check.notNull(browserOsMapping, "browserOsMapping");

		browserToOperatingSystemMap.add(browserOsMapping);
		return this;
	}

	/**
	 * Appends a browser pattern to the map of pattern sorted by ID.
	 *
	 * @param pattern
	 * 		a pattern for a browser
	 *
	 * @return itself
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 */
	@javax.validation.constraints.NotNull
	public DataBuilder appendBrowserPattern(@javax.validation.constraints.NotNull BrowserPattern pattern)
	{
		Check.notNull(pattern, "pattern");
		if (!browserPatterns.containsKey(pattern.getId()))
		{
			browserPatterns.put(pattern.getId(), new TreeSet<>(BROWSER_PATTERN_COMPARATOR));
		}

		browserPatterns.get(pattern.getId())
		               .add(pattern);
		return this;
	}

	@javax.validation.constraints.NotNull
	public DataBuilder appendBrowserType(@javax.validation.constraints.NotNull BrowserType type)
	{
		Check.notNull(type, "type");

		browserTypes.put(type.getId(), type);
		return this;
	}

	public DataBuilder appendDevice(@javax.validation.constraints.NotNull Device device)
	{
		Check.notNull(device, "device");

		devices.add(device);
		return this;
	}

	/**
	 * Appends a copy of the given {@code Device.Builder} to the internal data structure.
	 *
	 * @param deviceBuilder
	 * 		{@code Device.Builder} to be copied and appended
	 *
	 * @return this {@code Builder}, for chaining
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the ID of the given builder is invalid
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if a builder with the same ID already exists
	 */
	@javax.validation.constraints.NotNull
	public DataBuilder appendDeviceBuilder(@javax.validation.constraints.NotNull Device.Builder deviceBuilder)
	{
		Check.notNull(deviceBuilder, "deviceBuilder");
		Check.notNegative(deviceBuilder.getId(), "deviceBuilder.getId()");
		if (deviceBuilders.containsKey(deviceBuilder.getId()))
		{
			throw new IllegalStateOfArgumentException("The device builder '" + deviceBuilder.getName() + "' is already in the map.");
		}

		Device.Builder builder = deviceBuilder.copy();
		deviceBuilders.put(builder.getId(), builder);
		return this;
	}

	/**
	 * Appends a device pattern to the map of pattern sorted by ID.
	 *
	 * @param pattern
	 * 		a pattern for a device
	 *
	 * @return itself
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 */
	@javax.validation.constraints.NotNull
	public DataBuilder appendDevicePattern(@javax.validation.constraints.NotNull DevicePattern pattern)
	{
		Check.notNull(pattern, "pattern");
		if (!devicePatterns.containsKey(pattern.getId()))
		{
			devicePatterns.put(pattern.getId(), new TreeSet<>(DEVICE_PATTERN_COMPARATOR));
		}

		devicePatterns.get(pattern.getId())
		              .add(pattern);
		return this;
	}

	@javax.validation.constraints.NotNull
	public DataBuilder appendOperatingSystem(@javax.validation.constraints.NotNull OperatingSystem operatingSystem)
	{
		Check.notNull(operatingSystem, "operatingSystem");

		operatingSystems.add(operatingSystem);
		return this;
	}

	/**
	 * Appends a copy of the given {@code OperatingSystem.Builder} to the internal data structure.
	 *
	 * @param operatingSystemBuilder
	 * 		{@code OperatingSystem.Builder} to be copied and appended
	 *
	 * @return this {@code Builder}, for chaining
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
	 * 		if the ID of the given builder is negative
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if a builder with the same ID already exists
	 */
	@javax.validation.constraints.NotNull
	public DataBuilder appendOperatingSystemBuilder(@javax.validation.constraints.NotNull OperatingSystem.Builder operatingSystemBuilder)
	{
		Check.notNull(operatingSystemBuilder, "operatingSystemBuilder");
		Check.notNegative(operatingSystemBuilder.getId(), "operatingSystemBuilder.getId()");
		Check.stateIsTrue(!operatingSystemBuilders.containsKey(operatingSystemBuilder.getId()),
		                  "Operating system builder with ID '%s' already exists.", operatingSystemBuilder.getId() + "");

		OperatingSystem.Builder builder = operatingSystemBuilder.copy();
		operatingSystemBuilders.put(builder.getId(), builder);
		return this;
	}

	/**
	 * Appends an operating system pattern to the map of pattern sorted by ID.
	 *
	 * @param pattern
	 * 		a pattern for a browser
	 *
	 * @return itself
	 * 		<p>
	 * 		<p>
	 * 		if the pattern is {@code null}
	 */
	@javax.validation.constraints.NotNull
	public DataBuilder appendOperatingSystemPattern(@javax.validation.constraints.NotNull OperatingSystemPattern pattern)
	{
		Check.notNull(pattern, "pattern");

		if (!operatingSystemPatterns.containsKey(pattern.getId()))
		{
			operatingSystemPatterns.put(pattern.getId(), new TreeSet<>(OS_PATTERN_COMPARATOR));
		}

		operatingSystemPatterns.get(pattern.getId())
		                       .add(pattern);
		return this;
	}

	@javax.validation.constraints.NotNull
	public DataBuilder appendRobot(@javax.validation.constraints.NotNull Robot robot)
	{
		Check.notNull(robot, "robot");

		robots.add(robot);
		return this;
	}

	@javax.validation.constraints.NotNull
	public Data build()
	{
		addTypeToBrowser(browserBuilders, browserTypes);
		addPatternToBrowser(browserBuilders, browserPatterns);
		addPatternToOperatingSystem(operatingSystemBuilders, operatingSystemPatterns);
		addPatternToDevice(deviceBuilders, devicePatterns);

		Map<Integer, OperatingSystem> systems = buildOperatingSystems(operatingSystemBuilders);
		addOperatingSystemToBrowser(browserBuilders, systems, convertBrowserOsMapping(browserToOperatingSystemMap));

		Set<OperatingSystem> osSet = convertOperatingSystems(systems);
		osSet.addAll(operatingSystems);

		Set<Browser> browserSet = buildBrowsers(browserBuilders);
		browserSet.addAll(browsers);

		Set<Device> deviceSet = buildDevices(deviceBuilders);
		deviceSet.addAll(devices);

		SortedMap<BrowserPattern, Browser> patternToBrowserMap = buildPatternToBrowserMap(browserSet);
		SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap = buildPatternToOperatingSystemMap(osSet);
		SortedMap<DevicePattern, Device> patternToDeviceMap = buildPatternToDeviceMap(deviceSet);

		return new Data(browserSet, browserPatterns, browserTypes, patternToBrowserMap, browserToOperatingSystemMap, osSet,
		                operatingSystemPatterns, patternToOperatingSystemMap, robots, deviceSet, devicePatterns, patternToDeviceMap, version);
	}

	private static void addTypeToBrowser(Map<Integer, Browser.Builder> builders, Map<Integer, BrowserType> types)
	{
		int typeId;
		for (Map.Entry<Integer, Browser.Builder> entry : builders.entrySet())
		{
			typeId = entry.getValue()
			              .getTypeId();
			if (types.containsKey(typeId))
			{
				entry.getValue()
				     .setType(types.get(typeId));
			}
			else
			{
				LOG.warning("No type available for '" + entry.getValue()
				                                             .getProducer() + " " + entry.getValue()
				                                                                         .getFamily() + "'.");
			}
		}
	}

	private static void addPatternToBrowser(Map<Integer, Browser.Builder> builders,
	                                        Map<Integer, SortedSet<BrowserPattern>> patterns)
	{
		for (Map.Entry<Integer, Browser.Builder> entry : builders.entrySet())
		{
			if (patterns.containsKey(entry.getKey()))
			{
				entry.getValue()
				     .setPatterns(patterns.get(entry.getKey()));
			}
			else
			{
				LOG.warning("No pattern available for '" + entry.getValue()
				                                                .getProducer() + " " + entry.getValue()
				                                                                            .getFamily() + "'.");
			}
		}
	}

	private static void addPatternToOperatingSystem(Map<Integer, OperatingSystem.Builder> builders,
	                                                Map<Integer, SortedSet<OperatingSystemPattern>> patterns)
	{
		for (Map.Entry<Integer, OperatingSystem.Builder> entry : builders.entrySet())
		{
			SortedSet<OperatingSystemPattern> patternSet = patterns.get(entry.getKey());
			if (patternSet != null)
			{
				entry.getValue()
				     .addPatterns(patternSet);
			}
			else
			{
				LOG.finer("No patterns for operating system entry (with id '" + entry.getKey() + "') available.");
			}
		}
	}

	private static void addPatternToDevice(Map<Integer, Device.Builder> builders,
	                                       Map<Integer, SortedSet<DevicePattern>> patterns)
	{
		for (Map.Entry<Integer, Device.Builder> entry : builders.entrySet())
		{
			if (patterns.containsKey(entry.getKey()))
			{
				entry.getValue()
				     .setPatterns(patterns.get(entry.getKey()));
			}
			else
			{
				LOG.finer("No pattern available for '" + entry.getValue()
				                                              .getName() + "'.");
			}
		}
	}

	private static Map<Integer, OperatingSystem> buildOperatingSystems(Map<Integer, OperatingSystem.Builder> osBuilders)
	{
		Map<Integer, OperatingSystem> operatingSystems = new HashMap<>();
		for (Map.Entry<Integer, OperatingSystem.Builder> entry : osBuilders.entrySet())
		{
			try
			{
				operatingSystems.put(entry.getKey(), entry.getValue()
				                                          .build());
			}
			catch (Exception e)
			{
				LOG.log(Level.WARNING, "Can not build operating system: " + e.getLocalizedMessage(), e);
			}
		}
		return operatingSystems;
	}

	private static void addOperatingSystemToBrowser(Map<Integer, Browser.Builder> browserBuilders,
	                                                Map<Integer, OperatingSystem> operatingSystems, Map<Integer, Integer> browserOsMap)
	{
		Browser.Builder browserBuilder;
		for (Map.Entry<Integer, Integer> entry : browserOsMap.entrySet())
		{
			if (browserBuilders.containsKey(entry.getKey()))
			{
				browserBuilder = browserBuilders.get(entry.getKey());
				if (operatingSystems.containsKey(entry.getValue()))
				{
					browserBuilder.setOperatingSystem(operatingSystems.get(entry.getValue()));
				}
				else
				{
					LOG.warning("Can not find an operating system with ID '" + entry.getValue() + "' for browser '"
					            + browserBuilder.getProducer() + " " + browserBuilder.getFamily() + "'.");
				}
			}
			else
			{
				LOG.warning("Can not find a browser with ID '" + entry.getKey() + "'.");
			}
		}
	}

	private static Map<Integer, Integer> convertBrowserOsMapping(Set<BrowserOperatingSystemMapping> browserOperatingSystemMappings)
	{
		Map<Integer, Integer> result = new HashMap<>();
		for (BrowserOperatingSystemMapping mapping : browserOperatingSystemMappings)
		{
			result.put(mapping.getBrowserId(), mapping.getOperatingSystemId());
		}
		return result;
	}

	private static Set<OperatingSystem> convertOperatingSystems(Map<Integer, OperatingSystem> operatingSystems)
	{
		Set<OperatingSystem> result = new HashSet<>();
		for (Entry<Integer, OperatingSystem> entry : operatingSystems.entrySet())
		{
			result.add(entry.getValue());
		}
		return result;
	}

	private static Set<Browser> buildBrowsers(Map<Integer, Browser.Builder> browserBuilders)
	{
		Set<Browser> browsers = new HashSet<>();
		for (Map.Entry<Integer, Browser.Builder> entry : browserBuilders.entrySet())
		{
			try
			{
				browsers.add(entry.getValue()
				                  .build());
			}
			catch (Exception e)
			{
				LOG.log(Level.WARNING, "Can not build browser: " + e.getLocalizedMessage(), e);
			}
		}
		return browsers;
	}

	private static Set<Device> buildDevices(Map<Integer, Device.Builder> deviceBuilders)
	{
		Set<Device> devices = new HashSet<>();
		for (Map.Entry<Integer, Device.Builder> entry : deviceBuilders.entrySet())
		{
			try
			{
				devices.add(entry.getValue()
				                 .build());
			}
			catch (Exception e)
			{
				LOG.log(Level.WARNING, "Can not build device '" + entry.getValue()
				                                                       .getName() + "': " + e.getLocalizedMessage(), e);
			}
		}
		return devices;
	}

	private static SortedMap<BrowserPattern, Browser> buildPatternToBrowserMap(Set<Browser> browserSet)
	{
		SortedMap<BrowserPattern, Browser> patternBrowser = new TreeMap<>(BROWSER_PATTERN_COMPARATOR);
		for (Browser browser : browserSet)
		{
			for (BrowserPattern pattern : browser.getPatterns())
			{
				patternBrowser.put(pattern, browser);
			}
		}
		return patternBrowser;
	}

	private static SortedMap<OperatingSystemPattern, OperatingSystem> buildPatternToOperatingSystemMap(Set<OperatingSystem> osSet)
	{
		SortedMap<OperatingSystemPattern, OperatingSystem> map = new TreeMap<>(
				OS_PATTERN_COMPARATOR);
		for (OperatingSystem os : osSet)
		{
			for (OperatingSystemPattern pattern : os.getPatterns())
			{
				map.put(pattern, os);
			}
		}
		return map;
	}

	private static SortedMap<DevicePattern, Device> buildPatternToDeviceMap(Set<Device> devices)
	{
		SortedMap<DevicePattern, Device> patternDevice = new TreeMap<>(DEVICE_PATTERN_COMPARATOR);
		for (Device device : devices)
		{
			for (DevicePattern pattern : device.getPatterns())
			{
				patternDevice.put(pattern, device);
			}
		}
		return patternDevice;
	}

	@javax.validation.constraints.NotNull
	public DataBuilder setVersion(@javax.validation.constraints.NotNull String version)
	{
		Check.notNull(version, "version");

		this.version = version;
		return this;
	}

}
