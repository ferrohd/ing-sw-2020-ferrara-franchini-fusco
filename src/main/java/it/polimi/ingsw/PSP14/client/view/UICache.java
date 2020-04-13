package it.polimi.ingsw.PSP14.client.view;

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

class UIBlock {
    private int size;
    private boolean hasDome;
    private String worker;

    UIBlock() {
        size = 0;
        hasDome = false;
        worker = null;
    }

    public int getSize() {
        return size;
    }

    public boolean getHasDome() {
        return hasDome;
    }

    public void setDome() {
        hasDome = true;
    }

    public void unsetDome() {
        hasDome = false;
    }

    public String getWorker() {
        return worker;
    }

    public boolean hasWorker() {
        return worker != null;
    }

    public void setWorker(String newWorker) {
        if(newWorker == null) throw new NullPointerException();
        worker = newWorker;
    }

    public void unsetWorker() {
        worker = null;
    }

    public void incrementSize() { size++; }
    public void decrementSize() { size--; }
}
