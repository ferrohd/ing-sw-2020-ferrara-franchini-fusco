package it.polimi.ingsw.PSP14.server.actions;

import java.io.Serializable;
import java.time.Instant;

import it.polimi.ingsw.PSP14.server.model.Match;

/**
 * A game action.
 */
public abstract class Action implements Serializable {
    // DO NOT TOUCH!
    private static final long serialVersionUID = 1L;
    private Instant timestamp;
    String user;

    public Action(String user) {
        this.timestamp = Instant.now();
        this.user = user;
    }

    /**
     * @return the timestamp at which the action was created.
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    public String getUser() { return user; }

    public abstract boolean execute(Match match);
}
