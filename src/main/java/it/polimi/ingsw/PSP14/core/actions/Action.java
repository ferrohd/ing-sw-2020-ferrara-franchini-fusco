package it.polimi.ingsw.PSP14.core.actions;

import java.time.Instant;

import it.polimi.ingsw.PSP14.server.model.Message;

/**
 * A game action.
 */
public abstract class Action extends Message {
    // DO NOT TOUCH!
    private static final long serialVersionUID = 1L;

    public Action() {
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
