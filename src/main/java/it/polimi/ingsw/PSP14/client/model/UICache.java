package it.polimi.ingsw.PSP14.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UICache {
    private final List<UIPlayer> players;
    private final UICell[][] board = new UICell[5][5];

    public UICache() {
        players = new ArrayList<>();
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
        this.players.add(new UIPlayer(username, color));
    }

    public void removePlayer(String username) {
        UIPlayer _player = getPlayer(username);
        // Remove workers first
        _player.getWorkers().forEach(UIWorker::remove);
        // Then remove the player
        this.players.remove(_player);
    }

    public List<UIPlayer> getPlayers() {
        return this.players;
    }

    public UIPlayer getPlayer(String username) {
        return this.players.stream()
                .filter(p -> p.getUsername().equals(username))
                .collect(Collectors.toList())
                .get(0);
    }
}
