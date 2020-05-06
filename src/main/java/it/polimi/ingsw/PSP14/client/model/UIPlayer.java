package it.polimi.ingsw.PSP14.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class containing all the details that are useful client-side to
 * display a player, in addition to their workers.
 * Each player has a unique number (it MAY differ on different clients).
 * Each player has a unique color (it MAY differ on different clients).
 */
public class UIPlayer {
    private final String username;
    private final int number;
    private final UIColor color;
    private UIGod god;
    private final Map<Integer, UIWorker> workers;

    /**
     * Construct a player object.
     * @param username the username of the player (retrieved from the user
     *                 or the server).
     * @param number a unique number to distinguish the player
     * @param color a unique UIColor to highlight the player's belongings.
     */
    UIPlayer(String username, int number, UIColor color) {
        this.username = username;
        this.color = color;
        this.number = number;
        this.workers = new HashMap<>();
    }

    /**
     * Retrieve a player's username
     * @return the player username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieve the color assigned to this player
     * @return the color (it may need a cast to the correct implementation)
     */
    public UIColor getColor() {
        return color;
    }

    /**
     * Retrieve the player unique number
     * @return the number
     */
    public int getNumber() { return number; }

    /**
     * Set a player god. If a god is not found, it will be <code>null</code>!
     * @param id the name retrieved from the server corresponding
     *           to this god.
     */
    public void setGod(String id) {
        try {
            this.god = (new GodFactory("src/main/resources/gods/godlist.xml")).getGod(id);
        } catch (Exception e) {
            System.out.println("ERROR: God or Godfile.xml not found!");
            e.printStackTrace();
        }
    }

    /**
     * Get the god assigned to this player
     * @return the god object
     */
    public UIGod getGod() {
        return god;
    }

    /**
     * Get all this player's workers in a List.
     * @return a list of workers
     */
    public List<UIWorker> getWorkers() {
        return new ArrayList<>(workers.values());
    }

    /**
     * Get a worker from a player.
     * @param id the id of the worker
     * @return the worker
     */
    public UIWorker getWorker(int id) {
        return workers.get(id);
    }

    /**
     * Assign a new worker to this player.
     * @param worker a new instance of a worker.
     */
    public void setWorker(UIWorker worker) {
        this.workers.put(worker.getId(), worker);
    }

    /**
     * Remove a worker from the board.
     * <br/>
     * This is intended to be used temporarily, such as
     * during a move, where you should unset a worker from its
     * old position and move it to the next.
     * <br/>
     * If you want to <strong>delete</strong> a worker instead, see {@link #removeWorker(UIWorker)}
     * @param workerId the id of the worker to move
     */
    public void unsetWorker(int workerId) {
        UIWorker _worker = getWorker(workerId);
        unsetWorker(_worker);
    }

    /**
     * Remove a worker from the board.
     * @see #unsetWorker(int)
     * @param worker a reference to the worker
     */
    public void unsetWorker(UIWorker worker) {
        if (worker != null)
            worker.unsetCell();
    }

    /**
     * Delete a worker from a player by passing the worker.
     * @param worker the worker
     */
    public void removeWorker(UIWorker worker) {
        this.workers.remove(worker.getId());
    }

    /**
     * Delete a worker from a player by using the worker id.
     * This will effectively delete a worker from the entire match.
     * (Such as when the player loses).
     * @param withId the id of the worker
     */
    public void removeWorker(int withId) {
        this.workers.remove(withId);
    }
}
