package it.polimi.ingsw.PSP14.client.model;

public class UIBlock {
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

