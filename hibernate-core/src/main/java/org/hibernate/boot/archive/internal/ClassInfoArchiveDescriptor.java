package org.hibernate.boot.archive.internal;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;
import org.hibernate.boot.archive.spi.*;
import com.guicedee.guicedinjection.*;

import javax.persistence.*;
import java.net.URL;

public class ClassInfoArchiveDescriptor extends AbstractArchiveDescriptor {

    public ClassInfoArchiveDescriptor(ArchiveDescriptorFactory archiveDescriptorFactory, URL archiveUrl, String entryBasePrefix) {
        super(archiveDescriptorFactory, archiveUrl, entryBasePrefix);
    }
    
    @Override
    public void visitArchive(ArchiveContext archiveContext) {
        ClassInfoList classesWithAnnotation = GuiceContext.instance().getScanResult()
                .getClassesWithAnnotation(Entity.class.getCanonicalName());
        classesWithAnnotation.addAll(
                        GuiceContext.instance().getScanResult()
                                .getClassesWithAnnotation(MappedSuperclass.class.getCanonicalName())
                );
        classesWithAnnotation.addAll(
                        GuiceContext.instance().getScanResult()
                                .getClassesWithAnnotation(Converter.class.getCanonicalName())
                );
        classesWithAnnotation.addAll(
                GuiceContext.instance().getScanResult()
                        .getClassesWithAnnotation(Embeddable.class.getCanonicalName())
        );
        classesWithAnnotation.addAll(
                GuiceContext.instance().getScanResult()
                        .getClassesWithAnnotation(Embedded.class.getCanonicalName())
        );
        ResourceList resourcesWithLeafName = GuiceContext.instance().getScanResult()
                .getResourcesWithLeafName("persistence.xml");
        resourcesWithLeafName.addAll(GuiceContext.instance().getScanResult()
                .getResourcesWithExtension("hbm.xml")
        );

        for (ClassInfo classInfo : classesWithAnnotation) {
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

        for (Resource resource : resourcesWithLeafName) {
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
