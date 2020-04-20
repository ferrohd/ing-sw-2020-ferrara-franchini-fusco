package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.ChoiceMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.server.actions.Action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            // TODO: Make server crash
            e.printStackTrace();
        }

    }

    public int requestGameOptions() {
        return 3;
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
    public void sendMessage(final Message message) throws IOException {
        // Serialize the message
        final ObjectOutputStream serializedMessage = new ObjectOutputStream(clientOutput);
        serializedMessage.writeObject(message);
        serializedMessage.close();
        // And send it to the client
        clientOutput.flush();
    }

    @Override
    public Message receiveMessage() throws IOException {
        try {
            final ObjectInputStream deserializedMessage = new ObjectInputStream(clientInput);
            final Object obj = deserializedMessage.readObject();

            return (Message) obj;
        } catch (final ClassNotFoundException e) {
            throw new IOException();
        }
    }

    @Override
    public int receiveChoice() throws IOException {
        ChoiceMessage choice;
        try {
            choice = (ChoiceMessage) clientInput.readObject();
        } catch(ClassNotFoundException e) {
            return -1;
        }

        return choice.getIndex();
    }
}
