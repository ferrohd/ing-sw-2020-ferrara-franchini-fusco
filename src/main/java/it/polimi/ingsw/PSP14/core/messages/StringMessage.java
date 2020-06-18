package it.polimi.ingsw.PSP14.core.messages;

/**
 * Message that contains only text.
 */
public class StringMessage implements Message {
    private String string;

    /**
     * Constructor of the message. You've got to set
     * here the contents.
     * @param string the content of the message
     */
    public StringMessage(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
