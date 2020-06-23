package it.polimi.ingsw.PSP14.core.messages.updates;

/**
 * Blueprint for a message that tells the client that a phase has started.
 */
public abstract class PhaseMessage implements UIUpdateMessage {
    private final String player;

    public PhaseMessage(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
