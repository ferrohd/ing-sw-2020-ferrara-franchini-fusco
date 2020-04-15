package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.server.actions.Action;
import it.polimi.ingsw.PSP14.server.model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ClientConnection implemented using TCP sockets.
 */
public class TCPClientConnection implements ClientConnection {
    private final Socket clientSocket;
    private DataOutputStream clientOutput;
    private DataInputStream clientInput;

    public TCPClientConnection(final Socket socket) {
        clientSocket = socket;
        try {
            clientOutput = new DataOutputStream(socket.getOutputStream());
            clientInput = new DataInputStream(socket.getInputStream());
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
    public void sendAction(final Action action) throws IOException {
        sendMessage(action);
    }

    @Override
    public Action receiveAction() throws IOException {
        
        Message msg;
        while (!((msg = receiveMessage()) instanceof Action));
       
        return (Action) msg;
    }
}
