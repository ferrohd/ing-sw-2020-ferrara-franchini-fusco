package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class YesNoMessage implements ClientExecutableMessage {
    private String question;

    public YesNoMessage(String question) {
        this.question = question;
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        ui.notify(question);
        // TODO: actual implementation, for now always returns yes as an answer

        Message message = new ChoiceMessage(1);
        try {
            serverConnection.sendMessage(message);
        } catch(IOException e) {
            return false;
        }

        return true;
    }
}
