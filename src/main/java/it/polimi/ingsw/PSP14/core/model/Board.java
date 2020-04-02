package it.polimi.ingsw.PSP14.core.model;

/**
 * A board for the game.
 * Contains information about all the cells and exposes functions to get and update their state.
 */
public class Board {
    private Cell[][] board = new Cell[5][5];

    public Board() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                board[j][i] = new Cell();
            }
        }
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return the tower size of the selected cell
     * @throws IndexOutOfBoundsException if the coordinates do not correspond to any cell
     */
    public int getTowerSize(int x, int y) {
        return board[y][x].getTowerSize();
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @throws TowerSizeException when you can't increase a tower size
     * @throws IndexOutOfBoundsException when the cell is outside the board
     */
    public void incrementTowerSize(int x, int y) throws TowerSizeException {
        board[y][x].incrementTowerSize();
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @throws IndexOutOfBoundsException if the cell is outside the board
     */
    public void setAsCompleted(int x, int y) {
        board[y][x].setAsCompleted();
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return whether the tower is complete
     * @throws IndexOutOfBoundsException if the cell is outside the board
     */
    public boolean getIsCompleted(int x, int y) {
        return board[y][x].getIsCompleted();
    }
}
