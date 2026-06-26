module io.cloudevents {
    exports io.cloudevents;
    //exports io.cloudevents.core;
    exports io.cloudevents.core.builder;
    exports io.cloudevents.core.data;
    //exports io.cloudevents.core.extensions;
    exports io.cloudevents.core.extensions.impl;
    exports io.cloudevents.core.format;
    exports io.cloudevents.core.impl;
    exports io.cloudevents.core.message;
    exports io.cloudevents.core.message.impl;
    //exports io.cloudevents.core.provider;
    exports io.cloudevents.core.v1;
//    exports io.cloudevents.core.v03;
    exports io.cloudevents.core.validator;
    exports io.cloudevents.lang;
    exports io.cloudevents.rw;
    exports io.cloudevents.types;

    uses io.cloudevents.core.format.EventFormat;
    provides io.cloudevents.core.format.EventFormat with io.cloudevents.jackson.JsonFormat;

    opens io.cloudevents to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.jackson to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.builder to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.data to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.extensions to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.extensions.impl to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.format to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.impl to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.message to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.message.impl to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.provider to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.v1 to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.v03 to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.validator to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.lang to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.rw to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.types to tools.jackson.databind, tools.jackson.core,com.google.guice;
}
