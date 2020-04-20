package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.RoomSizeMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Runnable to be run in a thread.
 *
 * Continuously creates and starts game threads with the connections provided by a ClientConnectionFactory.
 */
public class GameFactory implements Runnable {
    private ClientConnectionFactory clientConnectionFactory;

    /**
     * @param clientConnectionFactory factory that provides the ClientConnection objects
     */
    public GameFactory(ClientConnectionFactory clientConnectionFactory) {
        this.clientConnectionFactory = clientConnectionFactory;
        Thread clientConnectionThread = new Thread(clientConnectionFactory);
        clientConnectionThread.start();
    }

    public void run() {
        createGameLoop();
    }

    /**
     * Creates and starts games.
     */
    private void createGameLoop() {
        List<ClientConnection> players;
        while(true) {
            players = new ArrayList<>();
            try {
                players.add(clientConnectionFactory.getClientConnection());
                Message message = new RoomSizeMessage();
                players.get(0).sendMessage(message);
                int choice = players.get(0).receiveChoice();
                players.add(clientConnectionFactory.getClientConnection());
                if (choice == 3) {
                    players.add(clientConnectionFactory.getClientConnection());
                }

                // Starts a new game lobby/match with the players in the arrayList
                Thread newGame = new Thread(new MatchController(players));
                newGame.start();
            } catch(InterruptedException | IOException e) {
                players.forEach(ClientConnection::sendFatalError);
            }
        }
    }


}
