package org.hibernate.boot.archive.internal.shade;

import com.guicedee.client.IGuiceContext;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;
import org.hibernate.boot.archive.spi.ArchiveEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JarFileBasedArchiveDescriptorTest
{
    @TempDir
    Path directory;

    @Test
    void retainsPhysicalEntryPathsBeneathArchivePackageRoots() throws IOException
    {
        Path archive = directory.resolve("app.jar");
        byte[] content = new byte[] { 1, 2, 3 };
        try (var output = new JarOutputStream(Files.newOutputStream(archive)))
        {
            output.putNextEntry(new JarEntry("BOOT-INF/classes/example.txt"));
            output.write(content);
            output.closeEntry();
        }
        try (var scan = new ClassGraph().enableClasspathEntries("jar:" + archive.toUri() + "!/BOOT-INF/classes").scan())
        {
            IGuiceContext context = (IGuiceContext) Proxy.newProxyInstance(getClass().getClassLoader(),
                    new Class<?>[] { IGuiceContext.class },
                    (proxy, method, arguments) -> "getScanResult".equals(method.getName()) ? scan : null);
            var descriptor = new JarFileBasedArchiveDescriptor(null, archive.toUri().toURL(), null, context);
            ArchiveEntry entry = descriptor.findEntry("BOOT-INF/classes/example.txt");
            assertNotNull(entry);
            try (var input = entry.getStreamAccess().accessInputStream())
            {
                assertArrayEquals(content, input.readAllBytes());
            }
        }
    }

    @Test
    void readsJrtModuleEntriesFromTheGuicedeeClassGraphScan() throws IOException
    {
        try (ScanResult scanResult = new ClassGraph().enableClasspath().enableNonSystemModules().enableSystemJars().enableSystemModules().enableClassInfo().scan())
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
