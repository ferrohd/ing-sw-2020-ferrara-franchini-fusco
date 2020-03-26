package it.polimi.ingsw.PSP14.server.Match;

public class Board {
    private Cell[][] board = new Cell[5][5];

    Board() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                board[j][i] = new Cell();
            }
        }
    }

    public int getTowerSize(int x, int y) throws IndexOutOfBoundsException {
        return board[y][x].getTowerSize();
    }

    public void incrementTowerSize(int x, int y) {
        board[y][x].incrementTowerSize();
    }

    public void setAsCompleted(int x, int y) {
        board[y][x].setAsCompleted();
    }

    public boolean getIsCompleted(int x, int y) {
        return board[y][x].getIsCompleted();
    }
}
