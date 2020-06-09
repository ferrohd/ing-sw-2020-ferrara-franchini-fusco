package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.core.messages.ChoiceMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;

import java.io.IOException;
import java.util.List;

public class FakeMatchController extends MatchController {
    private List<Message> messages;
    private int messageId;

    public FakeMatchController() throws IOException {
    }
}
