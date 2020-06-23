package it.polimi.ingsw.PSP14.server.model.board;

import it.polimi.ingsw.PSP14.server.controller.MatchController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The board of the game containing information about all the cells and exposing functions to get and update their state.
 */
public class Board {
    private final Cell[][] board = new Cell[5][5];
    private final MatchController controller;

    /**
     * Constructs a board of empty cells.
     *
     * @param controller the controller of the current match
     */
    public Board(MatchController controller) {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                board[j][i] = new Cell();
            }
        }

        this.controller = controller;
    }

    /**
     * Get the height of a tower, in number of levels.
     *
     * @param pos the coordinates of the cell
     * @return the tower size of the selected cell
     */
    public int getTowerSize(Point pos) {
        return board[pos.getY()][pos.getX()].getTowerSize();
    }

    /**
     * Increase by one level the height of a tower.
     * @param pos the coordinates of the cell
     * @throws TowerSizeException when you can't increase a tower size
     * @throws IOException if it cant notify the controller
     */
    public void incrementTowerSize(Point pos) throws TowerSizeException, IOException {
        board[pos.getY()][pos.getX()].incrementTowerSize();
        controller.notifyBuild(pos);
    }

    /**
     * Mark the selected tower as complete, meaning it has to be treated as
     * if there was a dome on it.
     *
     * @param pos the coordinates of the cell
     * @throws IOException if it can't notify the controller
     */
    public void setAsCompleted(Point pos) throws IOException {
        board[pos.getY()][pos.getX()].setAsCompleted();
        controller.notifyDome(pos);
    }

    /**
     * Returns true if the tower is completed (has a dome on it), false otherwise.
     *
     * @param pos the coordinates of the cell
     * @return whether the tower is complete
     * @throws IndexOutOfBoundsException if the cell is outside the board
     */
    public boolean getIsCompleted(Point pos) {
        return board[pos.getY()][pos.getX()].getIsCompleted();
    }

    /**
     * Tells if the provided Point is inside the board
     * @param pos the Point to check
     * @return the validity of the point
     */
    public static boolean isValidPos(Point pos) {
        return pos.getX() >= 0 && pos.getX() < 5 &&
                pos.getY() >= 0 && pos.getY() < 5;
    }
}
