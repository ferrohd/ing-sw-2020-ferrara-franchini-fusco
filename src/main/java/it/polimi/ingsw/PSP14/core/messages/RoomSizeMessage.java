package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class RoomSizeMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        int choice = ui.getRoomSize();
        Message choiceMessage = new ChoiceMessage(choice);
        try {
            serverConnection.sendMessage(choiceMessage);
        } catch(IOException e) {
            return false;
        }

        return true;
    }
}
