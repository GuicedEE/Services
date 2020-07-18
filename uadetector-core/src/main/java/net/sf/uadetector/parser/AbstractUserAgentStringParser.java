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
package net.sf.uadetector.parser;

import net.sf.uadetector.*;
import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.*;
import net.sf.uadetector.internal.data.domain.OperatingSystem;

import java.util.Map.Entry;
import java.util.regex.Matcher;

public abstract class AbstractUserAgentStringParser
		implements UserAgentStringParser
{

	/**
	 * The number of capturing groups if nothing matches
	 */
	private static final int ZERO_MATCHING_GROUPS = 0;

	@Override
	public String getDataVersion()
	{
		return getDataStore().getData()
		                     .getVersion();
	}

	/**
	 * Gets the data store of this parser.
	 *
	 * @return data store of this parser
	 */
	protected abstract DataStore getDataStore();

	@Override
	public UserAgent parse(String userAgent)
	{
		UserAgent.Builder builder = new UserAgent.Builder(userAgent);

		// work during the analysis always with the same reference of data
		Data data = getDataStore().getData();

		if (!examineAsRobot(builder, data))
		{
			examineAsBrowser(builder, data);
			examineOperatingSystem(builder, data);
		}
		examineDeviceCategory(builder, data);
		return builder.build();
	}

	/**
	 * Examines the user agent string whether it is a robot.
	 *
	 * @param builder
	 * 		Builder for an user agent information
	 *
	 * @return {@code true} if it is a robot, otherwise {@code false}
	 */
	private static boolean examineAsRobot(UserAgent.Builder builder, Data data)
	{
		boolean isRobot = false;
		VersionNumber version;
		for (Robot robot : data.getRobots())
		{
			if (robot.getUserAgentString()
			         .equals(builder.getUserAgentString()))
			{
				isRobot = true;
				robot.copyTo(builder);

				// try to get the version from the last found group
				version = VersionNumber.parseLastVersionNumber(robot.getName());
				builder.setVersionNumber(version);

				break;
			}
		}
		return isRobot;
	}

	/**
	 * Examines the user agent string whether it is a browser.
	 *
	 * @param builder
	 * 		Builder for an user agent information
	 */
	private static void examineAsBrowser(UserAgent.Builder builder, Data data)
	{
		Matcher matcher;
		VersionNumber version = VersionNumber.UNKNOWN;
		for (Entry<BrowserPattern, Browser> entry : data.getPatternToBrowserMap()
		                                                .entrySet())
		{
			matcher = entry.getKey()
			               .getPattern()
			               .matcher(builder.getUserAgentString());
			if (matcher.find())
			{

				entry.getValue()
				     .copyTo(builder);

				// try to get the browser version from the first subgroup
				if (matcher.groupCount() > ZERO_MATCHING_GROUPS)
				{
					version = VersionNumber.parseVersion(matcher.group(1) != null ? matcher.group(1) : "");
				}
				builder.setVersionNumber(version);

				break;
			}
		}
	}

	/**
	 * Examines the operating system of the user agent string, if not available.
	 *
	 * @param builder
	 * 		Builder for an user agent information
	 */
	private static void examineOperatingSystem(UserAgent.Builder builder, Data data)
	{
		if (net.sf.uadetector.OperatingSystem.EMPTY.equals(builder.getOperatingSystem()))
		{
			for (Entry<OperatingSystemPattern, OperatingSystem> entry : data.getPatternToOperatingSystemMap()
			                                                                .entrySet())
			{
				Matcher matcher = entry.getKey()
				                       .getPattern()
				                       .matcher(builder.getUserAgentString());
				if (matcher.find())
				{
					entry.getValue()
					     .copyTo(builder);
					break;
				}
			}
		}
	}

	/**
	 * Examines the user agent string whether has a specific device category.
	 *
	 * @param builder
	 * 		Builder for an user agent information
	 */
	private static void examineDeviceCategory(UserAgent.Builder builder, Data data)
	{

		// a robot will be classified as 'Other'
		if (UserAgentType.ROBOT == builder.getType())
		{
			DeviceCategory category = findDeviceCategoryByValue(Category.OTHER, data);
			builder.setDeviceCategory(category);
			return;
		}

		// classification depends on matching order
		for (Entry<DevicePattern, Device> entry : data.getPatternToDeviceMap()
		                                              .entrySet())
		{
			Matcher matcher = entry.getKey()
			                       .getPattern()
			                       .matcher(builder.getUserAgentString());
			if (matcher.find())
			{
				Category category = Category.evaluate(entry.getValue()
				                                           .getName());
				DeviceCategory deviceCategory = findDeviceCategoryByValue(category, data);
				builder.setDeviceCategory(deviceCategory);
				return;
			}
		}

		// an unknown user agent type should lead to an unknown device
		if (UserAgentType.UNKNOWN == builder.getType())
		{
			builder.setDeviceCategory(DeviceCategory.EMPTY);
			return;
		}

		// if no pattern is available but the type is Other, Library, Validator or UA Anonymizer
		// than classify it as 'Other'
		if (UserAgentType.OTHER == builder.getType() || UserAgentType.LIBRARY == builder.getType()
		    || UserAgentType.VALIDATOR == builder.getType() || UserAgentType.USERAGENT_ANONYMIZER == builder.getType())
		{
			DeviceCategory category = findDeviceCategoryByValue(Category.OTHER, data);
			builder.setDeviceCategory(category);
			return;
		}

		// if no pattern is available but the type is a mobile or WAP browser than classify it as 'Smartphone'
		if (UserAgentType.MOBILE_BROWSER == builder.getType() || UserAgentType.WAP_BROWSER == builder.getType())
		{
			DeviceCategory category = findDeviceCategoryByValue(Category.SMARTPHONE, data);
			builder.setDeviceCategory(category);
			return;
		}

		DeviceCategory category = findDeviceCategoryByValue(Category.PERSONAL_COMPUTER, data);
		builder.setDeviceCategory(category);
	}

	private static DeviceCategory findDeviceCategoryByValue(@javax.validation.constraints.NotNull Category category, @javax.validation.constraints.NotNull Data data)
	{
		for (Device device : data.getDevices())
		{
			if (category == device.getCategory())
			{
				return new DeviceCategory(category, device.getIcon(), device.getInfoUrl(), device.getName());
			}
		}
		return DeviceCategory.EMPTY;
	}

	@Override
	public void shutdown()
	{
		// nothing to shutdown
	}

}
