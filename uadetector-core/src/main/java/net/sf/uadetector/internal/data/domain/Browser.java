/**
 * Copyright 2012 André Rouél
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/
package net.sf.uadetector.internal.data.domain;

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.Check;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Browser
		implements Identifiable, Serializable
{
	private final UserAgentFamily family;
	private final String familyName;
	private final int hash;
	private final String icon;
	private final int id;
	private final String infoUrl;

	private final OperatingSystem operatingSystem;
	private final SortedSet<BrowserPattern> patterns;
	private final String producer;
	private final String producerUrl;
	private final BrowserType type;
	private final String url;

	public Browser(int id, @NotNull UserAgentFamily family, @NotNull String familyName,
	               @NotNull SortedSet<BrowserPattern> patterns, BrowserType type,
	               OperatingSystem operatingSystem, @NotNull String icon, @NotNull String infoUrl,
	               @NotNull String producer, @NotNull String producerUrl, @NotNull String url)
	{
		this.id = Check.notNegative(id, "id");
		this.family = Check.notNull(family, "family");
		this.familyName = Check.notNull(familyName, "familyName");
		this.patterns = Collections.unmodifiableSortedSet(new TreeSet<>(Check.notNull(patterns, "patterns")));
		this.type = Check.notNull(type, "type");
		this.operatingSystem = operatingSystem;
		this.icon = Check.notNull(icon, "icon");
		this.infoUrl = Check.notNull(infoUrl, "infoUrl");
		this.producer = Check.notNull(producer, "producer");
		this.producerUrl = Check.notNull(producerUrl, "producerUrl");
		this.url = Check.notNull(url, "url");
		hash = buildHashCode(id, family, familyName, patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl, url);
	}

	private static int buildHashCode(int id, @NotNull UserAgentFamily family, @NotNull String familyName,
	                                 @NotNull SortedSet<BrowserPattern> patterns, BrowserType type,
	                                 OperatingSystem operatingSystem, @NotNull String icon, @NotNull String infoUrl,
	                                 @NotNull String producer, @NotNull String producerUrl, @NotNull String url)
	{
		int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + family.hashCode();
		result = prime * result + familyName.hashCode();
		result = prime * result + patterns.hashCode();
		result = prime * result + type.hashCode();
		result = prime * result + (operatingSystem == null ? 0 : operatingSystem.hashCode());
		result = prime * result + icon.hashCode();
		result = prime * result + infoUrl.hashCode();
		result = prime * result + producer.hashCode();
		result = prime * result + producerUrl.hashCode();
		result = prime * result + url.hashCode();
		return result;
	}

	/**
	 * Copy values from itself to a <code>UserAgentInfo.Builder</code>.
	 */
	public void copyTo(@NotNull UserAgent.Builder builder)
	{
		builder.setFamily(family);
		builder.setIcon(icon);
		builder.setName(familyName);
		builder.setProducer(producer);
		builder.setProducerUrl(producerUrl);
		builder.setTypeName(type.getName());
		builder.setUrl(url);
		if (operatingSystem != null)
		{
			operatingSystem.copyTo(builder);
		}
	}

	@NotNull
	public UserAgentFamily getFamily()
	{
		return family;
	}

	@NotNull
	public String getFamilyName()
	{
		return familyName;
	}

	@NotNull
	public String getIcon()
	{
		return icon;
	}

	@Override

	public int getId()
	{
		return id;
	}

	@NotNull
	public String getInfoUrl()
	{
		return infoUrl;
	}

	public OperatingSystem getOperatingSystem()
	{
		return operatingSystem;
	}

	@NotNull
	public SortedSet<BrowserPattern> getPatterns()
	{
		return patterns;
	}

	@NotNull
	public String getProducer()
	{
		return producer;
	}

	@NotNull
	public String getProducerUrl()
	{
		return producerUrl;
	}

	@NotNull
	public BrowserType getType()
	{
		return type;
	}

	@NotNull
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
		Browser other = (Browser) obj;
		if (id != other.id)
		{
			return false;
		}
		if (!family.equals(other.family))
		{
			return false;
		}
		if (!familyName.equals(other.familyName))
		{
			return false;
		}
		if (!patterns.equals(other.patterns))
		{
			return false;
		}
		if (!type.equals(other.type))
		{
			return false;
		}
		if (operatingSystem == null)
		{
			if (other.operatingSystem != null)
			{
				return false;
			}
		}
		else if (!operatingSystem.equals(other.operatingSystem))
		{
			return false;
		}
		if (!icon.equals(other.icon))
		{
			return false;
		}
		if (!infoUrl.equals(other.infoUrl))
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
		return url.equals(other.url);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Browser [id=");
		builder.append(id);
		builder.append(", family=");
		builder.append(family);
		builder.append(", familyName=");
		builder.append(familyName);
		builder.append(", patterns=");
		builder.append(patterns);
		builder.append(", type=");
		builder.append(type);
		builder.append(", operatingSystem=");
		builder.append(operatingSystem);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}

	public static final class Builder
	{

		private static final String EMPTY = "";

		@NotNull
		private UserAgentFamily family = UserAgentFamily.UNKNOWN;

		@NotNull
		private String familyName = EMPTY;

		@NotNull
		private String icon = EMPTY;

		private int id = Integer.MIN_VALUE;

		@NotNull
		private String infoUrl = EMPTY;


		private OperatingSystem operatingSystem;

		@NotNull
		private SortedSet<BrowserPattern> patterns = new TreeSet<>();

		@NotNull
		private String producer = EMPTY;

		@NotNull
		private String producerUrl = EMPTY;


		private BrowserType type;

		private int typeId = Integer.MIN_VALUE;

		@NotNull
		private String url = EMPTY;

		public Builder()
		{
			//No config
		}

		public Builder(@NotNull Browser browser)
		{
			Check.notNull(browser, "browser");
			id = Check.notNegative(browser.getId(), "browser.getId()");
			family = Check.notNull(browser.getFamily(), "browser.getFamily()");
			familyName = Check.notNull(browser.getFamilyName(), "browser.getFamilyName()");
			patterns = new TreeSet<>(Check.notNull(browser.getPatterns(), "browser.getPatterns()"));
			type = Check.notNull(browser.getType(), "browser.getType()");
			operatingSystem = Check.notNull(browser.getOperatingSystem(), "browser.getOperatingSystem()");
			icon = Check.notNull(browser.getIcon(), "browser.getIcon()");
			infoUrl = Check.notNull(browser.getInfoUrl(), "browser.getInfoUrl()");
			producer = Check.notNull(browser.getProducer(), "browser.getProducer()");
			producerUrl = Check.notNull(browser.getProducerUrl(), "browser.getProducerUrl()");
			url = Check.notNull(browser.getUrl(), "browser.getUrl()");
		}

		protected Builder(@NotNull Builder builder)
		{
			Check.notNull(builder, "builder");
			family = builder.family;
			familyName = builder.familyName;
			icon = builder.icon;
			id = builder.id;
			infoUrl = builder.infoUrl;
			operatingSystem = builder.operatingSystem;
			patterns = builder.patterns;
			producer = builder.producer;
			producerUrl = builder.producerUrl;
			type = builder.type;
			typeId = builder.typeId;
			url = builder.url;
		}

		public Browser build()
		{
			return new Browser(id, family, familyName, patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl, url);
		}

		public Builder copy()
		{
			return new Builder(this);
		}

		public UserAgentFamily getFamily()
		{
			return family;
		}

		@NotNull
		private Builder setFamily(@NotNull UserAgentFamily family)
		{
			this.family = Check.notNull(family, "family");
			return this;
		}

		public String getFamilyName()
		{
			return familyName;
		}

		@NotNull
		public Builder setFamilyName(@NotNull String familyName)
		{
			this.familyName = Check.notNull(familyName, "familyName");
			return setFamily(UserAgentFamily.evaluate(familyName));
		}

		public String getIcon()
		{
			return icon;
		}

		@NotNull
		public Builder setIcon(@NotNull String icon)
		{
			this.icon = Check.notNull(icon, "icon");
			return this;
		}

		public int getId()
		{
			return id;
		}

		@NotNull
		public Builder setId(int id)
		{
			this.id = Check.notNegative(id, "id");
			return this;
		}

		@NotNull
		public Builder setId(@NotNull String id)
		{
			setId(Integer.parseInt(Check.notEmpty(id, "id")));
			return this;
		}

		public String getInfoUrl()
		{
			return infoUrl;
		}

		@NotNull
		public Builder setInfoUrl(@NotNull String infoUrl)
		{
			this.infoUrl = Check.notNull(infoUrl, "infoUrl");
			return this;
		}

		public OperatingSystem getOperatingSystem()
		{
			return operatingSystem;
		}

		@NotNull
		public Builder setOperatingSystem(@NotNull OperatingSystem operatingSystem)
		{
			this.operatingSystem = Check.notNull(operatingSystem, "operatingSystem");
			return this;
		}

		public SortedSet<BrowserPattern> getPatterns()
		{
			return patterns;
		}

		@NotNull
		public Builder setPatterns(@NotNull SortedSet<BrowserPattern> patterns)
		{
			this.patterns = new TreeSet<>(Check.notNull(patterns, "patterns"));
			return this;
		}

		public String getProducer()
		{
			return producer;
		}

		@NotNull
		public Builder setProducer(@NotNull String producer)
		{
			this.producer = Check.notNull(producer, "producer");
			return this;
		}

		public String getProducerUrl()
		{
			return producerUrl;
		}

		@NotNull
		public Builder setProducerUrl(@NotNull String producerUrl)
		{
			this.producerUrl = Check.notNull(producerUrl, "producerUrl");
			return this;
		}

		public BrowserType getType()
		{
			return type;
		}

		@NotNull
		public Builder setType(@NotNull BrowserType type)
		{
			this.type = Check.notNull(type, "type");
			setTypeId(type.getId());
			return this;
		}

		@NotNull
		public Builder setTypeId(int typeId)
		{
			this.typeId = Check.notNegative(typeId, "typeId");
			return this;
		}

		public int getTypeId()
		{
			return typeId;
		}

		@NotNull
		public Builder setTypeId(@NotNull String typeId)
		{
			setTypeId(Integer.parseInt(Check.notEmpty(typeId, "typeId")));
			return this;
		}

		public String getUrl()
		{
			return url;
		}

		@NotNull
		public Builder setUrl(@NotNull String url)
		{
			this.url = Check.notNull(url, "url");
			return this;
		}
	}

}
