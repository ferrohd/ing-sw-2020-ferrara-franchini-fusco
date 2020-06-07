package it.polimi.ingsw.PSP14.server.controller;

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
                players.get(0).sendNotification("Waiting for other players to connect...");
                players.add(clientConnectionFactory.getClientConnection());
                players.get(1).sendNotification("Game found! You are player 2 of " + choice);
                players.get(0).sendNotification("Player 2 found!");
                System.out.println("Found player 2.");
                if (choice == 3) {
                    players.add(clientConnectionFactory.getClientConnection());
                    players.get(2).sendNotification("Game found!");
                    System.out.println("Found player 3.");
            }

                // Starts a new game lobby/match with the players in the arrayList
                System.out.println("Creating game...");
                for(ClientConnection c : players) c.ping();
                Thread newGame = new Thread(new Match(players));
                newGame.setName("Match");
                System.out.println("Starting game...");
                newGame.start();
            } catch(InterruptedException | IOException e) {
                System.out.println("Error occurred during room setup. Connected players will be put in queue.");
                recycleConnections(players);
            }
        }
    }

    private void recycleConnections(List<ClientConnection> clients) {
        for(ClientConnection c : clients) {
            try {
                c.ping();
                c.sendNotification("An error occurred. You will be put back in queue.");
                clientConnectionFactory.addClientConnection(c);
            } catch(IOException e) {}
        }
    }


}
