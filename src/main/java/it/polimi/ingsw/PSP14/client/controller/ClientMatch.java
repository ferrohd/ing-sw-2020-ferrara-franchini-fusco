package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.messages.ClientExecutableMessage;

import java.io.IOException;

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
        // Execute every new message that the client receives
        while(true) {
            try {
                ClientExecutableMessage inMessage  = (ClientExecutableMessage) serverConnection.receiveMessage();
                inMessage.execute(ui, serverConnection);
            } catch (IOException | InterruptedException e) {
                System.out.println("Connection lost. Closing...");
                try {
                    serverConnection.close();
                } catch(IOException ioe) {
                    System.out.println("Could not close. Terminating...");
                }
                System.exit(-1);
                return;
            }
        }
    }
}
