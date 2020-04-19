package it.polimi.ingsw.PSP14.core.messages;

public class NotifyMessage implements Message {
    private String content;

    public NotifyMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
