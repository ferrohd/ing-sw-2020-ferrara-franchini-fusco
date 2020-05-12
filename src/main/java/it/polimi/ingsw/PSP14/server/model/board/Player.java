package it.polimi.ingsw.PSP14.server.model.board;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.gods.God;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Model for a player in the game.
 */
public class Player {
    private String username;
    private God god;
    private Worker[] workers = new Worker[2];
    private List<ClientConnection> clients = new ArrayList<>();

    /**
     * @param username username of the player to display in game
     */
    public Player(String username, God god, List<ClientConnection> clients) throws IOException {
        if (username == null || username.equals("")) throw new NullPointerException();
        this.username = username;
        this.god = god;
        this.clients.addAll(clients);

        for(ClientConnection c : clients) c.registerPlayer(username);
    }

    /**
     * @param index index of the worker to move
     * @param dir direction of movement
     * @throws IndexOutOfBoundsException if the index does not correspond to any worker
     */
    public void moveWorker(int index, Direction dir) throws IndexOutOfBoundsException, IOException {
        workers[index].move(dir);
        for(ClientConnection c : clients) c.notifyWorkerMove(workers[index].getPos(), username, index);
    }

    /**
     * Create, or update position, of a worker, by specifying its index
     * (0 or 1) and a new position.
     * @param index {0, 1} the index of the worker
     * @param position the position of the worker
     */
    public void setWorker(int index, Point position) throws IOException {
        if (workers[index] == null) {
            workers[index] = new Worker(position);
            for(ClientConnection c : clients) c.registerWorker(position, index, username);
        } else {
            workers[index].setPos(position);
            for(ClientConnection c : clients) c.notifyWorkerMove(position, username, index);
        }
    }

    public Point getWorkerPos(int index) {
        return workers[index].getPos();
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
