package it.polimi.ingsw.PSP14.server.match;

import it.polimi.ingsw.PSP14.core.model.PlayerNotFoundException;
import it.polimi.ingsw.PSP14.core.model.actions.Action;
import it.polimi.ingsw.PSP14.core.Player;
import it.polimi.ingsw.PSP14.core.model.Board;

import java.util.*;

/**
 * Model for a single match, server side.
 * The MatchModel contains references to the clients' connections.
 */
public class MatchModel {
    private HashMap<String, Player> players = new HashMap<>();
    private Board board;
    private List<Action> history;

    /**
     * Constructor of MatchModel.
     * Since players don't require any previous data except for username,
     * the order of setup doesn't matter.
     * @param usernames a Set of each player's username
     */
    public MatchModel(Set<String> usernames) {
        // Init players
        for (String username : usernames) {
            players.put(username, new Player(username));
        }
        // Init board
        this.board = new Board();
        // Init history
        history = new ArrayList<>();
    }

    public void addActionToHistory(Action action) {
        history.add(action);
    }

    public List<Action> getHistory() {
        return history;
    }

    /**
     * Retrieve a Player object by username.
     * @param username the username of the Player
     * @return the associated Player
     * @throws PlayerNotFoundException when the player is not found
     */
    public Player getPlayerByUsername(String username) throws PlayerNotFoundException {
        Player player = players.get(username);
        if (player == null)
            throw new PlayerNotFoundException();
        else return player;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<Player>(players.values());
    }

    public Board getBoard() {
        return board;
    }
}
