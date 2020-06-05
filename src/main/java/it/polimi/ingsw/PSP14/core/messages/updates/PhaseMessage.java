package it.polimi.ingsw.PSP14.core.messages.updates;

public abstract class PhaseMessage implements UIUpdateMessage {
    private String player;

    public PhaseMessage(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
