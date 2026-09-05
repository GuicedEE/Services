/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.boot.archive.internal.shade;

import com.guicedee.client.IGuiceContext;
import io.github.classgraph.Resource;
import org.hibernate.boot.archive.spi.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * An ArchiveDescriptor implementation that obtains archive entries from the GuicedEE ClassGraph scan.
 *
 * @author Steve Ebersole
 * @author Marc Magon
 */
public class JarFileBasedArchiveDescriptor extends AbstractArchiveDescriptor
{
    private final IGuiceContext guiceContext;

    /**
     * Constructs a JarFileBasedArchiveDescriptor
     *
     * @param archiveDescriptorFactory The factory creating this
     * @param archiveUrl               The url to the JAR file
     * @param entry                    The prefix for entries within the JAR url
     */
    public JarFileBasedArchiveDescriptor(
            ArchiveDescriptorFactory archiveDescriptorFactory,
            URL archiveUrl,
            String entry)
    {
        this(archiveDescriptorFactory, archiveUrl, entry, IGuiceContext.instance());
    }

    /**
     * Constructs a JarFileBasedArchiveDescriptor using an existing GuicedEE context.
     *
     * @param archiveDescriptorFactory The factory creating this
     * @param archiveUrl               The URL to the archive
     * @param entry                    The prefix for entries within the archive URL
     * @param guiceContext             The GuicedEE context that owns the ClassGraph scan
     */
    public JarFileBasedArchiveDescriptor(
            ArchiveDescriptorFactory archiveDescriptorFactory,
            URL archiveUrl,
            String entry,
            IGuiceContext guiceContext)
    {
        super(archiveDescriptorFactory, archiveUrl, entry);
        this.guiceContext = Objects.requireNonNull(guiceContext, "guiceContext");
    }

    @Override
    public void visitArchive(ArchiveContext context)
    {
        for (Resource resource : getArchiveResources())
        {
            final String name = normalizePathName(resource.getPathRelativeToClasspathElement());
            if (getEntryBasePrefix() != null && !name.startsWith(getEntryBasePrefix()))
            {
                continue;
            }

            final ArchiveEntry entry = toArchiveEntry(resource, name);
            final ArchiveEntryHandler entryHandler = context.obtainArchiveEntryHandler(entry);
            entryHandler.handleEntry(entry, context);
        }
    }

    @Override
    public ArchiveEntry findEntry(String path)
    {
        final String normalizedPath = normalizePathName(path);
        for (Resource resource : getArchiveResources())
        {
            if (normalizedPath.equals(normalizePathName(resource.getPathRelativeToClasspathElement())))
            {
                return toArchiveEntry(resource, normalizedPath);
            }
        }
        return null;
    }

    private List<Resource> getArchiveResources()
    {
        final URI archiveUri;
        try
        {
            archiveUri = getArchiveUrl().toURI().normalize();
        }
        catch (URISyntaxException e)
        {
            throw new ArchiveException("Malformed archive URL [" + getArchiveUrl() + "]", e);
        }

        final List<Resource> resources = new ArrayList<>();
        for (Resource resource : guiceContext.getScanResult().getAllResources())
        {
            URI classpathElementUri = resource.getClasspathElementURI();
            if (classpathElementUri != null && archiveUri.equals(classpathElementUri.normalize()))
            {
                resources.add(resource);
            }
        }
        return resources;
    }

    private ArchiveEntry toArchiveEntry(Resource resource, String name)
    {
        final String relativeName = getEntryBasePrefix() != null && name.contains(getEntryBasePrefix())
                ? name.substring(getEntryBasePrefix().length())
                : name;
        final InputStreamAccess inputStreamAccess;
        try (InputStream inputStream = resource.open())
        {
            inputStreamAccess = buildByteBasedInputStreamAccess(name, inputStream);
        }
        catch (IOException e)
        {
            throw new ArchiveException("Unable to access ClassGraph resource [" + resource.getURI() + "]", e);
        }

        return new ArchiveEntry()
        {
            @Override
            public String getName()
            {
                return name;
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
    }
}
