package it.polimi.ingsw.PSP14.server.model;

import java.util.Random;

import it.polimi.ingsw.PSP14.server.model.gods.God;

/**
 * Model for a player in the game.
 */
public class Player {
    private String username;
    private God god;
    private Worker[] workers = new Worker[2];

    /**
     * @param username username of the player to display in game
     */
    public Player(String username, God god) {
        if (username == null || username.equals("")) throw new NullPointerException();
        this.username = username;
        this.god = god;
    }

    /**
     * @param index index of the worker to move
     * @param dir direction of movement
     * @throws IndexOutOfBoundsException if the index does not correspond to any worker
     */
    public void moveWorker(int index, Direction dir) throws IndexOutOfBoundsException {
        workers[index].move(dir);
    }

    /**
     * Create, or update position, of a worker, by specifying its index
     * (0 or 1) and a new position.
     * @param index {0, 1} the index of the worker
     * @param position the position of the worker
     */
    public void setWorker(int index, Point position) {
        if (workers[index] == null)
            workers[index] = new Worker(position);
        else
            workers[index].setPos(position);
    }

    public Worker getWorker(int index) {
        return workers[index];
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    public God getGod() {
        return god;
    }
}
