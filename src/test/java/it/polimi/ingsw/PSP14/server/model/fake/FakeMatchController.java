package it.polimi.ingsw.PSP14.server.model.fake;

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

    /**
     * Always returns true, for the test cases.
     * @param player who cares
     * @param message who cares
     * @return true, always
     * @throws IOException never
     */
    @Override
    public boolean askQuestion(String player, String message) throws IOException {
        return true;
    }
}
