package it.polimi.ingsw.PSP14.server;

import it.polimi.ingsw.PSP14.server.controller.ClientConnectionFactory;
import it.polimi.ingsw.PSP14.server.controller.GameFactory;
import it.polimi.ingsw.PSP14.server.controller.TCPClientConnectionFactory;

import java.io.IOException;

/**
 * The entry point of the server.
 */
public class Server {
    public static void main(String[] args) throws InterruptedException, IOException {
        ClientConnectionFactory clientConnectionFactory = new TCPClientConnectionFactory(42069);
        GameFactory gameFactory = new GameFactory(clientConnectionFactory);
        Thread mainThread = new Thread(gameFactory);
        mainThread.setName("gameFactory");
        mainThread.start();
        mainThread.join();
    }
}
