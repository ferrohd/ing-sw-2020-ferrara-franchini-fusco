package it.polimi.ingsw.PSP14.server;

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
                int gameSize = players.get(0).requestGameOptions();
                players.add(clientConnectionFactory.getClientConnection());
                if (gameSize == 3) {
                    players.add(clientConnectionFactory.getClientConnection());
                }
                // TODO: start game
            } catch(InterruptedException e) {
                players.forEach(ClientConnection::sendFatalError);
            }
        }
    }


}
