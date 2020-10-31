module javax.xml.ws {
    requires transitive java.xml;
    requires transitive java.xml.bind;
    requires transitive javax.xml.soap;
    requires java.logging;

    exports javax.xml.ws;
    exports javax.xml.ws.handler;
    exports javax.xml.ws.handler.soap;
    exports javax.xml.ws.http;
    exports javax.xml.ws.soap;
    exports javax.xml.ws.spi;
    exports javax.xml.ws.spi.http;
    exports javax.xml.ws.wsaddressing;

    opens javax.xml.ws.wsaddressing to javax.xml.bind;

    uses javax.xml.ws.spi.Provider;
}