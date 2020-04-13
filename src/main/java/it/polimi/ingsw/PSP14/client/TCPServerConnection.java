package it.polimi.ingsw.PSP14.client;

import java.io.IOException;
import java.net.Socket;

import it.polimi.ingsw.PSP14.core.Message;
import it.polimi.ingsw.PSP14.core.actions.Action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ServerConnection implemented using TCP sockets.
 */
public class TCPServerConnection implements ServerConnection {
    private final Socket serverSocket;
    private DataOutputStream serverOutput;
    private DataInputStream serverInput;

    public TCPServerConnection(final Socket socket) {
        serverSocket = socket;
        try {
            serverOutput = new DataOutputStream(socket.getOutputStream());
            serverInput = new DataInputStream(socket.getInputStream());
        } catch (final IOException e) {
            // TODO: Make server crash
            e.printStackTrace();
        }

    }

    public void sendFatalError() {
        // TODO
    }

    @Override
    public void sendMessage(final Message message) throws IOException {
        // Serialize the message
        final ObjectOutputStream serializedMessage = new ObjectOutputStream(serverOutput);
        serializedMessage.writeObject(message);
        serializedMessage.close();
        // And send it to the server
        serverOutput.flush();
    }

    @Override
    public Message receiveMessage() throws IOException {
        try {
            final ObjectInputStream deserializedMessage = new ObjectInputStream(serverInput);
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
