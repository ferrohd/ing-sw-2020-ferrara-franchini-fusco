package it.polimi.ingsw.PSP14.client.view.cli;

import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

/**
 * This class reproduce locally the basic features of the server in order to
 * maintain a consistent state of the game between server and client. It handles
 * the players, the workers and the board.
 */
public class UICache {
    private final Map<String, UIPlayer> players;
    private final UICell[][] board = new UICell[5][5];

    public UICache() {
        players = new HashMap<>();
        for(int y = 0; y < 5; ++y) {
            for(int x = 0; x < 5; ++x) {
                board[y][x] = new UICell(x, y);
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
    public UICell getCell(Point position) {
        return getCell(position.getX(), position.getY());
    }

    /**
     * Add a player to the match.
     * @param username username of the player
     * @param number unique id of the player
     * @param color unique (not necessarily) color of the player
     */
    public void addPlayer(String username, int number, UIColor color) {
        this.players.put(username, new UIPlayer(username, number, color));
    }

    /**
     * Remove a player from the match. This will effectively wipe
     * the player and all of its workers from the game.
     * @param username the username of the player to remove
     */
    public void removePlayer(String username) {
        UIPlayer _player = getPlayer(username);
        // Remove workers first
        _player.getWorkers().forEach(UIWorker::remove);
        // Then remove the player
        this.players.remove(username);
    }

    /**
     * @return an ordered List of all Players
     */
    public List<UIPlayer> getPlayers() {
        List<UIPlayer> _list = new ArrayList<>(this.players.values());
        _list.sort(comparing(UIPlayer::getNumber));
        return _list;
    }

    /**
     * @param username name of the player to be returned
     * @return the player
     */
    public UIPlayer getPlayer(String username) {
        return this.players.get(username);
    }

    /**
     * Updates the cache by setting a worker to a cell
     * @param worker to be set
     * @param player owner of the worker
     * @param cell where the worker should be set to
     */
    public void setWorker(UIWorker worker, String player, UICell cell) {
        if (cell == null) return;

        if (cell.getWorker() != worker) {
            // Remove the worker on target cell
            unsetWorker(cell.getWorker());
        }
        // Free the new worker
        unsetWorker(worker);
        // Set it to target cell
        getPlayer(player).setWorker(worker);
        worker.setCell(cell);
        cell.setWorker(worker);
    }

    /**
     * Remove a worker from the Board
     * @param worker to be removed from the board
     */
    public void unsetWorker(UIWorker worker) {
        if (worker == null) return;
        UICell _c = worker.getCell();
        if (_c != null) _c.unsetWorker(); // remove worker ref from cell
        worker.unsetCell(); // remove cell ref from worker
    }
}
