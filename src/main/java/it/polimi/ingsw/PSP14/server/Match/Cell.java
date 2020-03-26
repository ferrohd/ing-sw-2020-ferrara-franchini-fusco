package it.polimi.ingsw.PSP14.server.Match;

/**
 * Contains information about a single cell of the board.
 */
public class Cell {
    private int towerSize = 0;
    private boolean isCompleted = false;

    /**
     * @return the current tower size.
     */
    public int getTowerSize() {
        return towerSize;
    }

    /**
     * @return a truth value indicating if the dome is built.
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Increases by one the size of the tower.
     */
    public void incrementTowerSize() {
        towerSize++;
    }

    /**
     * Builds the dome.
     */
    public void setAsCompleted() {
        isCompleted = true;
    }
}
