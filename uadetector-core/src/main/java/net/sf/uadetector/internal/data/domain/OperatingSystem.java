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

import net.sf.uadetector.OperatingSystemFamily;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.VersionNumber;
import net.sf.uadetector.internal.Check;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public final class OperatingSystem
		implements Identifiable, Serializable
{

	private static final long serialVersionUID = -5330180544816352323L;
	@javax.validation.constraints.NotNull
	private final String family;
	private final int hash;
	@javax.validation.constraints.NotNull
	private final String icon;

	private final int id;
	@javax.validation.constraints.NotNull
	private final String infoUrl;
	@javax.validation.constraints.NotNull
	private final String name;
	@javax.validation.constraints.NotNull
	private final SortedSet<OperatingSystemPattern> patterns;
	@javax.validation.constraints.NotNull
	private final String producer;
	@javax.validation.constraints.NotNull
	private final String producerUrl;
	@javax.validation.constraints.NotNull
	private final String url;

	public OperatingSystem(int id, @javax.validation.constraints.NotNull String name, @javax.validation.constraints.NotNull String family,
	                       @javax.validation.constraints.NotNull String infoUrl, @javax.validation.constraints.NotNull SortedSet<OperatingSystemPattern> patterns, @javax.validation.constraints.NotNull String producer,
	                       @javax.validation.constraints.NotNull String producerUrl, @javax.validation.constraints.NotNull String url, @javax.validation.constraints.NotNull String icon)
	{
		this.id = Check.notNegative(id, "id");
		this.name = Check.notNull(name, "name");
		this.family = Check.notNull(family, "family");
		this.infoUrl = Check.notNull(infoUrl, "infoUrl");
		this.patterns = Collections.unmodifiableSortedSet(new TreeSet<>(Check.notNull(patterns, "patterns")));
		this.producer = Check.notNull(producer, "producer");
		this.producerUrl = Check.notNull(producerUrl, "producerUrl");
		this.url = Check.notNull(url, "url");
		this.icon = Check.notNull(icon, "icon");
		hash = buildHashCode(id, name, family, infoUrl, patterns, producer, producerUrl, url, icon);
	}

	private static int buildHashCode(int id, @javax.validation.constraints.NotNull String name, @javax.validation.constraints.NotNull String family,
	                                 @javax.validation.constraints.NotNull String infoUrl, @javax.validation.constraints.NotNull SortedSet<OperatingSystemPattern> patterns, @javax.validation.constraints.NotNull String producer,
	                                 @javax.validation.constraints.NotNull String producerUrl, @javax.validation.constraints.NotNull String url, @javax.validation.constraints.NotNull String icon)
	{
		int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + name.hashCode();
		result = prime * result + family.hashCode();
		result = prime * result + infoUrl.hashCode();
		result = prime * result + patterns.hashCode();
		result = prime * result + producer.hashCode();
		result = prime * result + producerUrl.hashCode();
		result = prime * result + url.hashCode();
		result = prime * result + icon.hashCode();
		return result;
	}

	/**
	 * Copies all information of the current operating system entry to the given user agent builder.
	 *
	 * @param builder
	 * 		user agent builder
	 */
	public void copyTo(@javax.validation.constraints.NotNull UserAgent.Builder builder)
	{
		OperatingSystemFamily f = OperatingSystemFamily.evaluate(family);
		VersionNumber version = VersionNumber.parseOperatingSystemVersion(f, builder.getUserAgentString());
		builder.setOperatingSystem(new net.sf.uadetector.OperatingSystem(f, family, icon, name, producer, producerUrl, url, version));
	}

	@javax.validation.constraints.NotNull
	public String getFamily()
	{
		return family;
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
	public SortedSet<OperatingSystemPattern> getPatterns()
	{
		return patterns;
	}

	@javax.validation.constraints.NotNull
	public String getProducer()
	{
		return producer;
	}

	@javax.validation.constraints.NotNull
	public String getProducerUrl()
	{
		return producerUrl;
	}

	@javax.validation.constraints.NotNull
	public String getUrl()
	{
		return url;
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
		OperatingSystem other = (OperatingSystem) obj;
		if (id != other.id)
		{
			return false;
		}
		if (!name.equals(other.name))
		{
			return false;
		}
		if (!family.equals(other.family))
		{
			return false;
		}
		if (!infoUrl.equals(other.infoUrl))
		{
			return false;
		}
		if (!patterns.equals(other.patterns))
		{
			return false;
		}
		if (!producer.equals(other.producer))
		{
			return false;
		}
		if (!producerUrl.equals(other.producerUrl))
		{
			return false;
		}
		if (!url.equals(other.url))
		{
			return false;
		}
		return icon.equals(other.icon);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("OperatingSystem [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", family=");
		builder.append(family);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", patterns=");
		builder.append(patterns);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", url=");
		builder.append(url);
		builder.append(", icon=");
		builder.append(icon);
		builder.append("]");
		return builder.toString();
	}


	public static final class Builder
	{

		@javax.validation.constraints.NotNull
		private String family = "";

		@javax.validation.constraints.NotNull
		private String icon = "";

		private int id = Integer.MIN_VALUE;

		@javax.validation.constraints.NotNull
		private String infoUrl = "";

		@javax.validation.constraints.NotNull
		private String name = "";

		@javax.validation.constraints.NotNull
		private SortedSet<OperatingSystemPattern> patterns = new TreeSet<>();

		@javax.validation.constraints.NotNull
		private String producer = "";

		@javax.validation.constraints.NotNull
		private String producerUrl = "";

		@javax.validation.constraints.NotNull
		private String url = "";

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
		protected Builder(@javax.validation.constraints.NotNull Builder builder)
		{
			Check.notNull(builder, "builder");

			family = builder.family;
			icon = builder.icon;
			id = builder.id;
			infoUrl = builder.infoUrl;
			name = builder.name;
			patterns.addAll(builder.patterns);
			producer = builder.producer;
			producerUrl = builder.producerUrl;
			url = builder.url;
		}

		public Builder(@javax.validation.constraints.NotNull OperatingSystem operatingSystem)
		{
			Check.notNull(operatingSystem, "operatingSystem");
			id = Check.notNegative(operatingSystem.getId(), "operatingSystem.getId()");
			name = Check.notNull(operatingSystem.getName(), "operatingSystem.getName()");
			family = Check.notNull(operatingSystem.getFamily(), "operatingSystem.getFamily()");
			infoUrl = Check.notNull(operatingSystem.getInfoUrl(), "operatingSystem.getInfoUrl()");
			patterns = new TreeSet<>(Check.notNull(operatingSystem.getPatterns(), "operatingSystem.getPatterns()"));
			producer = Check.notNull(operatingSystem.getProducer(), "operatingSystem.getProducer()");
			producerUrl = Check.notNull(operatingSystem.getProducerUrl(), "operatingSystem.getProducerUrl()");
			url = Check.notNull(operatingSystem.getUrl(), "operatingSystem.getUrl()");
			icon = Check.notNull(operatingSystem.getIcon(), "operatingSystem.getIcon()");
		}

		@javax.validation.constraints.NotNull
		public Builder addPatterns(@javax.validation.constraints.NotNull Set<OperatingSystemPattern> patterns)
		{
			Check.notNull(patterns, "patterns");

			this.patterns.addAll(patterns);
			return this;
		}

		@javax.validation.constraints.NotNull
		public OperatingSystem build()
		{
			return new OperatingSystem(id, name, family, infoUrl, patterns, producer, producerUrl, url, icon);
		}

		/**
		 * Creates a copy (with all its data) of the current builder.
		 *
		 * @return a new instance of the current builder, never {@code null}
		 */
		@javax.validation.constraints.NotNull
		public OperatingSystem.Builder copy()
		{
			return new Builder(this);
		}

		@javax.validation.constraints.NotNull
		public String getFamily()
		{
			return family;
		}

		@javax.validation.constraints.NotNull
		public Builder setFamily(@javax.validation.constraints.NotNull String family)
		{
			this.family = Check.notNull(family, "family");
			return this;
		}

		@javax.validation.constraints.NotNull
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
			Check.notEmpty(id, "id");

			this.setId(Integer.parseInt(id.trim()));
			return this;
		}

		@javax.validation.constraints.NotNull
		public Builder setId(int id)
		{
			this.id = Check.notNegative(id, "id");
			return this;
		}

		@javax.validation.constraints.NotNull
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

		@javax.validation.constraints.NotNull
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

		@javax.validation.constraints.NotNull
		public SortedSet<OperatingSystemPattern> getPatterns()
		{
			return patterns;
		}

		@javax.validation.constraints.NotNull
		public Builder setPatterns(@javax.validation.constraints.NotNull SortedSet<OperatingSystemPattern> patterns)
		{
			this.patterns = new TreeSet<>(Check.notNull(patterns, "patterns"));
			return this;
		}

		@javax.validation.constraints.NotNull
		public String getProducer()
		{
			return producer;
		}

		@javax.validation.constraints.NotNull
		public Builder setProducer(@javax.validation.constraints.NotNull String producer)
		{
			this.producer = Check.notNull(producer, "producer");
			return this;
		}

		@javax.validation.constraints.NotNull
		public String getProducerUrl()
		{
			return producerUrl;
		}

		@javax.validation.constraints.NotNull
		public Builder setProducerUrl(@javax.validation.constraints.NotNull String producerUrl)
		{
			this.producerUrl = Check.notNull(producerUrl, "producerUrl");
			return this;
		}

		@javax.validation.constraints.NotNull
		public String getUrl()
		{
			return url;
		}

		@javax.validation.constraints.NotNull
		public Builder setUrl(@javax.validation.constraints.NotNull String url)
		{
			this.url = Check.notNull(url, "url");
			return this;
		}

	}

}
