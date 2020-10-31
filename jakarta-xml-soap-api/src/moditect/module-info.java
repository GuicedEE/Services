module javax.xml.soap {
    requires transitive java.xml;
    requires transitive jakarta.activation;
    requires java.logging;

    exports javax.xml.soap;

    uses javax.xml.soap.MessageFactory;
    uses javax.xml.soap.SAAJMetaFactory;
    uses javax.xml.soap.SOAPConnectionFactory;
    uses javax.xml.soap.SOAPFactory;
}