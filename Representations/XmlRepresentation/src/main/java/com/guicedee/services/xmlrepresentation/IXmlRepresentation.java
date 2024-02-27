package com.guicedee.services.xmlrepresentation;

import com.guicedee.services.xmlrepresentation.utils.Pair;
import jakarta.xml.bind.*;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * Makes any object representable as XML
 *
 * @param <J>
 */
public interface IXmlRepresentation<J>
{
	
	@SuppressWarnings("unchecked")
	default J fromXml(String xml, Class<J> type)
	{
		try
		{
			J instance = type.getDeclaredConstructor()
							.newInstance();
			JAXBContext context = null;
			if (XmlContexts.JAXB.containsKey(type))
			{
				context = XmlContexts.JAXB.get(type);
			} else
			{
				context = JAXBContext.newInstance(type);
				XmlContexts.JAXB.put(type, context);
			}
			JAXBIntrospector introspector = context.createJAXBIntrospector();
			Unmarshaller unmarshaller = context.createUnmarshaller();
			try (StringReader sr = new StringReader(xml))
			{
				if (null == introspector.getElementName(instance))
				{
					XMLInputFactory factory = XMLInputFactory.newFactory();
					factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, isResolveExternalEntities());
					factory.setProperty(XMLInputFactory.SUPPORT_DTD, isSupportDTD());
					XMLStreamReader streamReader = factory.createXMLStreamReader(
									sr);
					JAXBElement<J> customer = unmarshaller.unmarshal(streamReader, type);
					instance = customer.getValue();
				} else
				{
					instance = (J) unmarshaller.unmarshal(sr);
				}
			}
			return instance;
		} catch (IllegalAccessException T)
		{
			throw new XmlRenderException("Unable to IllegalAccessException ", T);
		} catch (IllegalArgumentException T)
		{
			throw new XmlRenderException("Unable to IllegalArgumentException ", T);
		} catch (InstantiationException T)
		{
			throw new XmlRenderException("Unable to InstantiationException ", T);
		} catch (NoSuchMethodException T)
		{
			throw new XmlRenderException("Unable to NoSuchMethodException ", T);
		} catch (SecurityException T)
		{
			throw new XmlRenderException("Unable to SecurityException ", T);
		} catch (InvocationTargetException T)
		{
			throw new XmlRenderException("Unable to InvocationTargetException ", T);
		} catch (JAXBException T)
		{
			throw new XmlRenderException("Unable to JAXBException ", T);
		} catch (XMLStreamException T)
		{
			throw new XmlRenderException("Unable to XMLStreamException ", T);
		}
	}
	
	@SuppressWarnings("unchecked")
	default String toXml()
	{
		Object requestObject = this;
		try (StringWriter stringWriter = new StringWriter())
		{
			JAXBContext context = null;
			if (XmlContexts.JAXB.containsKey(requestObject.getClass()))
			{
				context = XmlContexts.JAXB.get(requestObject.getClass());
			} else
			{
				context = JAXBContext.newInstance(requestObject.getClass());
				XmlContexts.JAXB.put(requestObject.getClass(), context);
			}
			if (requestObject instanceof Pair)
			{
				Pair<?, ?> p = (Pair<?, ?>) requestObject;
				Class<?> keyType = p.getKey()
								.getClass();
				Class<?> valueType = p.getValue()
								.getClass();
				context = JAXBContext.newInstance(requestObject.getClass(), keyType, valueType);
			}
			JAXBIntrospector introspector = context.createJAXBIntrospector();
			Marshaller marshaller = context.createMarshaller();
			if (null == introspector.getElementName(requestObject))
			{
				
				@SuppressWarnings("rawtypes")
				JAXBElement<?> jaxbElement = new JAXBElement(new QName(requestObject.getClass()
								.getSimpleName()),
								requestObject.getClass(), requestObject);
				marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isFormattedOutput());
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isFragment());
				marshaller.marshal(jaxbElement, stringWriter);
			} else
			{
				marshaller.marshal(requestObject, stringWriter);
			}
			return stringWriter.toString();
		} catch (Exception e)
		{
			throw new XmlRenderException("Unable to marshal string writer from log intercepter", e);
		}
	}
	
	/**
	 * The property that requires the parser to resolve external parsed entities
	 * @return
	 */
	default boolean isResolveExternalEntities()
	{
		return false;
	}
	
	/**
	 * Write a DTD statement to the XML
	 * @return
	 */
	default boolean isSupportDTD()
	{
		return false;
	}
	
	/**
	 * The name of the property used to specify whether or not the marshalled XML data is formatted with linefeeds and indentation
	 * @return
	 */
	default boolean isFormattedOutput()
	{
		return false;
	}
	
	/**
	 * The name of the property used to specify whether or not the marshaller will generate document level events (ie calling startDocument or endDocument).
	 *
	 * @return
	 */
	default boolean isFragment()
	{
		return true;
	}
}
