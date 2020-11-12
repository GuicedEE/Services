module jakarta.ws.rs {
    requires transitive jakarta.xml.bind;
    requires java.logging;

    exports jakarta.ws.rs;
    exports jakarta.ws.rs.client;
    exports jakarta.ws.rs.container;
    exports jakarta.ws.rs.core;
    exports jakarta.ws.rs.ext;
    exports jakarta.ws.rs.sse;

    opens jakarta.ws.rs.core to jakarta.xml.bind;

    uses jakarta.ws.rs.client.ClientBuilder;
    uses jakarta.ws.rs.ext.RuntimeDelegate;
    uses jakarta.ws.rs.sse.SseEventSource$Builder;
}