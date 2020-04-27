package it.polimi.ingsw.PSP14.server.model;

import java.awt.Color;
import java.util.Random;

import it.polimi.ingsw.PSP14.server.model.gods.God;

/**
 * Model for a player in the game.
 */
public class Player {
    private String username;
    private God god;
    private Worker[] workers = new Worker[2];
    private Color color;

    /**
     * @param username username of the player to display in game
     */
    public Player(String username, God god) {
        if (username == null || username.equals("")) throw new NullPointerException();
        this.username = username;
        this.god = god;

        setPlayerColor();
    }

    /**
     *  Set the player color to a random one
     */
    private void setPlayerColor() {
        Random ran = new Random();
        color = new Color(ran.nextFloat(), ran.nextFloat(), ran.nextFloat());
    }

    /**
     * @param index index of the worker to move
     * @param dir direction of movement
     * @throws InvalidActionException if movement goes out of the board
     * @throws IndexOutOfBoundsException if the index does not correspond to any worker
     */
    public void moveWorker(int index, Direction dir) throws InvalidActionException, IndexOutOfBoundsException {
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

    /**
     * @return the color
     */
    public Color getColor() { return color;}

    public God getGod() {
        return god;
    }
}
