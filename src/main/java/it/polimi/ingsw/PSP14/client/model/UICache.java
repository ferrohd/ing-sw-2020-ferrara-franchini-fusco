package it.polimi.ingsw.PSP14.client.model;

import it.polimi.ingsw.PSP14.server.model.Point;


public class UICache {
    private UIBlock[][] board;

    public UICache() {
        board = new UIBlock[5][5];
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                board[i][j] = new UIBlock();
            }
        }
    }

    public UIBlock getBlock(Point pos) {
        return board[pos.getY()][pos.getX()];
    }
}