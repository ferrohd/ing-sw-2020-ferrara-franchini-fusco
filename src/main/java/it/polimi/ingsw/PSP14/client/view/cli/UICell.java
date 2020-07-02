package it.polimi.ingsw.PSP14.client.view.cli;

import it.polimi.ingsw.PSP14.server.model.board.Point;

/**
 * A class that represent a cell on the board.
 * Each cell has <code>towerHeight</code> which represent
 * the height of the tower on it (0 = no tower, 3 = full height).
 * A tower can be completed by setting <code>dome</code> equals to
 * true.
 * Each cell also has a reference to its potentially assigned worker,
 * which may be null if there isn't any worker on it.
 */
public class UICell {
    private final int x, y;
    private int towerHeight;
    private boolean dome;
    private UIWorker worker;

    public UICell(int x, int y) {
        this.x = x;
        this.y = y;
        this.towerHeight = 0;
        this.dome = false;
        this.worker = null;
    }

    /**
     * Get the tower height on this cell.
     *
     * @return the height of the tower
     */
    public int getTowerHeight() {
        return towerHeight;
    }

    /**
     * Increase the height of the tower by 1.
     */
    public void incrementTowerHeight() {
        this.towerHeight += 1;
    }

    /**
     * Check whether the tower has a dome on it.
     *
     * @return true if there's a dome, false if there is not one
     */
    public boolean hasDome() {
        return dome;
    }

    /**
     * Pass a boolean to mark this cell tower as completed or not.
     *
     * @param dome whether the tower is completed or not
     */
    public void setDome(boolean dome) {
        this.dome = dome;
    }

    /**
     * Get the worker assigned to this cell.
     *
     * @return the worker (<code>null</code> if there's no worker)
     */
    public UIWorker getWorker() {
        return worker;
    }

    /**
     * Check whether there's a worker assigned to this cell.
     *
     * @return false if there's no worker, true if there is one.
     */
    public boolean hasWorker() {
        return worker != null;
    }

    /**
     * Assign a player's worker to this cell.
     *
     * @param worker a reference to a player's worker.
     */
    public void setWorker(UIWorker worker) {
        this.worker = worker;
    }

    /**
     * Remove a player's worker from this cell.
     * This equals to removing a piece from the board
     * such as during a move, but not as in "removing"
     * the piece from the match.
     */
    public void unsetWorker() {
        this.worker = null;
    }

    /**
     * @return X coordinate of the Cell
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y coordinate of the Cell
     */
    public int getY() {
        return y;
    }

    /**
     * @return a Point where the cell is located
     */
    public Point getPoint() {
        return new Point(x, y);
    }
}
