package it.polimi.ingsw.PSP14.server;

public interface ClientConnection {
    int requestGameOptions();
    void sendFatalError();
}
