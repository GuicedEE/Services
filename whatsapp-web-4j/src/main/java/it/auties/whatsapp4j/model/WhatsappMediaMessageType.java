package it.auties.whatsapp4j.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * The constants of this enumerated type describe the various types of media type that a {@link WhatsappMediaMessage} can hold
 */
@AllArgsConstructor
@Accessors(fluent = true)
public enum WhatsappMediaMessageType {
    /**
     * The message is an image
     */
    IMAGE("image/jpeg"),

    /**
     * The message is a document
     */
    DOCUMENT("application/octet-stream"),

    /**
     * The message is an audio
     */
    AUDIO("audio/mpeg"),

    /**
     * The message is a video
     */
    VIDEO("video/mp4"),

    /**
     * The message is a sticker
     */
    STICKER("image/webp");

    /**
     * The URL used to build the media request
     */
    private static final String WHATSAPP_URL = "https://media-mxp1-1.cdn.whatsapp.net/mms/%s";

    /**
     * The default mime type for this enumerated type.
     * Might be right, might be wrong, who knows.
     */
    private final @NotNull @Getter String defaultMimeType;

    /**
     * Returns the key used to encrypt a media message
     *
     * @return a non null array of bytes
     */
    public byte @NotNull [] key() {
        var name = whatsappName();
        return "WhatsApp %s Keys".formatted(Character.toUpperCase(name.charAt(0)) + name.substring(1)).getBytes();
    }


    /**
     * Returns the URL used to upload this enumerated type to Whatsapp Web's servers
     *
     * @return a non null string
     */
    public @NotNull String url() {
        return WHATSAPP_URL.formatted(whatsappName());
    }


    /**
     * Returns a name that can be interpreted by Whatsapp Web's servers
     *
     * @return a non null string
     */
    public @NotNull String whatsappName() {
        return this == STICKER ? IMAGE.whatsappName() : this.name().toLowerCase();
    }
}
