package org.hibernate.boot.archive.internal.shade;

import com.guicedee.client.IGuiceContext;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;
import org.hibernate.boot.archive.spi.ArchiveEntry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JarFileBasedArchiveDescriptorTest
{
    @Test
    void readsJrtModuleEntriesFromTheGuicedeeClassGraphScan() throws IOException
    {
        try (ScanResult scanResult = new ClassGraph().enableSystemJarsAndModules().scan())
        {
            Resource objectClass = scanResult.getResourcesWithPath("java/lang/Object.class").get(0);
            IGuiceContext context = (IGuiceContext) Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class<?>[] { IGuiceContext.class },
                    (proxy, method, arguments) -> "getScanResult".equals(method.getName()) ? scanResult : null);
            JarFileBasedArchiveDescriptor descriptor = new JarFileBasedArchiveDescriptor(
                    null,
                    objectClass.getClasspathElementURI().toURL(),
                    null,
                    context);

            ArchiveEntry entry = descriptor.findEntry("java/lang/Object.class");

            assertNotNull(entry);
            assertArrayEquals(new byte[] { (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE },
                    entry.getStreamAccess().accessInputStream().readNBytes(4));
        }
    }
}
