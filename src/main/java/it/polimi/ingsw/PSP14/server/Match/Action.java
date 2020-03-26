package it.polimi.ingsw.PSP14.server.Match;

import java.time.Instant;

public abstract class Action {
    private Instant timestamp;

    public Instant getTimestamp() {
        return timestamp;
    }
}
