package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message containing a custom notification for the player.
 */
public class NotificationMessage implements ClientExecutableMessage {
    private String content;

    public NotificationMessage(String content) {
        this.content = content;
    }

    public void execute(UI ui, ServerConnection serverConnection) throws IOException {
        ui.showNotification(content);
    }
}
