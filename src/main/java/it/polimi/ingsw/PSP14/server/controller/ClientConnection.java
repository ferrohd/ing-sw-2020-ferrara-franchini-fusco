package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.UsernameMessage;
import it.polimi.ingsw.PSP14.server.actions.Action;

import java.io.IOException;
import java.util.List;

/**
 * Generic connection to a client.
 * Exposes the functions for bidirectional communication with a client.
 */
public abstract class ClientConnection {
    private String username = null;

    /**
     * Serialize and send a message to the client.
     */
    public abstract void sendMessage(Message message) throws IOException;

    /**
     * Receive a message from the client.
     */
    public abstract Message receiveMessage() throws IOException;

    /**
     * A request to the client to provide the name that the player has chosen.
     * @return the player username
     */
    public String getUsername() throws IOException {
        if (username == null) {
            Message message = new UsernameMessage();
            sendMessage(message);
            username = receiveString();
        }

        return username;
    }

    public abstract int receiveChoice() throws IOException;

    public abstract String receiveString() throws IOException;

    public static void sendAll(List<ClientConnection> clients, Message message) throws IOException {
        for(int i = 0; i < clients.size(); ++i) {
            clients.get(i).sendMessage(message);
        }
    }
}
