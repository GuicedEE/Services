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
package net.sf.uadetector.internal.data.domain;

import net.sf.uadetector.internal.Check;

import java.io.Serializable;

/**
 * The {@code BrowserType} class represents the type of a browser.<br>
 * <br>
 * A {@code BrowserType} object is immutable, their values cannot be changed after creation.
 *
 * @author André Rouél
 */

public final class BrowserType
		implements Identifiable, Serializable
{

	private static final long serialVersionUID = 2643535063309729806L;

	private final int id;
	@javax.validation.constraints.NotNull
	private final String name;

	public BrowserType(int id, @javax.validation.constraints.NotNull String name)
	{
		Check.notNegative(id, "id");
		Check.notNull(name, "name");

		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the identification number (ID) of a browser type.
	 *
	 * @return identification number (ID) of a browser type
	 */
	@Override
	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + name.hashCode();
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
		BrowserType other = (BrowserType) obj;
		if (id != other.id)
		{
			return false;
		}
		return name.equals(other.name);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BrowserType [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}


	public static final class Builder
	{

		/**
		 * Identification number (ID) of a browser type entry
		 */
		private int id = Integer.MIN_VALUE;

		/**
		 * Name of a browser type entry
		 */
		private String name;

		/**
		 * Builds a new instance of {@code BrowserType} and returns it.
		 *
		 * @return a new instance of {@code BrowserType}
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if one of the needed arguments to build an instance of {@code BrowserType} is invalid
		 * 		<p>
		 * 		if one of the needed arguments to build an instance of {@code BrowserType} is invalid
		 */
		@javax.validation.constraints.NotNull
		public BrowserType build()
		{
			return new BrowserType(id, name);
		}

		/**
		 * Sets the identification number via a string.<br>
		 * <br>
		 * An opening and closing Square brackets at the end of a string will be filtered. If the string can not be
		 * parsed as a long, a {@code NumberFormatException} will be thrown.
		 *
		 * @param id
		 * 		ID of browser type
		 */
		@javax.validation.constraints.NotNull
		public Builder setId(@javax.validation.constraints.NotNull String id)
		{
			Check.notNull(id, "id");

			this.setId(Integer.parseInt(id.trim()));
			return this;
		}

		/**
		 * Sets the identification number (ID).
		 *
		 * @param id
		 * 		ID of browser type
		 */
		@javax.validation.constraints.NotNull
		public Builder setId(int id)
		{
			Check.notNegative(id, "id");

			this.id = id;
			return this;
		}

		/**
		 * Sets the name.
		 *
		 * @param name
		 * 		name of the browser type
		 */
		@javax.validation.constraints.NotNull
		public Builder setName(@javax.validation.constraints.NotNull String name)
		{
			Check.notNull(name, "name");

			this.name = name;
			return this;
		}

	}

}
