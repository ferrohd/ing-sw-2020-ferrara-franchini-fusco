package it.polimi.ingsw.PSP14.server.model.board;

/**
 * Model of a worker.
 */
public class Worker {
    private Point pos;

    /**
     * Constructs a worker at specified position
     * @param pos the position
     */
    public Worker(Point pos) {
        this.pos = pos;
    }

    /**
     * @return the current position
     */
    public Point getPos() {
        return pos;
    }

    /**
     * @param pos new position to set the worker to
     */
    public void setPos(Point pos) { this.pos = pos; }

    /**
     * @param dir direction where to move the worker
     */
    public void move(Direction dir) {
        pos = pos.move(dir);
    }
}
