package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class NotificationMessage implements ClientExecutableMessage {
    private String content;

    public NotificationMessage(String content) {
        this.content = content;
    }

    public boolean execute(UI ui, ServerConnection serverConnection) throws IOException {
        ui.showNotification(content);
        return false;
    }
}
