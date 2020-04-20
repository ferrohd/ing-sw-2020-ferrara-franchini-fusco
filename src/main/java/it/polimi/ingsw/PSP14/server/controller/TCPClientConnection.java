package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.ChoiceMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.StringMessage;
import it.polimi.ingsw.PSP14.server.actions.Action;

import java.io.*;
import java.net.Socket;

/**
 * ClientConnection implemented using TCP sockets.
 */
public class TCPClientConnection implements ClientConnection {
    private final Socket clientSocket;
    private ObjectOutputStream clientOutput;
    private ObjectInputStream clientInput;

    public TCPClientConnection(final Socket socket) {
        clientSocket = socket;
        try {
            clientOutput = new ObjectOutputStream(socket.getOutputStream());
            clientInput = new ObjectInputStream(socket.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public void sendFatalError() {
        // TODO
    }

    @Override
    public String getPlayerUsername() {
        // TODO: Implement function
        // Use a cache, as username should not change
        return "";
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        clientOutput.writeObject(message);
    }

    @Override
    public Message receiveMessage() throws IOException {
        try {
            Message message = (Message) clientInput.readObject();
            return message;
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }
    }

    @Override
    public int receiveChoice() throws IOException {
        ChoiceMessage choice = (ChoiceMessage) receiveMessage();

        return choice.getIndex();
    }

    @Override
    public String receiveString() throws IOException {
        StringMessage string = (StringMessage) receiveMessage();

        return string.getString();
    }
}
