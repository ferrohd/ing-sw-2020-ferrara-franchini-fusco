package it.polimi.ingsw.PSP14.client;

import java.io.*;
import java.net.Socket;

import it.polimi.ingsw.PSP14.core.messages.Message;

/**
 * ServerConnection implemented using TCP sockets.
 */
public class TCPServerConnection implements ServerConnection {
    private final Socket serverSocket;
    private ObjectOutputStream serverOutput;
    private ObjectInputStream serverInput;

    /**
     * Constructor of the connection.
     * @param socket the socket on the server we need to connect to.
     */
    public TCPServerConnection(final Socket socket) {
        serverSocket = socket;
        try {
            serverOutput = new ObjectOutputStream(socket.getOutputStream());
            serverInput = new ObjectInputStream(socket.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public void sendFatalError() {
        // TODO
    }

    @Override
    public void sendMessage(final Message message) throws IOException {
        serverOutput.writeObject(message);
    }

    @Override
    public Message receiveMessage() throws IOException {
        try {
            return (Message) serverInput.readObject();
        } catch (final ClassNotFoundException e) {
            throw new IOException();
        }
    }
}
