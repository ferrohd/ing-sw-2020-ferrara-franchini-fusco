package it.polimi.ingsw.PSP14.client.model;

/**
 * Represent a Worker and all of its binding to
 * the owner (Player) and a board cell.
 */
public class UIWorker {
    private final int id;
    private final UIPlayer player;
    private UICell cell;
    /**
     * Constructor of UIWorker.
     * Create a worker by passing an Id and
     * the Player that owns this worker.
     * @param id the ID of this worker (typically 0 or 1)
     * @param player reference to the owner
     */
    public UIWorker(int id, UIPlayer player) {
        this.id = id;
        this.player = player;
    }

    /**
     * Removes this worker from the game,
     * safely unbinding it from both board and player.
     */
    public void remove() {
        if (cell != null)
            cell.setWorker(null);
        player.removeWorker(this);
    }

    public int getId() {
        return id;
    }

    public UIPlayer getPlayer() {
        return player;
    }

    public UICell getCell() {
        return cell;
    }

    public void setCell(UICell cell) {
        if (this.cell != null)
            this.cell.unsetWorker();
        this.cell = cell;
    }

    public void unsetCell() {
        setCell(null);
    }
}
