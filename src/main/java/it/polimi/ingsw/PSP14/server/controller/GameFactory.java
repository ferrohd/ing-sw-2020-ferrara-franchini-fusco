package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.LobbySizeMessage;
import it.polimi.ingsw.PSP14.server.model.Match;

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
                players.get(0).sendNotification("You are the room leader!");
                System.out.println("Room leader found.");
                int choice = players.get(0).askLobbySize();
                System.out.println("Room size is: " + choice + ".");
                players.add(clientConnectionFactory.getClientConnection());
                players.get(1).sendNotification("Game found!");
                System.out.println("Found player 2.");
                if (choice == 3) {
                    players.add(clientConnectionFactory.getClientConnection());
                    players.get(2).sendNotification("Game found!");
                    System.out.println("Found player 3.");
                }

                // Starts a new game lobby/match with the players in the arrayList
                System.out.println("Creating game...");
                Thread newGame = new Thread(new Match(players));
                System.out.println("Starting game...");
                newGame.start();
            } catch(InterruptedException | IOException e) {
                System.out.println("Error occurred during room setup. All clients in the room will be disconnected.");
                for(ClientConnection c : players)
                    try {
                        c.close();
                    } catch (IOException exc) {
                        System.out.println("Error occurred closing a client connection.");
                    }
            }
        }
    }


}
