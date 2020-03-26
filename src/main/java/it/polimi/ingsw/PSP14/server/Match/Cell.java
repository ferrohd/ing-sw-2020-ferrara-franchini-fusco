package it.polimi.ingsw.PSP14.server.Match;

public class Cell {
    private int towerSize = 0;
    private boolean isCompleted = false;

    public int getTowerSize() {
        return towerSize;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void incrementTowerSize() {
        towerSize++;
    }

    public void setAsCompleted() {
        isCompleted = true;
    }
}
