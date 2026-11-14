package com.guicedee.modules.services.hibernate.scan;

import com.guicedee.client.services.IGuiceDiscovery;
import java.util.*;

public class PersistenceDiscoveryIndex implements IGuiceDiscovery<PersistenceDiscoveryIndex> {
    public Collection<Class<?>> classes() { return List.of(ExplicitPersistenceScannerTest.Model.class); }
}
