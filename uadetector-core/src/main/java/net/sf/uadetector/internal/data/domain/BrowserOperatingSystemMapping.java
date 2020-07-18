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


public final class BrowserOperatingSystemMapping
		implements Serializable
{

	private static final long serialVersionUID = 6074931648810031757L;
	/**
	 * ID of a browser entry
	 */

	private final int browserId;
	/**
	 * ID of a operating system entry
	 */

	private final int operatingSystemId;

	/**
	 * Constructs an instance of {@code BrowserOperatingSystemMapping}.
	 *
	 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
	 * 		if one of the given arguments is smaller than {@code 0}
	 */
	public BrowserOperatingSystemMapping(int browserId, int operatingSystemId)
	{
		Check.notNegative(browserId, "browserId");
		Check.notNegative(operatingSystemId, "operatingSystemId");

		this.browserId = browserId;
		this.operatingSystemId = operatingSystemId;
	}


	public int getBrowserId()
	{
		return browserId;
	}


	public int getOperatingSystemId()
	{
		return operatingSystemId;
	}

	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = prime * result + browserId;
		result = prime * result + operatingSystemId;
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
		BrowserOperatingSystemMapping other = (BrowserOperatingSystemMapping) obj;
		if (browserId != other.browserId)
		{
			return false;
		}
		return operatingSystemId == other.operatingSystemId;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BrowserOperatingSystemMapping [browserId=");
		builder.append(browserId);
		builder.append(", operatingSystemId=");
		builder.append(operatingSystemId);
		builder.append("]");
		return builder.toString();
	}


	public static final class Builder
	{

		/**
		 * ID of a browser entry
		 */
		private int browserId = Integer.MIN_VALUE;

		/**
		 * ID of a operating system entry
		 */
		private int operatingSystemId = Integer.MIN_VALUE;

		/**
		 * Build an instance of {@code BrowserOperatingSystemMapping}.
		 *
		 * @return a new instance of {@code BrowserOperatingSystemMapping}
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if one of the needed arguments to build an instance of {@code BrowserOperatingSystemMapping} is
		 * 		invalid
		 * 		<p>
		 * 		if one of the needed arguments to build an instance of {@code BrowserOperatingSystemMapping} is
		 * 		invalid
		 */
		@javax.validation.constraints.NotNull
		public BrowserOperatingSystemMapping build()
		{
			return new BrowserOperatingSystemMapping(browserId, operatingSystemId);
		}

		/**
		 * Gets the identification number of a browser entry.
		 *
		 * @return identification number of a browser entry
		 */
		public int getBrowserId()
		{
			return browserId;
		}

		/**
		 * Sets the identification number of a browser entry via a string.
		 *
		 * @param browserId
		 * 		identification number (as a {@code String)}
		 * 		<p>
		 * 		<p>
		 * 		if the given argument is {@code null}
		 *
		 * @throws NumberFormatException
		 * 		if the string can not be interpreted as a number
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the interpreted number is smaller than {@code 0}
		 */
		@javax.validation.constraints.NotNull
		public Builder setBrowserId(@javax.validation.constraints.NotNull String browserId)
		{
			Check.notNull(browserId, "browserId");

			this.setBrowserId(Integer.parseInt(browserId.trim()));
			return this;
		}

		/**
		 * Sets the identification number of a browser entry.
		 *
		 * @param browserId
		 * 		identification number
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the given number is smaller than {@code 0}
		 */
		@javax.validation.constraints.NotNull
		public Builder setBrowserId(int browserId)
		{
			Check.notNegative(browserId, "browserId");

			this.browserId = browserId;
			return this;
		}

		/**
		 * Gets the identification number of an operating system entry.
		 *
		 * @return identification number of an operating system entry
		 */
		public int getOperatingSystemId()
		{
			return operatingSystemId;
		}

		/**
		 * Sets the identification number of an operating system entry via a string.
		 *
		 * @param operatingSystemId
		 * 		identification number (as a {@code String)}
		 * 		<p>
		 * 		<p>
		 * 		if the given argument is {@code null}
		 *
		 * @throws NumberFormatException
		 * 		if the string can not be interpreted as a number
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the interpreted number is smaller than {@code 0}
		 */
		@javax.validation.constraints.NotNull
		public Builder setOperatingSystemId(@javax.validation.constraints.NotNull String operatingSystemId)
		{
			Check.notNull(operatingSystemId, "operatingSystemId");

			this.setOperatingSystemId(Integer.parseInt(operatingSystemId.trim()));
			return this;
		}

		/**
		 * Sets the identification number of an operating system entry.
		 *
		 * @param operatingSystemId
		 * 		identification number
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the given number is smaller than {@code 0}
		 */
		@javax.validation.constraints.NotNull
		public Builder setOperatingSystemId(int operatingSystemId)
		{
			Check.notNegative(operatingSystemId, "operatingSystemId");

			this.operatingSystemId = operatingSystemId;
			return this;
		}

	}

}
