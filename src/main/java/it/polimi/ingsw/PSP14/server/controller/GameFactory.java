package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.LobbySizeMessage;

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
            System.out.println("Creating new room...");
            players = new ArrayList<>();
            try {
                players.add(clientConnectionFactory.getClientConnection());
                System.out.println("Room leader found.");
                Message message = new LobbySizeMessage();
                players.get(0).sendMessage(message);
                int choice = players.get(0).receiveChoice();
                System.out.println("Room size is: " + choice + ".");
                players.add(clientConnectionFactory.getClientConnection());
                System.out.println("Found player 2.");
                if (choice == 3) {
                    players.add(clientConnectionFactory.getClientConnection());
                    System.out.println("Found player 3.");
                }

                // Starts a new game lobby/match with the players in the arrayList
                System.out.println("Creating game...");
                Thread newGame = new Thread(new MatchController(players));
                System.out.println("Starting game...");
                newGame.start();
            } catch(InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }


}
