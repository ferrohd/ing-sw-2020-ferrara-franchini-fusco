package it.polimi.ingsw.PSP14.client.model;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class UICache {
    private final Map<String, UIPlayer> players;
    private final UICell[][] board = new UICell[5][5];

    public UICache() {
        players = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                board[i][j] = new UICell();
            }
        }
    }

    /**
     * Get a cell from the board.
     * @param x coordinate
     * @param y coordinate
     * @return the cell at x,y coordinates
     */
    public UICell getCell(int x, int y) {
        return this.board[y][x];
    }

    /**
     * Get a cell from the board.
     * @param position a UIPoint representing the coordinates
     *                 of the cell.
     * @return the cell at x,y coordinates
     */
    public UICell getCell(UIPoint position) {
        return getCell(position.getX(), position.getY());
    }

    /**
     * Add a player to the match.
     * @param username
     * @param number
     * @param color
     */
    public void addPlayer(String username, int number, UIColor color) {
        this.players.put(username, new UIPlayer(username, number, color));
    }

    /**
     * Remove a player from the match. This will effectively wipe
     * the player and all of it's workers from the game.
     * @param username
     */
    public void removePlayer(String username) {
        UIPlayer _player = getPlayer(username);
        // Remove workers first
        _player.getWorkers().forEach(UIWorker::remove);
        // Then remove the player
        this.players.remove(username);
    }

    public List<UIPlayer> getPlayers() {
        List<UIPlayer> _list = new ArrayList<>(this.players.values());
        _list.sort(comparing(UIPlayer::getNumber));
        return _list;
    }

    public UIPlayer getPlayer(String username) {
        return this.players.get(username);
    }
}
