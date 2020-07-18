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
package net.sf.uadetector.internal.data;

import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.data.domain.*;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents the detection information of <i>UADetector</i>.
 * <p>
 * An instance of {@code Data} is immutable, their values cannot be changed after creation.
 *
 * @author André Rouél
 */

public class Data
		implements Serializable
{

	/**
	 * An <i>immutable</i> empty {@code Data} object.
	 */
	public static final Data EMPTY = new Data(new HashSet<>(0), new HashMap<>(0),
	                                          new HashMap<>(0), new TreeMap<>(), new HashSet<>(0),
	                                          new HashSet<>(0), new HashMap<>(0),
	                                          new TreeMap<>(), new ArrayList<>(0), new HashSet<>(0),
	                                          new HashMap<>(0), new TreeMap<>(), "");

	private static final long serialVersionUID = 8522012551928801089L;

	@javax.validation.constraints.NotNull
	private final Map<Integer, SortedSet<BrowserPattern>> browserPatterns;

	@javax.validation.constraints.NotNull
	private final Set<Browser> browsers;

	@javax.validation.constraints.NotNull
	private final Map<Integer, SortedSet<DevicePattern>> devicePatterns;

	@javax.validation.constraints.NotNull
	private final Set<Device> devices;

	@javax.validation.constraints.NotNull
	private final SortedMap<DevicePattern, Device> patternToDeviceMap;

	@javax.validation.constraints.NotNull
	private final Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings;

	@javax.validation.constraints.NotNull
	private final Map<Integer, BrowserType> browserTypes;

	@javax.validation.constraints.NotNull
	private final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns;

	@javax.validation.constraints.NotNull
	private final Set<OperatingSystem> operatingSystems;

	@javax.validation.constraints.NotNull
	private final SortedMap<BrowserPattern, Browser> patternToBrowserMap;

	@javax.validation.constraints.NotNull
	private final SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap;

	@javax.validation.constraints.NotNull
	private final List<Robot> robots;

	/**
	 * Version information of the UAS data
	 */
	@javax.validation.constraints.NotNull
	private final String version;

	public Data(@javax.validation.constraints.NotNull Set<Browser> browsers, @javax.validation.constraints.NotNull Map<Integer, SortedSet<BrowserPattern>> browserPatterns,
	            @javax.validation.constraints.NotNull Map<Integer, BrowserType> browserTypes, @javax.validation.constraints.NotNull SortedMap<BrowserPattern, Browser> patternToBrowserMap,
	            @javax.validation.constraints.NotNull Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings,
	            @javax.validation.constraints.NotNull Set<OperatingSystem> operatingSystems,
	            @javax.validation.constraints.NotNull Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns,
	            @javax.validation.constraints.NotNull SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap,
	            @javax.validation.constraints.NotNull List<Robot> robots, @javax.validation.constraints.NotNull Set<Device> devices,
	            @javax.validation.constraints.NotNull Map<Integer, SortedSet<DevicePattern>> devicePatterns,
	            @javax.validation.constraints.NotNull SortedMap<DevicePattern, Device> patternToDeviceMap, @javax.validation.constraints.NotNull String version)
	{
		Check.notNull(browsers, "browsers");
		Check.notNull(browserPatterns, "browserPatterns");
		Check.notNull(browserTypes, "browserTypes");
		Check.notNull(patternToBrowserMap, "patternToBrowserMap");
		Check.notNull(browserToOperatingSystemMappings, "browserToOperatingSystemMap");
		Check.notNull(operatingSystems, "operatingSystems");
		Check.notNull(operatingSystemPatterns, "operatingSystemPatterns");
		Check.notNull(patternToOperatingSystemMap, "patternToOperatingSystemMap");
		Check.notNull(robots, "robots");
		Check.notNull(devices, "devices");
		Check.notNull(devicePatterns, "devicePatterns");
		Check.notNull(patternToDeviceMap, "patternToDeviceMap");
		Check.notNull(version, "version");

		this.browsers = Collections.unmodifiableSet(new HashSet<>(browsers));
		this.browserPatterns = Collections.unmodifiableMap(new HashMap<>(browserPatterns));
		this.browserTypes = Collections.unmodifiableMap(new HashMap<>(Check.notNull(browserTypes, "browserTypes")));
		this.patternToBrowserMap = Collections.unmodifiableSortedMap(new TreeMap<>(patternToBrowserMap));
		this.browserToOperatingSystemMappings = Collections.unmodifiableSet(new HashSet<>(
				browserToOperatingSystemMappings));
		this.operatingSystems = Collections.unmodifiableSet(new HashSet<>(operatingSystems));
		this.operatingSystemPatterns = Collections.unmodifiableMap(new HashMap<>(
				operatingSystemPatterns));
		this.patternToOperatingSystemMap = Collections.unmodifiableSortedMap(new TreeMap<>(
				patternToOperatingSystemMap));
		this.robots = Collections.unmodifiableList(new ArrayList<>(robots));
		this.devices = Collections.unmodifiableSet(new HashSet<>(devices));
		this.devicePatterns = Collections.unmodifiableMap(new HashMap<>(devicePatterns));
		this.patternToDeviceMap = Collections.unmodifiableSortedMap(new TreeMap<>(patternToDeviceMap));
		this.version = Check.notNull(version, "version");
	}

	@javax.validation.constraints.NotNull
	public Map<Integer, SortedSet<BrowserPattern>> getBrowserPatterns()
	{
		return browserPatterns;
	}

	@javax.validation.constraints.NotNull
	public Set<Browser> getBrowsers()
	{
		return browsers;
	}

	@javax.validation.constraints.NotNull
	public Set<BrowserOperatingSystemMapping> getBrowserToOperatingSystemMappings()
	{
		return browserToOperatingSystemMappings;
	}

	@javax.validation.constraints.NotNull
	public Map<Integer, BrowserType> getBrowserTypes()
	{
		return browserTypes;
	}

	@javax.validation.constraints.NotNull
	public Map<Integer, SortedSet<DevicePattern>> getDevicePatterns()
	{
		return devicePatterns;
	}

	@javax.validation.constraints.NotNull
	public Set<Device> getDevices()
	{
		return devices;
	}

	@javax.validation.constraints.NotNull
	public Map<Integer, SortedSet<OperatingSystemPattern>> getOperatingSystemPatterns()
	{
		return operatingSystemPatterns;
	}

	@javax.validation.constraints.NotNull
	public Set<OperatingSystem> getOperatingSystems()
	{
		return operatingSystems;
	}

	@javax.validation.constraints.NotNull
	public SortedMap<BrowserPattern, Browser> getPatternToBrowserMap()
	{
		return patternToBrowserMap;
	}

	@javax.validation.constraints.NotNull
	public SortedMap<DevicePattern, Device> getPatternToDeviceMap()
	{
		return patternToDeviceMap;
	}

	@javax.validation.constraints.NotNull
	public SortedMap<OperatingSystemPattern, OperatingSystem> getPatternToOperatingSystemMap()
	{
		return patternToOperatingSystemMap;
	}

	@javax.validation.constraints.NotNull
	public List<Robot> getRobots()
	{
		return robots;
	}

	/**
	 * Gets the version of the UAS data which are available within this instance.
	 *
	 * @return version of UAS data
	 */
	@javax.validation.constraints.NotNull
	public String getVersion()
	{
		return version;
	}

	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = prime * result + browsers.hashCode();
		result = prime * result + browserPatterns.hashCode();
		result = prime * result + browserTypes.hashCode();
		result = prime * result + patternToBrowserMap.hashCode();
		result = prime * result + browserToOperatingSystemMappings.hashCode();
		result = prime * result + operatingSystems.hashCode();
		result = prime * result + operatingSystemPatterns.hashCode();
		result = prime * result + patternToOperatingSystemMap.hashCode();
		result = prime * result + robots.hashCode();
		result = prime * result + devices.hashCode();
		result = prime * result + devicePatterns.hashCode();
		result = prime * result + patternToDeviceMap.hashCode();
		result = prime * result + version.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Data other = (Data) obj;
		if (!browsers.equals(other.browsers))
		{
			return false;
		}
		if (!browserPatterns.equals(other.browserPatterns))
		{
			return false;
		}
		if (!browserTypes.equals(other.browserTypes))
		{
			return false;
		}
		if (!patternToBrowserMap.equals(other.patternToBrowserMap))
		{
			return false;
		}
		if (!browserToOperatingSystemMappings.equals(other.browserToOperatingSystemMappings))
		{
			return false;
		}
		if (!operatingSystems.equals(other.operatingSystems))
		{
			return false;
		}
		if (!operatingSystemPatterns.equals(other.operatingSystemPatterns))
		{
			return false;
		}
		if (!patternToOperatingSystemMap.equals(other.patternToOperatingSystemMap))
		{
			return false;
		}
		if (!robots.equals(other.robots))
		{
			return false;
		}
		if (!devices.equals(other.devices))
		{
			return false;
		}
		if (!devicePatterns.equals(other.devicePatterns))
		{
			return false;
		}
		if (!patternToDeviceMap.equals(other.patternToDeviceMap))
		{
			return false;
		}
		return version.equals(other.version);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Data [browsers=");
		builder.append(browsers);
		builder.append(", browserPatterns=");
		builder.append(browserPatterns);
		builder.append(", browserTypes=");
		builder.append(browserTypes);
		builder.append(", patternToBrowserMap=");
		builder.append(patternToBrowserMap);
		builder.append(", browserToOperatingSystemMap=");
		builder.append(browserToOperatingSystemMappings);
		builder.append(", operatingSystems=");
		builder.append(operatingSystems);
		builder.append(", operatingSystemPatterns=");
		builder.append(operatingSystemPatterns);
		builder.append(", patternToOperatingSystemMap=");
		builder.append(patternToOperatingSystemMap);
		builder.append(", robots=");
		builder.append(robots);
		builder.append(", devices=");
		builder.append(devices);
		builder.append(", devicePatterns=");
		builder.append(devicePatterns);
		builder.append(", patternToDeviceMap=");
		builder.append(patternToDeviceMap);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

	@javax.validation.constraints.NotNull
	public String toStats()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UAS data stats\n");
		builder.append("----------------------------------------------------------------");
		builder.append('\n');
		builder.append("version:\t\t");
		builder.append(version);
		builder.append('\n');
		builder.append("browser:\t\t");
		builder.append(browsers.size());
		builder.append('\n');
		Map<String, AtomicInteger> browserByType = new HashMap<>();
		for (Browser browser : browsers)
		{
			AtomicInteger counter = browserByType.get(browser.getType()
			                                                 .getName());
			if (counter == null)
			{
				browserByType.put(browser.getType()
				                         .getName(), new AtomicInteger(1));
			}
			else
			{
				counter.incrementAndGet();
			}
		}
		for (Entry<String, AtomicInteger> entry : browserByType.entrySet())
		{
			builder.append('\t');
			builder.append('\t');
			builder.append('\t');
			builder.append(entry.getKey());
			builder.append(":\t");
			builder.append(entry.getValue()
			                    .get());
			builder.append('\n');
		}
		builder.append("browser patterns:\t");
		builder.append(patternToBrowserMap.size());
		builder.append('\n');
		builder.append("operating systems:\t");
		builder.append(operatingSystems.size());
		builder.append('\n');
		builder.append("os patterns:\t\t");
		builder.append(patternToOperatingSystemMap.size());
		builder.append('\n');
		builder.append("robots:\t\t\t");
		builder.append(robots.size());
		builder.append('\n');
		builder.append("devices:\t");
		builder.append(devices.size());
		builder.append('\n');
		builder.append("device patterns:\t");
		builder.append(patternToDeviceMap.size());
		builder.append('\n');
		builder.append("----------------------------------------------------------------");
		return builder.toString();
	}

}
