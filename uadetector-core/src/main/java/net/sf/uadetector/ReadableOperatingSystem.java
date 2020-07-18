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
package net.sf.uadetector;


/**
 * Defines an operating system.<br>
 * <br>
 * An operating system (OS) is a set of software that manages the hardware of a computer and provides common services
 * for programs. Popular examples of modern operating systems are Android, iOS, Linux, Mac OS X and Windows.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.
 *
 * @author André Rouél
 */
public interface ReadableOperatingSystem
{

	/**
	 * Gets the family of an operating system.
	 *
	 * @return the family of an operating system
	 */
	@javax.validation.constraints.NotNull
	OperatingSystemFamily getFamily();

	/**
	 * Gets the family name of an operating system.
	 *
	 * @return the family of an operating system
	 */
	@javax.validation.constraints.NotNull
	String getFamilyName();

	/**
	 * Gets the icon name of an operating system.
	 *
	 * @return the icon name of an operating system
	 */
	@javax.validation.constraints.NotNull
	String getIcon();

	/**
	 * Gets the name of an operating system.
	 *
	 * @return the name of an operating system
	 */
	@javax.validation.constraints.NotNull
	String getName();

	/**
	 * Returns the manufacturer of an operating system.
	 *
	 * @return the manufacturer
	 */
	@javax.validation.constraints.NotNull
	String getProducer();

	/**
	 * Returns the URL to the main website of the manufacturer of an operating system.
	 *
	 * @return the URL to the website of the manufacturer
	 */
	@javax.validation.constraints.NotNull
	String getProducerUrl();

	/**
	 * Returns the URL to the product or information page of an operating system.
	 *
	 * @return the URL to the product page
	 */
	@javax.validation.constraints.NotNull
	String getUrl();

	/**
	 * Gets the version number of an operating system.
	 *
	 * @return version number an operating system
	 */
	@javax.validation.constraints.NotNull
	VersionNumber getVersionNumber();

}
