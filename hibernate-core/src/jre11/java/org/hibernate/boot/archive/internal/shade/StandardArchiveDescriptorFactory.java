package org.hibernate.boot.archive.internal.shade;

import org.hibernate.boot.archive.internal.ArchiveHelper;
import org.hibernate.boot.archive.spi.ArchiveDescriptor;
import org.hibernate.boot.archive.spi.ArchiveDescriptorFactory;
import org.jboss.logging.Logger;

import java.net.URL;

/**
 * Standard implementation of ArchiveDescriptorFactory
 *
 * @author Emmanuel Bernard
 * @author Steve Ebersole
 */
public class StandardArchiveDescriptorFactory implements ArchiveDescriptorFactory {
    private static final Logger log = Logger.getLogger( StandardArchiveDescriptorFactory.class );

    /**
     * Singleton access
     */
    public static final StandardArchiveDescriptorFactory INSTANCE = new StandardArchiveDescriptorFactory();

    @Override
    public ArchiveDescriptor buildArchiveDescriptor(URL url) {
        return buildArchiveDescriptor( url, "" );
    }

    @Override
    public ArchiveDescriptor buildArchiveDescriptor(URL url, String entry) {

        ArchiveDescriptor returnable = null;
        returnable = new org.hibernate.boot.archive.internal.shade.ClassInfoArchiveDescriptor(this,url,entry);
        return returnable;
    }

    @Override
    public URL getJarURLFromURLEntry(URL url, String entry) throws IllegalArgumentException {
        return ArchiveHelper.getJarURLFromURLEntry( url, entry );
    }

    @Override
    public URL getURLFromPath(String jarPath) {
        return ArchiveHelper.getURLFromPath( jarPath );
    }

}
