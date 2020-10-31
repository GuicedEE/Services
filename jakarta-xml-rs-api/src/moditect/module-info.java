module javax.ws.rs {
    requires transitive java.xml.bind;
    requires java.logging;

    exports javax.ws.rs;
    exports javax.ws.rs.client;
    exports javax.ws.rs.container;
    exports javax.ws.rs.core;
    exports javax.ws.rs.ext;
    exports javax.ws.rs.sse;

    opens javax.ws.rs.core to javax.xml.bind;

    uses javax.ws.rs.client.ClientBuilder;
    uses javax.ws.rs.ext.RuntimeDelegate;
    uses javax.ws.rs.sse.SseEventSource$Builder;
}