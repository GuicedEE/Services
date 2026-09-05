package com.guicedee.modules.services.hibernate.scan;

import com.guicedee.client.IGuiceContext;
import org.hibernate.boot.archive.scan.spi.Scanner;
import org.hibernate.boot.archive.scan.spi.ScannerFactory;
import org.hibernate.boot.archive.spi.ArchiveDescriptorFactory;

/**
 * Supplies the GuicedEE ClassGraph-backed Hibernate archive scanner.
 */
public final class ClassGraphScannerFactory implements ScannerFactory
{
    @Override
    public Scanner getScanner(ArchiveDescriptorFactory archiveDescriptorFactory)
    {
        return new ClassGraphScanner(IGuiceContext.instance());
    }
}
