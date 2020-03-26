package it.polimi.ingsw.PSP14.server.Match;

import java.time.Instant;

/**
 * A game action.
 */
public abstract class Action {
    private Instant timestamp;

    /**
     * @return the timestamp at which the action was created.
     */
    public Instant getTimestamp() {
        return timestamp;
    }
}
