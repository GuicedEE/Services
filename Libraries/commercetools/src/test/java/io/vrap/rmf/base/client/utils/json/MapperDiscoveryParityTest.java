package io.vrap.rmf.base.client.utils.json;

import com.commercetools.api.RegisterSimpleModuleMixin;
import com.commercetools.api.models.cart.CartUpdateAction;
import com.commercetools.api.models.product.Attribute;
import com.commercetools.discoveryfixture.ExtensionMixin;
import com.commercetools.discoveryfixture.ExtensionType;
import com.commercetools.discoveryfixture.ExtensionValue;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import static org.junit.jupiter.api.Assertions.*;

class MapperDiscoveryParityTest {
    @Test void discoversTheSameFullSdkAndApplicationExtensions() {
        var original = new Reflections("com.commercetools");
        ModelDiscovery.inScope(true, () -> {
            assertEquals(original.getTypesAnnotatedWith(RegisterSimpleModuleMixin.class),
                    ModelDiscovery.annotatedTypes("com.commercetools", RegisterSimpleModuleMixin.class));
            var subtypes = ModelDiscovery.annotatedTypes("com.commercetools", SubType.class);
            assertEquals(original.getTypesAnnotatedWith(SubType.class), subtypes);
            assertTrue(subtypes.contains(ExtensionValue.class));
            return null;
        });
    }

    @Test void keepsPolymorphismMixinsAndOptionsWithAndWithoutReuse() {
        for (String reuse : new String[]{"true", "false"}) {
            var mapper = JsonUtils.createObjectMapper(name -> name.equals(ModelDiscovery.REUSE_OPTION) ? reuse
                    : name.equals("customMixin") ? "true" : null);
            var extension = mapper.readValue("{\"kind\":\"extension\",\"value\":\"works\"}", ExtensionType.class);
            assertInstanceOf(ExtensionValue.class, extension);
            assertEquals("works", extension.getValue());
            assertEquals("{\"custom\":\"extension\"}", mapper.writeValueAsString(new ExtensionMixin.Value()));
            var action = mapper.readValue("{\"action\":\"addLineItem\",\"sku\":\"fixture\",\"quantity\":2}", CartUpdateAction.class);
            assertEquals("addLineItem", action.getAction());
            var attribute = mapper.readValue("{\"name\":\"number\",\"value\":42}", Attribute.class);
            assertEquals(42L, attribute.getValue());
        }
    }
}
