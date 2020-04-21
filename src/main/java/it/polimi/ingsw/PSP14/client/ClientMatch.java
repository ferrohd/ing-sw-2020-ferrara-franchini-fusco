package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.messages.ClientExecutableMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;

import java.io.IOException;
import java.net.Socket;

/**
 * This class handles the basic logic behind a match on the client,
 * processing commands received from the server, and sending
 * back responses to it.
 */
public class ClientMatch implements Runnable {
    private ServerConnection serverConnection;
    private UI ui;

    /**
     * Constructor for ClientMatch.
     * @param serverConnection a connection to send and receive messages.
     * @param ui a UI to represent the game state
     */
    public ClientMatch(ServerConnection serverConnection, UI ui) {
        this.serverConnection = serverConnection;
        this.ui = ui;
    }

    @Override
    public void run() {
        while(true) {
            ClientExecutableMessage inMessage = null;
            try {
                inMessage = (ClientExecutableMessage) serverConnection.receiveMessage();
            } catch (IOException e) {
                System.out.println("Connection lost. Closing...");
                System.exit(-1);
            }

            inMessage.execute(ui, serverConnection);
        }
    }
}
