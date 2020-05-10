package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.ChoiceMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.StringMessage;

import java.io.*;
import java.net.Socket;

/**
 * ClientConnection implemented using TCP sockets.
 */
public class TCPClientConnection extends ClientConnection {
    private ObjectOutputStream clientOutput;
    private ObjectInputStream clientInput;

    public TCPClientConnection(final Socket socket) throws IOException {
        clientOutput = new ObjectOutputStream(socket.getOutputStream());
        clientInput = new ObjectInputStream(socket.getInputStream());
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
