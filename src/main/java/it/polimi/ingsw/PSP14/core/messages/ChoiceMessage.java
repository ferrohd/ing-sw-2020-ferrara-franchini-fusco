package it.polimi.ingsw.PSP14.core.messages;

/**
 * Message containing the index of the proposal chosen in a list of proposals.
 * Usually used to return the client's selection to the server.
 */
public class ChoiceMessage implements Message {
    private int index;

    public ChoiceMessage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
