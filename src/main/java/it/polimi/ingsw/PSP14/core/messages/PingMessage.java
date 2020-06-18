package it.polimi.ingsw.PSP14.core.messages;


import java.time.Instant;

/**
 * Message to detect disconnections.
 */
public class PingMessage implements Message {
    private Instant timestamp = Instant.now();

    public Instant getTimestamp() {
        return timestamp;
    }
}
