package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.MatchModel;

import java.io.IOException;
import java.time.Instant;

/**
 * A game action which provides the base for custom actions.
 */
public abstract class Action {
    private final Instant timestamp;
    private final String user;

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

    /**
     * @return the player who made the action
     */
    public String getUser() {
        return user;
    }

    /**
     * Executes the message on the current model
     *
     * @param model the current model
     * @throws IOException if a connection error occurs
     */
    public abstract void execute(MatchModel model) throws IOException;
}
