package it.polimi.ingsw.PSP14.server.Match;

import java.util.List;

/**
 * Model for a single match, server side.
 * Contains references to the client connections
 */
public class MatchModel {
    // private PlayerConnection[] playerConnections;
    private Player players[];
    private Board board;
    private List<Action> history;
}
