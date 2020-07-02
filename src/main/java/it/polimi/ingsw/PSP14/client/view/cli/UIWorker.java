package it.polimi.ingsw.PSP14.client.view.cli;

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
     * Create a worker by passing an unique ID and
     * the Player that owns this worker.
     *
     * @param id     the ID of this worker (typically 0 or 1)
     * @param player reference to the owner
     */
    public UIWorker(int id, UIPlayer player) {
        this.id = id;
        this.player = player;
    }

    /**
     * Get the worker id.
     *
     * @return the worker id
     */
    public int getId() {
        return id;
    }

    /**
     * Get this worker owner (a player). It should
     * never be null. No Ronin Workers thanks.
     *
     * @return the player that owns this worker
     */
    public UIPlayer getPlayer() {
        return player;
    }

    /**
     * Get the cell this worker is standing on right now.
     * It may be <code>null</code> if this worker is being unset by
     * either the cell or the player, meaning it's
     * being moved, for example.
     *
     * @return the cell
     */
    public UICell getCell() {
        return cell;
    }

    /**
     * Set the cell this worker should stand on.
     * It may be <code>null</code> if you want to unset this
     * worker, but to do that please use {@link #unsetCell()}.
     *
     * @param cell the target cell
     */
    public void setCell(UICell cell) {
        this.cell = cell;
    }

    /**
     * Removed the assigned cell from this worker,
     * removing it temporarily from the board.
     * This is useful to perform moves, for example.
     */
    public void unsetCell() {
        this.cell = null;
    }

    /**
     * Remove a worker from the board
     * This is useful to remove workers of player that lost the game
     */
    public void remove() {
        this.cell.unsetWorker();
        this.player.removeWorker(id);
    }
}
