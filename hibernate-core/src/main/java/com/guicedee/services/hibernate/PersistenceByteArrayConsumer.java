package com.guicedee.services.hibernate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.guicedee.guicedinjection.interfaces.ObjectBinderKeys;
import com.guicedee.logger.LogFactory;

import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;
import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A consumer that reads persistence.xml files into PersistenceUnit objects
 */
public class PersistenceByteArrayConsumer
		implements ResourceList.ByteArrayConsumer
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("PersistenceByteArrayConsumer");

	/**
	 * Object mapper reader for Persistence XML Files
	 */
	private static final ObjectMapper om = new ObjectMapper();

	static
	{
		om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		om.registerModule(new SimpleModule()
						.addDeserializer(Properties.class,new StringToPropertiesDeserializer()));

	}

	/**
	 * Method accept ...
	 *
	 * @param resource
	 * 		of type Resource
	 * @param byteArray
	 * 		of type byte[]
	 */
	@Override
	public void accept(Resource resource, byte[] byteArray)
	{
		Set<ParsedPersistenceXmlDescriptor> units = getPersistenceUnitsFromFile(byteArray);
		for (Iterator<ParsedPersistenceXmlDescriptor> iterator = units.iterator(); iterator.hasNext(); )
		{
			ParsedPersistenceXmlDescriptor unit = iterator.next();
			Properties props = unit.getProperties();
			for (Object property : unit.getProperties().keySet())
			{
				String propName =property.toString();
				if(propName.equalsIgnoreCase(PersistenceFileHandler.getIgnorePersistenceUnitProperty()) &&
				    "true".equalsIgnoreCase(props.get(property).toString()))
				{

					iterator.remove();
				}
			}
			PersistenceFileHandler.getPersistenceUnits()
			                      .add(unit);
		}
		resource.close();
	}

	/**
	 * Gets all the persistence files
	 *
	 * @param persistenceFile
	 * 		The persistence file bytes
	 *
	 * @return A set of persistence units
	 */
	private Set<ParsedPersistenceXmlDescriptor> getPersistenceUnitsFromFile(byte[] persistenceFile)
	{
		Set<ParsedPersistenceXmlDescriptor> units = new LinkedHashSet<>();
		try
		{
			String xml = new String(persistenceFile);
			JSONObject jsonObj = XML.toJSONObject(xml);
			JSONObject pers = jsonObj.getJSONObject("persistence");
			try {
				JSONObject persU = pers.getJSONObject("persistence-unit");
				try {
					ParsedPersistenceXmlDescriptor pp = om.readValue(persU.toString(), ParsedPersistenceXmlDescriptor.class);
					units.add(pp);
				} catch (JsonProcessingException e) {
					log.log(Level.SEVERE, "Error streaming into Persistence Unit", e);
				}
			}catch (JSONException e)
			{
				//try as array
				JSONArray persU = pers.getJSONArray("persistence-unit");
				persU.forEach(a->{
					try {
						ParsedPersistenceXmlDescriptor p1 = om.readValue(a.toString(), ParsedPersistenceXmlDescriptor.class);
						units.add(p1);
					} catch (JsonProcessingException e1) {
						log.log(Level.SEVERE, "Error streaming into Persistence Unit", e1);
					}
				});
			}
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "Error streaming", t);
		}
		return units;
	}
}