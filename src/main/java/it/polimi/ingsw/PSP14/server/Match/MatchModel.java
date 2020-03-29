package it.polimi.ingsw.PSP14.server.Match;

import it.polimi.ingsw.PSP14.core.Action;
import it.polimi.ingsw.PSP14.core.Board;

import java.util.List;

/**
 * Model for a single match, server side.
 * The MatchModel contains references to the clients' connections.
 */
public class MatchModel {
    // private PlayerConnection[] playerConnections;
    private Player[] players;
    private Board board;
    private List<Action> history;
}
