package it.polimi.ingsw.PSP14.server.controller;

/**
 * Creates client connections.
 */
public interface ClientConnectionFactory extends Runnable {
    public ClientConnection getClientConnection() throws InterruptedException;
}
