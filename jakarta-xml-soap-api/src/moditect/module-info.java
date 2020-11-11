module jakarta.xml.soap {
    requires transitive java.xml;
    requires transitive jakarta.activation;
    requires java.logging;

    exports jakarta.xml.soap;

    uses jakarta.xml.soap.MessageFactory;
    uses jakarta.xml.soap.SAAJMetaFactory;
    uses jakarta.xml.soap.SOAPConnectionFactory;
    uses jakarta.xml.soap.SOAPFactory;
}