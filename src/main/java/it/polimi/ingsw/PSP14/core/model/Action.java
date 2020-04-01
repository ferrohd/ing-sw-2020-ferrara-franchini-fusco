package it.polimi.ingsw.PSP14.core.model;

import java.time.Instant;

/**
 * A game action.
 */
public abstract class Action {
    Action() {
        this.timestamp = Instant.now();
    }
    private Instant timestamp;

    /**
     * @return the timestamp at which the action was created.
     */
    public Instant getTimestamp() {
        return timestamp;
    }
}
