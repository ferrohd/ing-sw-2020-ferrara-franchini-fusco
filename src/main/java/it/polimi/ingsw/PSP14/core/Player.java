package it.polimi.ingsw.PSP14.core;

import java.awt.Color;
import java.util.Random;

import it.polimi.ingsw.PSP14.core.model.Direction;
import it.polimi.ingsw.PSP14.core.model.InvalidActionException;
import it.polimi.ingsw.PSP14.core.model.Worker;

/**
 * Model for a player in the game.
 */
public class Player {
    private String username;
    //private God god;
    private Worker[] workers = new Worker[2];
    private Color color;

    /**
     * @param username username of the player to display in game
     */
    public Player(String username) {
        this.username = username;
        // this.god = god;
        // workers[0] = maleWorker;
        // workers[1] = femaleWorker;
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

    /* public God getGod() {
        return god;
    } */
}
