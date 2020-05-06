package it.polimi.ingsw.PSP14.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void addPlayer(String username, UIColor color) {
        this.players.put(username, new UIPlayer(username, color));
    }

    public void removePlayer(String username) {
        UIPlayer _player = getPlayer(username);
        // Remove workers first
        _player.getWorkers().forEach(UIWorker::remove);
        // Then remove the player
        this.players.remove(username);
    }

    public List<UIPlayer> getPlayers() {
        return new ArrayList<>(this.players.values());
    }

    public UIPlayer getPlayer(String username) {
        return this.players.get(username);
    }
}
