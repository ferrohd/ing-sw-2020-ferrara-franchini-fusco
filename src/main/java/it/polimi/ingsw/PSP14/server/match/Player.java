package it.polimi.ingsw.PSP14.server.match;

import it.polimi.ingsw.PSP14.core.model.Direction;
import it.polimi.ingsw.PSP14.core.model.InvalidActionException;
import it.polimi.ingsw.PSP14.core.model.Worker;
import it.polimi.ingsw.PSP14.server.ClientConnection;

/**
 * Model for a player in the game.
 */
public class Player {
    private String username;
    //private God god;
    private Worker[] workers = new Worker[2];

    /**
     * @param username username of the player to display in game
     * @param connection a client connection interface to communicate with the client
     */
    Player(String username) {
        this.username = username;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /* public God getGod() {
        return god;
    } */
}
