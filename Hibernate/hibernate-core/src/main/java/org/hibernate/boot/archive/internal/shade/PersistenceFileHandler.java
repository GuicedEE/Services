package org.hibernate.boot.archive.internal.shade;

import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;
import org.hibernate.jpa.boot.internal.PersistenceXmlParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Loads persistence units from persistence files as found on the registered classpath
 */
@SuppressWarnings("unused")
public class PersistenceFileHandler
{
	/**
	 * The logger
	 */
	private static final Logger log = Logger.getLogger("PersistenceFileHandler");
	/**
	 * The property that marks a persistence unit as ignore
	 */
	private static final String ignorePersistenceUnitProperty = "guicedpersistence.ignore";
	/**
	 * A list of all registered persistence units
	 */
	private static final Set<ParsedPersistenceXmlDescriptor> persistenceUnits = new HashSet<>();
	
	static
	{
		List<ParsedPersistenceXmlDescriptor> parsedPersistenceXmlDescriptors = PersistenceXmlParser.locatePersistenceUnits(new HashMap());
		for (ParsedPersistenceXmlDescriptor parsedPersistenceXmlDescriptor : parsedPersistenceXmlDescriptors)
		{
			log.config("Parsed XML Descriptor - " + parsedPersistenceXmlDescriptor.getName());
		}
		persistenceUnits.addAll(parsedPersistenceXmlDescriptors.stream()
		                                                       .filter(a -> !"true".equals(a.getProperties()
		                                                                                    .getProperty(ignorePersistenceUnitProperty, "false")))
		                                                       .collect(Collectors.toList()));
	}
	
	/**
	 * A new persistence file handler
	 */
	public PersistenceFileHandler()
	{
		//No Config Required
	}
	
	/**
	 * Returns all the persistence units that were found or loaded
	 *
	 * @return A set of persistence units
	 */
	public static Set<ParsedPersistenceXmlDescriptor> getPersistenceUnits()
	{
		return persistenceUnits;
	}
	
	/**
	 * Method getIgnorePersistenceUnitProperty returns the ignorePersistenceUnitProperty of this PersistenceFileHandler object.
	 * <p>
	 * The property that marks a persistence unit as ignore
	 *
	 * @return the ignorePersistenceUnitProperty (type String) of this PersistenceFileHandler object.
	 */
	@SuppressWarnings("WeakerAccess")
	public static String getIgnorePersistenceUnitProperty()
	{
		return ignorePersistenceUnitProperty;
	}
	
	/**
	 * Method hashCode ...
	 *
	 * @return int
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
	
	/**
	 * Method equals ...
	 *
	 * @param obj of type Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		return obj.getClass()
		          .equals(getClass());
	}
}
