package it.polimi.ingsw.PSP14.server.controller;

/**
 * Class that continuously provides client connections.
 */
public interface ClientConnectionFactory extends Runnable {
    /**
     * Main function, used to obtain client connections of client who try to connect to the server.
     * @return a valid client connection
     * @throws InterruptedException if synchronization errors occur
     */
    ClientConnection getClientConnection() throws InterruptedException;

    /**
     * Used to manually add connections to be later retrieved through getClientConnection.
     * @param clientConnection the client connection
     */
    void addClientConnection(ClientConnection clientConnection);
}
