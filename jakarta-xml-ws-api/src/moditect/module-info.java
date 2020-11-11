module jakarta.xml.ws {
    requires transitive java.xml;
    requires transitive java.xml.bind;
    requires transitive jakarta.xml.soap;
    requires java.logging;

    exports jakarta.xml.ws;
    exports jakarta.xml.ws.handler;
    exports jakarta.xml.ws.handler.soap;
    exports jakarta.xml.ws.http;
    exports jakarta.xml.ws.soap;
    exports jakarta.xml.ws.spi;
    exports jakarta.xml.ws.spi.http;
    exports jakarta.xml.ws.wsaddressing;

    opens jakarta.xml.ws.wsaddressing to jakarta.xml.bind;

    uses jakarta.xml.ws.spi.Provider;
}