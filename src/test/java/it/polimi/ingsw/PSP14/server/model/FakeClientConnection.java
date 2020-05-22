package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.core.messages.ChoiceMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.StringMessage;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.TCPClientConnection;

import java.io.IOException;
import java.util.List;

public class FakeClientConnection extends ClientConnection {
    private List<Message> messages;
    private int messageId;

    public FakeClientConnection() {
    }

    @Override
    protected void sendMessage(Message message) throws IOException {
    }

    @Override
    protected Message receiveMessage() throws IOException {
        return new ChoiceMessage(1);
    }

    @Override
    protected int receiveChoice() throws IOException {
        return 1;
    }

    @Override
    protected String receiveString() throws IOException {
        return "";
    }

    @Override
    public void close() throws IOException {

    }
}
