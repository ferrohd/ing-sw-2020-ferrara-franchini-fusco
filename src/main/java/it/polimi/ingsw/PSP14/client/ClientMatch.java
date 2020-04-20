package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;

import java.net.Socket;

public class ClientMatch implements Runnable {
    private ServerConnection serverConnection;

    public ClientMatch(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public void run() {

    }
}
