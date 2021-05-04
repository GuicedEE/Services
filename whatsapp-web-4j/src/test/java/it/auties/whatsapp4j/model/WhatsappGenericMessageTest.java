package it.auties.whatsapp4j.model;

import it.auties.whatsapp4j.LogItAll;

import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

public class WhatsappGenericMessageTest {

    @org.junit.jupiter.api.Test
    public void testContextInfo() {
        LogManager.getLogManager().reset();
        for (Handler handler : java.util.logging.Logger.getLogger("").getHandlers()) {
            handler.setLevel(Level.OFF);
        }
        java.util.logging.Logger.getLogger("").addHandler(new LogItAll());
    //    WhatsappGenericMessage message = new WhatsappGenericMessage(new WhatsappProtobuf.WebMessageInfo());
    }
}