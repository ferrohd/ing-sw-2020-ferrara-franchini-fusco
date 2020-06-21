package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.net.TCPIn;
import it.polimi.ingsw.PSP14.core.net.TCPOut;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ServerConnection implemented using TCP sockets.
 */
public class TCPServerConnection implements ServerConnection {
    private final TCPOut serverOutput;
    private final TCPIn serverInput;

    /**
     * Constructor of the connection.
     * @param socket the socket on the server we need to connect to.
     * @throws IOException if a connection error occurs
     */
    public TCPServerConnection(final Socket socket) throws IOException {
        serverOutput = new TCPOut(new ObjectOutputStream(socket.getOutputStream()));
        serverInput = new TCPIn(new ObjectInputStream(socket.getInputStream()));
        new Thread(serverInput).start();
        new Thread(serverOutput).start();
    }

    @Override
    public void sendMessage(final Message message) throws IOException {
        serverOutput.sendMessage(message);
    }

    @Override
    public Message receiveMessage() throws IOException {
        return serverInput.receiveMessage();
    }

    @Override
    public void close() throws IOException {
        serverInput.close();
        serverOutput.close();
    }
}
