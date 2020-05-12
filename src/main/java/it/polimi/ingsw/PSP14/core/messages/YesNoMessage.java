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
    public void execute(UI ui, ServerConnection serverConnection) throws IOException {
        ui.notify(question);
        Message message = new ChoiceMessage(ui.chooseYesNo(question));
        try {
            serverConnection.sendMessage(message);
        } catch(IOException e) {
            return false;
        }

        return true;
    }
}
