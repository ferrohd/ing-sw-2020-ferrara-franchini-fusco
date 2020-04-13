package it.polimi.ingsw.PSP14.server.model;

/**
 * Contains information about a single cell of the board.
 */
public class Cell {
    private int towerSize;
    private boolean isCompleted;

    public Cell() {
        towerSize = 0;
        isCompleted = false;
    }

    /**
     * @return the current tower size.
     */
    public int getTowerSize() {
        return towerSize;
    }

    /**
     * @return a truth value indicating if the dome has been built.
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Increases by one the size of the tower.
     * @throws TowerSizeException when you try to increase a tower of 3 blocks.
     */
    public void incrementTowerSize() throws TowerSizeException{
        if (towerSize < 3) {
            towerSize += 1;
        } else throw new TowerSizeException();
    }

    /**
     * Builds the dome, marking the tower as completed.
     */
    public void setAsCompleted() {
        isCompleted = true;
    }
}
