package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

public class NoticeMessage implements ClientExecutableMessage {
    private String content;

    public NoticeMessage(String content) {
        this.content = content;
    }

    public boolean execute(UI ui, ServerConnection serverConnection) {
        ui.notice(content);
        return true;
    }
}
