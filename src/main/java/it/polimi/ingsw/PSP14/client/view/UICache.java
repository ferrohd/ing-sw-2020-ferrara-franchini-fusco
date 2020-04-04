package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.core.model.Point;


public class UICache {
    private UIBlock[][] board;

    public int getSize(Point pos) {
        return board[pos.getY()][pos.getY()].getSize();
    }

    public boolean getHasDome(Point pos) {
        return board[pos.getY()][pos.getY()].getHasDome();
    }

    public boolean hasWorker(Point pos) {
        return board[pos.getY()][pos.getY()].hasWorker();
    }

    public String getWorker(Point pos) {
        return board[pos.getY()][pos.getY()].getWorker();
    }

    public void setWorker(Point pos, String worker) { board[pos.getY()][pos.getY()].setWorker(worker); }

    public void unsetWorker(Point pos) { board[pos.getY()][pos.getY()].unsetWorker(); }

    public void incrementSize(Point pos) { board[pos.getY()][pos.getX()].incrementSize(); }

    public void decrementSize(Point pos) { board[pos.getY()][pos.getX()].decrementSize(); }

    public void setDome(Point pos) { board[pos.getY()][pos.getX()].setDome(); }
    public void unsetDome(Point pos) { board[pos.getY()][pos.getX()].unsetDome(); }
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
