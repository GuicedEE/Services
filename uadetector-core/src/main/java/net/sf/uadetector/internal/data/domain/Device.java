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
package net.sf.uadetector.internal.data.domain;

import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.internal.Check;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;


public final class Device
		implements Identifiable, Serializable
{

	private static final long serialVersionUID = 1L;
	private final int hash;
	@javax.validation.constraints.NotNull
	private final String icon;
	@javax.validation.constraints.NotNull
	private final Category category;

	private final int id;
	@javax.validation.constraints.NotNull
	private final String infoUrl;
	@javax.validation.constraints.NotNull
	private final String name;
	@javax.validation.constraints.NotNull
	private final SortedSet<DevicePattern> patterns;

	public Device(@javax.validation.constraints.NotNull String name,
	              int id, @javax.validation.constraints.NotNull Category category, @javax.validation.constraints.NotNull String icon,
	              @javax.validation.constraints.NotNull String infoUrl, @javax.validation.constraints.NotNull SortedSet<DevicePattern> patterns)
	{
		this.category = category;
		this.icon = Check.notNull(icon, "icon");
		this.id = Check.notNegative(id, "id");
		this.infoUrl = Check.notNull(infoUrl, "infoUrl");
		this.name = Check.notNull(name, "name");
		this.patterns = Collections.unmodifiableSortedSet(new TreeSet<>(Check.notNull(patterns, "patterns")));
		hash = buildHashCode(category, icon, id, infoUrl, name, patterns);
	}

	private static int buildHashCode(@javax.validation.constraints.NotNull Category category, @javax.validation.constraints.NotNull String icon, int id,
	                                 @javax.validation.constraints.NotNull String infoUrl, @javax.validation.constraints.NotNull String name, @javax.validation.constraints.NotNull SortedSet<DevicePattern> patterns)
	{
		int prime = 31;
		int result = 1;
		result = prime * result + category.hashCode();
		result = prime * result + icon.hashCode();
		result = prime * result + id;
		result = prime * result + infoUrl.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + patterns.hashCode();
		return result;
	}

	@javax.validation.constraints.NotNull
	public Category getCategory()
	{
		return category;
	}

	@javax.validation.constraints.NotNull
	public String getIcon()
	{
		return icon;
	}

	@Override

	public int getId()
	{
		return id;
	}

	@javax.validation.constraints.NotNull
	public String getInfoUrl()
	{
		return infoUrl;
	}

	@javax.validation.constraints.NotNull
	public String getName()
	{
		return name;
	}

	@javax.validation.constraints.NotNull
	public SortedSet<DevicePattern> getPatterns()
	{
		return patterns;
	}

	@Override
	public int hashCode()
	{
		return hash;
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
		Device other = (Device) obj;
		if (category != other.category)
		{
			return false;
		}
		if (!icon.equals(other.icon))
		{
			return false;
		}
		if (id != other.id)
		{
			return false;
		}
		if (!infoUrl.equals(other.infoUrl))
		{
			return false;
		}
		if (!name.equals(other.name))
		{
			return false;
		}
		return patterns.equals(other.patterns);
	}

	@Override
	public String toString()
	{
		return "Device [icon=" + icon + ", id=" + id + ", infoUrl=" + infoUrl + ", name=" + name + ", patterns=" + patterns + "]";
	}


	public static final class Builder
	{

		private static final String EMPTY = "";

		@javax.validation.constraints.NotNull
		private String icon = EMPTY;

		private int id = Integer.MIN_VALUE;

		@javax.validation.constraints.NotNull
		private String infoUrl = EMPTY;

		private String name;

		@javax.validation.constraints.NotNull
		private SortedSet<DevicePattern> patterns = new TreeSet<>();

		public Builder()
		{
			// default constructor
		}

		/**
		 * Creates a new instance of a builder with the data of the passed builder.
		 *
		 * @param builder
		 * 		builder containing the data to be copied
		 * 		<p>
		 * 		<p>
		 * 		if the given argument is {@code null}
		 */
		private Builder(@javax.validation.constraints.NotNull Builder builder)
		{
			Check.notNull(builder, "builder");

			icon = builder.icon;
			id = builder.id;
			infoUrl = builder.infoUrl;
			name = builder.name;
		}

		public Builder(@javax.validation.constraints.NotNull Device device)
		{
			Check.notNull(device, "device");
			icon = Check.notNull(device.getIcon(), "device.getIcon()");
			id = Check.notNegative(device.getId(), "device.getId()");
			infoUrl = Check.notNull(device.getInfoUrl(), "device.getInfoUrl()");
			name = Check.notNull(device.getName(), "device.getName()");
			patterns = new TreeSet<>(Check.notNull(device.getPatterns(), "device.getPatterns()"));
		}

		@javax.validation.constraints.NotNull
		public Device build()
		{
			return new Device(name, id, Category.evaluate(name), icon, infoUrl, patterns);
		}

		/**
		 * Creates a copy (with all its data) of the current builder.
		 *
		 * @return a new instance of the current builder, never {@code null}
		 */
		@javax.validation.constraints.NotNull
		public Builder copy()
		{
			return new Builder(this);
		}

		public String getIcon()
		{
			return icon;
		}

		@javax.validation.constraints.NotNull
		public Builder setIcon(@javax.validation.constraints.NotNull String icon)
		{
			this.icon = Check.notNull(icon, "icon");
			return this;
		}

		public int getId()
		{
			return id;
		}

		@javax.validation.constraints.NotNull
		public Builder setId(@javax.validation.constraints.NotNull String id)
		{
			setId(Integer.parseInt(Check.notEmpty(id, "id")));
			return this;
		}

		@javax.validation.constraints.NotNull
		public Builder setId(int id)
		{
			this.id = Check.notNegative(id, "id");
			return this;
		}

		public String getInfoUrl()
		{
			return infoUrl;
		}

		@javax.validation.constraints.NotNull
		public Builder setInfoUrl(@javax.validation.constraints.NotNull String infoUrl)
		{
			this.infoUrl = Check.notNull(infoUrl, "infoUrl");
			return this;
		}

		public String getName()
		{
			return name;
		}

		@javax.validation.constraints.NotNull
		public Builder setName(@javax.validation.constraints.NotNull String name)
		{
			this.name = Check.notNull(name, "name");
			return this;
		}

		public SortedSet<DevicePattern> getPatterns()
		{
			return patterns;
		}

		@javax.validation.constraints.NotNull
		public Builder setPatterns(@javax.validation.constraints.NotNull SortedSet<DevicePattern> patterns)
		{
			this.patterns = new TreeSet<>(Check.notNull(patterns, "patterns"));
			return this;
		}

	}

}
