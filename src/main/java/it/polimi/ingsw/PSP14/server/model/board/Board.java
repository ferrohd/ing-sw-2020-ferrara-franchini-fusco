package it.polimi.ingsw.PSP14.server.model.board;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A board for the game.
 * Contains information about all the cells and exposes functions to get and update their state.
 */
public class Board {
    private Cell[][] board = new Cell[5][5];
    private List<ClientConnection> clients = new ArrayList<>();

    public Board(List<ClientConnection> clients) {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                board[j][i] = new Cell();
            }
        }

        this.clients.addAll(clients);
    }

    public Board() {
        this(new ArrayList<>());
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return the tower size of the selected cell
     * @throws IndexOutOfBoundsException if the coordinates do not correspond to any cell
     */
    public int getTowerSize(Point pos) {
        return board[pos.getY()][pos.getX()].getTowerSize();
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @throws TowerSizeException when you can't increase a tower size
     * @throws IndexOutOfBoundsException when the cell is outside the board
     */
    public void incrementTowerSize(Point pos) throws TowerSizeException, IOException {
        board[pos.getY()][pos.getX()].incrementTowerSize();
        for(ClientConnection c : clients) c.notifyBuild(pos, 1);
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @throws IndexOutOfBoundsException if the cell is outside the board
     */
    public void setAsCompleted(Point pos) throws IOException {
        board[pos.getY()][pos.getX()].setAsCompleted();
        for(ClientConnection c : clients) c.notifyDome(pos);
    }

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return whether the tower is complete
     * @throws IndexOutOfBoundsException if the cell is outside the board
     */
    public boolean getIsCompleted(Point pos) {
        return board[pos.getY()][pos.getX()].getIsCompleted();
    }

    public static boolean isValidPos(Point pos) {
        return pos.getX() >= 0 && pos.getX() < 5 &&
                pos.getY() >= 0 && pos.getY() < 5;
    }
}
