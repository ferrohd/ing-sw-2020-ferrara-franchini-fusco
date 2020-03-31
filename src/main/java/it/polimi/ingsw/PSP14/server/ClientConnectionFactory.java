package it.polimi.ingsw.PSP14.server;

public interface ClientConnectionFactory extends Runnable {
    public ClientConnection getClientConnection() throws InterruptedException;
}
