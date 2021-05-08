package it.auties.whatsapp4j.builder;

import it.auties.whatsapp4j.model.WhatsappChat;
import it.auties.whatsapp4j.model.WhatsappContactMessage;
import it.auties.whatsapp4j.utils.ProtobufUtils;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Accessors(fluent = true)
public class WhatsappContactMessageBuilder implements WhatsappMessageBuilder<WhatsappContactMessage> {
    /**
     * The chat where this message is stored
     */
    private @Setter WhatsappChat chat;

    /**
     * A non null list of parsed VCards, one for each contact that the raw protobuf message used to build this object holds
     */
    private @Setter List<String> sharedContacts;

    /**
     * Builds a {@link WhatsappContactMessage} from the data provided
     *
     * @return a non null WhatsappContactMessage
     */
    @Override
    public @NotNull WhatsappContactMessage create() {
        Objects.requireNonNull(chat, "WhatsappAPI: Cannot create a WhatsappContactMessage with a null chat");
        Objects.requireNonNull(sharedContacts, "WhatsappAPI: Cannot create a WhatsappContactMessage with null sharedContacts");
        return new WhatsappContactMessage(ProtobufUtils.createMessageInfo(ProtobufUtils.createContactMessage(sharedContacts), chat.jid()));
    }
}