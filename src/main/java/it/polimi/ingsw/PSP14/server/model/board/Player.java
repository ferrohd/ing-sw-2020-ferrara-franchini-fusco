package it.polimi.ingsw.PSP14.server.model.board;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.gods.God;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Model of a player in the game.
 */
public class Player {
    private final String username;
    private final God god;
    private final Worker[] workers = new Worker[2];
    private final MatchController controller;

    /**
     * Default constructor.
     * @param username username of the player to display in game
     * @param god this player's god
     * @param controller current match controller
     * @throws IOException if it can't register the player
     */
    public Player(String username, God god, MatchController controller) throws IOException {
        this.username = username;
        this.god = god;
        this.controller = controller;

        controller.registerPlayer(username);
    }

    /**
     * Constructs a Player with a dummy MatchController.
     * @param username username of the player to display in game
     * @param god this player's god
     * @throws IOException if it can't register the player
     */
    public Player(String username, God god) throws IOException {
        this(username, god, new MatchController(new ArrayList<>()));
    }

    /**
     * Constructs a Player with a dummy God and MatchController.
     * @param username username of the player to display in game
     * @throws IOException if it can't register the player
     */
    public Player(String username) throws IOException {
        this(username, new God(username));
    }

    /**
     * Move the worker in the specified direction.
     * @param index index of the worker to move
     * @param dir direction of movement
     * @throws IndexOutOfBoundsException if the index does not correspond to any worker
     * @throws IOException if it can't notify the controller
     */
    public void moveWorker(int index, Direction dir) throws IndexOutOfBoundsException, IOException {
        workers[index].move(dir);
        controller.notifyWorkerMove(workers[index].getPos(), username, index);
    }

    /**
     * Create, or update position, of a worker, by specifying its index
     * (0 or 1) and a new position.
     *
     * @param index {0, 1} the index of the worker
     * @param position the position of the worker
     * @throws IOException if it can't register the worker
     */
    public void setWorker(int index, Point position) throws IOException {
        if (workers[index] == null) {
            workers[index] = new Worker(position);
            controller.registerWorker(position, username, index);
        } else {
            workers[index].setPos(position);
            controller.notifyWorkerMove(position, username, index);
        }
    }

    /**
     * Unregister player from the match.
     * @throws IOException if it fails to notify the controller
     */
    public void clear() throws IOException {
        controller.notifyUnregisterPlayer(username);
    }

    /**
     * Get the position of the worker of a particular index.
     * @param index {0, 1} worker to find position
     * @return the position of the selected worker
     */
    public Point getWorkerPos(int index) {
        return workers[index].getPos();
    }

    /**
     * Get the player's username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the player's god.
     * @return the god of the player
     */
    public God getGod() {
        return god;
    }
}
