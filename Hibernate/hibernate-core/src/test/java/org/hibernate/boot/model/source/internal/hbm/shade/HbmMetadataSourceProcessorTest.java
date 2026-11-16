package org.hibernate.boot.model.source.internal.hbm.shade;

import org.hibernate.boot.spi.MetadataBuildingContext;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HbmMetadataSourceProcessorTest {
    @Test void emptyBindingsNeedNoLegacyServicesAndAllPhasesRemainNoOps() {
        var context = (MetadataBuildingContext) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class<?>[]{MetadataBuildingContext.class}, (proxy, method, args) -> {
                    throw new AssertionError("No legacy mapping services should be requested: " + method);
                });
        var processor = new HbmMetadataSourceProcessorImpl(List.of(), context);
        processor.prepare();
        processor.processTypeDefinitions();
        processor.processQueryRenames();
        processor.processNamedQueries();
        processor.processAuxiliaryDatabaseObjectDefinitions();
        processor.processFilterDefinitions();
        processor.processFetchProfiles();
        processor.processIdentifierGenerators();
        processor.prepareForEntityHierarchyProcessing();
        var entities = new HashSet<>(List.of("already.mapped.Entity"));
        processor.processEntityHierarchies(entities);
        processor.postProcessEntityHierarchies();
        processor.processResultSetMappings();
        processor.finishUp();
        assertEquals(java.util.Set.of("already.mapped.Entity"), entities);
    }
}
