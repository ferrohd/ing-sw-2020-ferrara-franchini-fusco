package it.polimi.ingsw.PSP14.core.messages;


import java.time.Instant;

public class PingMessage implements Message {
    private Instant timestamp = Instant.now();

    public Instant getTimestamp() {
        return timestamp;
    }
}
