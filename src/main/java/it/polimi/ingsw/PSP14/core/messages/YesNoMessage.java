package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message of a terribly named class that returns a player's choice as a
 * boolean value to the server.
 */
public class YesNoMessage implements ClientExecutableMessage {
    private final String question;

    public YesNoMessage(String question) {
        this.question = question;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        Message message = new ChoiceMessage(ui.chooseYesNo(question));
        serverConnection.sendMessage(message);
    }
}
