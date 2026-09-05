package com.guicedee.modules.services.hibernate.scan;

import com.guicedee.client.IGuiceContext;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.hibernate.boot.archive.scan.spi.ScanParameters;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassGraphScannerTest
{
    @Test
    void discoversModelsFromTheGuicedeeClassGraphScan() throws Exception
    {
        try (ScanResult scanResult = new ClassGraph()
                .overrideClassLoaders(ClassGraphScannerTest.class.getClassLoader())
                .acceptPackages(ClassGraphScannerTest.class.getPackageName())
                .enableClassInfo()
                .enableAnnotationInfo()
                .ignoreClassVisibility()
                .scan())
        {
            IGuiceContext context = (IGuiceContext) Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class<?>[] { IGuiceContext.class },
                    (proxy, method, arguments) -> "getScanResult".equals(method.getName()) ? scanResult : null);
            URL rootUrl = scanResult.getClassInfo(TestEntity.class.getName()).getClasspathElementURI().toURL();
            org.hibernate.boot.archive.scan.spi.ScanResult result = new ClassGraphScanner(context).scan(
                    environment(rootUrl),
                    options(),
                    new ScanParameters() { });

            assertTrue(result.getLocatedClasses().stream().anyMatch(descriptor ->
                    descriptor.getName().equals(TestEntity.class.getName())
                            && descriptor.getCategorization() == ClassDescriptor.Categorization.MODEL));
        }
    }

    @Test
    void discoversPackageInfoFromTheGuicedeeClassGraphScan() throws Exception
    {
        try (ScanResult scanResult = new ClassGraph()
                .overrideClassLoaders(ClassGraphScannerTest.class.getClassLoader())
                .acceptPackages(ClassGraphScannerTest.class.getPackageName())
                .enableClassInfo()
                .enableAnnotationInfo()
                .ignoreClassVisibility()
                .scan())
        {
            IGuiceContext context = (IGuiceContext) Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class<?>[] { IGuiceContext.class },
                    (proxy, method, arguments) -> "getScanResult".equals(method.getName()) ? scanResult : null);
            URL rootUrl = scanResult.getClassInfo(ClassGraphScannerTest.class.getName()).getClasspathElementURI().toURL();
            org.hibernate.boot.archive.scan.spi.ScanResult result = new ClassGraphScanner(context).scan(
                    environment(rootUrl),
                    options(),
                    new ScanParameters() { });

            assertTrue(result.getLocatedPackages().stream().anyMatch(descriptor ->
                    descriptor.getName().equals(ClassGraphScannerTest.class.getPackageName())));
        }
    }

    private static ScanEnvironment environment(URL rootUrl)
    {
        return new ScanEnvironment()
        {
            @Override
            public URL getRootUrl()
            {
                return rootUrl;
            }

            @Override
            public List<URL> getNonRootUrls()
            {
                return List.of();
            }

            @Override
            public List<String> getExplicitlyListedClassNames()
            {
                return List.of();
            }

            @Override
            public List<String> getExplicitlyListedMappingFiles()
            {
                return List.of();
            }
        };
    }

    private static ScanOptions options()
    {
        return new ScanOptions()
        {
            @Override
            public boolean canDetectUnlistedClassesInRoot()
            {
                return true;
            }

            @Override
            public boolean canDetectUnlistedClassesInNonRoot()
            {
                return false;
            }

            @Override
            public boolean canDetectHibernateMappingFiles()
            {
                return false;
            }
        };
    }

    @Entity
    static class TestEntity
    {
        @Id
        Long id;
    }
}
