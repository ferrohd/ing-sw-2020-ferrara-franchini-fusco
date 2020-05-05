package it.polimi.ingsw.PSP14.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class containing all the details
 * that are useful client-side to
 * display a player, in addition to their
 * workers.
 */
public class UIPlayer {
    private final String username;
    private final UIColor color;
    private UIGod god;
    private final Map<Integer, UIWorker> workers;

    UIPlayer(String username, UIColor color) {
        this.username = username;
        this.color = color;
        this.workers = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public UIColor getColor() {
        return color;
    }

    /**
     * Set a player god. If a god is not found, it will be <code>null</code>!
     * @param id the name retrieved from the server corresponding
     *           to this god.
     */
    public void setGod(String id) {
        try {
            this.god = (new GodFactory("src/main/gods/godlist.xml")).getGod(id);
        } catch (Exception e) {
            System.out.println("ERROR: God or Godfile.xml not found!");
            e.printStackTrace();
        }
    }

    public UIGod getGod() {
        return god;
    }

    /**
     * Get all this player's workers.
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

    public void setWorker(UIWorker worker) {
        this.workers.put(worker.getId(), worker);
    }

    public void unsetWorker(int workerId) {
        UIWorker _worker = getWorker(workerId);
        if (_worker != null)
            _worker.unsetCell();
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
     * @param withId the id of the worker
     */
    public void removeWorker(int withId) {
        this.workers.remove(withId);
    }
}
