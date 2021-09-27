package org.hibernate.boot.archive.internal.shade;

import com.guicedee.guicedinjection.GuiceContext;
import io.github.classgraph.*;
import jakarta.persistence.*;
import org.hibernate.boot.archive.internal.UrlInputStreamAccess;
import org.hibernate.boot.archive.spi.*;

import java.net.URL;

public class ClassInfoArchiveDescriptor extends AbstractArchiveDescriptor
{
	
	public ClassInfoArchiveDescriptor(ArchiveDescriptorFactory archiveDescriptorFactory, URL archiveUrl, String entryBasePrefix)
	{
		super(archiveDescriptorFactory, archiveUrl, entryBasePrefix);
	}
	
	@Override
	public void visitArchive(ArchiveContext archiveContext)
	{
		
		ScanResult scanResult = GuiceContext.instance()
		                                    .getScanResult();
		
		ClassInfoList classesWithAnnotation = scanResult
				.getClassesWithAnnotation(Entity.class.getCanonicalName());
		classesWithAnnotation.addAll(
				scanResult
						.getClassesWithAnnotation(MappedSuperclass.class.getCanonicalName())
		);
		classesWithAnnotation.addAll(
				scanResult
						.getClassesWithAnnotation(Converter.class.getCanonicalName())
		);
		classesWithAnnotation.addAll(
				scanResult
						.getClassesWithAnnotation(Embeddable.class.getCanonicalName())
		);
		classesWithAnnotation.addAll(
				scanResult
						.getClassesWithAnnotation(Embedded.class.getCanonicalName())
		);
		ResourceList resourcesWithLeafName = scanResult
				.getResourcesWithLeafName("persistence.xml");
		resourcesWithLeafName.addAll(scanResult
				.getResourcesWithExtension("hbm.xml")
		);
		
		for (ClassInfo classInfo : classesWithAnnotation)
		{
			InputStreamAccess inputStreamAccess
					= new UrlInputStreamAccess(classInfo.getClasspathElementURL());
			String entryName = classInfo.getName();
			ArchiveEntry entry = new ArchiveEntry()
			{
				@Override
				public String getName()
				{
					return entryName;
				}
				
				@Override
				public String getNameWithinArchive()
				{
					return entryName;
				}
				
				@Override
				public InputStreamAccess getStreamAccess()
				{
					return inputStreamAccess;
				}
			};
			
			archiveContext.obtainArchiveEntryHandler(entry)
			              .handleEntry(entry, archiveContext);
		}
		
		for (Resource resource : resourcesWithLeafName)
		{
			InputStreamAccess inputStreamAccess
					= new UrlInputStreamAccess(resource.getClasspathElementURL());
			final String entryName = resource.getPath();
			final String relativeName = resource.getPathRelativeToClasspathElement();
			ArchiveEntry entry = new ArchiveEntry()
			{
				@Override
				public String getName()
				{
					return entryName;
				}
				
				@Override
				public String getNameWithinArchive()
				{
					return relativeName;
				}
				
				@Override
				public InputStreamAccess getStreamAccess()
				{
					return inputStreamAccess;
				}
			};
			archiveContext.obtainArchiveEntryHandler(entry)
			              .handleEntry(entry, archiveContext);
		}
		
	}
}
