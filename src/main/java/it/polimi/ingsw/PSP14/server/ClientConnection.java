package it.polimi.ingsw.PSP14.server;

/**
 * Generic connection to a client.
 * Exposes the functions for bidirectional communication with a client.
 */
public interface ClientConnection {
    int requestGameOptions();
    void sendFatalError();
}
