package it.polimi.ingsw.PSP14.core.controller;

import it.polimi.ingsw.PSP14.server.ClientConnection;
import it.polimi.ingsw.PSP14.server.Match.MatchModel;

import java.util.ArrayList;
import java.util.List;

public class Match implements Runnable {
    private List<ClientConnection> clientConnections;
    private MatchModel matchModel;

    public Match(List<ClientConnection> clientConnections) {
        this.clientConnections = new ArrayList<>(clientConnections);
    }

    public void run() {
        gameSetup();
        gameLoop();
    }

    public void gameSetup() {
        matchModel = new MatchModel();
    }

    public void gameLoop() {
        while(true) {

        }
    }
}
