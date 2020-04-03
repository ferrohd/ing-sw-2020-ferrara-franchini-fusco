package it.polimi.ingsw.PSP14.core;

import it.polimi.ingsw.PSP14.core.model.Action;
import it.polimi.ingsw.PSP14.core.model.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a single match, server side.
 * The MatchModel contains references to the clients' connections.
 */
public class MatchModel {
    private List<Player> players;
    private Board board;
    private List<Action> history;

    public MatchModel() {
        players = new ArrayList<>();
        board = new Board();
        history = new ArrayList<>();
    }
}
