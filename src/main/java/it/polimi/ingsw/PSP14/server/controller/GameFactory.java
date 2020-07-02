package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.server.model.MatchModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Runnable to be run in a thread.
 *
 * Continuously creates and starts game threads with the connections provided by a ClientConnectionFactory.
 */
public class GameFactory implements Runnable {
    private static final String NEW_ROOM = "Creating new room...";
    private static final String ROOM_LEADER = "You are the room leader!";
    private static final String PLAYERS_TO_CONNECT = "Waiting for other players to connect...";
    private static final String PLAYER_2_OF = "Game found! You are player 2 of ";
    private static final String PLAYER_2_FOUND = "Player 2 found!";
    private static final String GAME_FOUND = "Game found!";
    private static final String BACK_IN_QUEUE = "An error occurred. You will be put back in queue";
    private static final String ERROR_WHILE_RECYCLING_CLIENTS = "Error while recycling clients";

    private static final String LEADER_FOUND = "Room leader found";
    private static final String ROOM_SIZE_IS = "Room size is: ";
    private static final String FOUND_PLAYER_2 = "Found player 2";
    private static final String FOUND_PLAYER_3 = "Found player 3";
    private static final String CREATING_GAME = "Creating game...";
    private static final String STARTING_GAME = "Starting game...";
    private static final String IN_QUEUE = "Error occurred during room setup. Connected players will be put in queue";

    private final ClientConnectionFactory clientConnectionFactory;

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
            System.out.println(NEW_ROOM);
            players = new ArrayList<>();
            try {
                players.add(clientConnectionFactory.getClientConnection());
                players.get(0).sendNotification(ROOM_LEADER);
                System.out.println(LEADER_FOUND);
                int choice = players.get(0).askLobbySize();
                System.out.println(ROOM_SIZE_IS + choice);
                players.get(0).sendNotification(PLAYERS_TO_CONNECT);
                players.add(clientConnectionFactory.getClientConnection());
                players.get(1).sendNotification(PLAYER_2_OF + choice);
                players.get(0).sendNotification(PLAYER_2_FOUND);
                System.out.println(FOUND_PLAYER_2);
                if (choice == 3) {
                    players.add(clientConnectionFactory.getClientConnection());
                    players.get(2).sendNotification(GAME_FOUND);
                    System.out.println(FOUND_PLAYER_3);
            }

                // Starts a new game lobby/match with the players in the arrayList
                System.out.println(CREATING_GAME);
                for(ClientConnection c : players) c.ping();
                MatchController controller = new MatchController(players);
                Thread newGame = new Thread(new MatchModel(controller));
                newGame.setName("Match");
                System.out.println(STARTING_GAME);
                newGame.start();
            } catch(InterruptedException | IOException e) {
                e.printStackTrace();
                try {
                    for (ClientConnection c : players) c.close();
                } catch (IOException ignore) {}
            }
        }
    }
}
