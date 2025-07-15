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

    opens io.cloudevents to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.jackson to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.builder to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.data to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.extensions to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.extensions.impl to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.format to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.impl to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.message to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.message.impl to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.provider to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.v1 to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.v03 to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.core.validator to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.lang to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.rw to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
    opens io.cloudevents.types to com.fasterxml.jackson.databind, com.fasterxml.jackson.core,com.google.guice;
}
