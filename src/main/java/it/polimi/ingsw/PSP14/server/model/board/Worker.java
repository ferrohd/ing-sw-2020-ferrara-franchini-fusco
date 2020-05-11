package it.polimi.ingsw.PSP14.server.model.board;

/**
 * Model for a worker
 */
public class Worker {
    private Point pos;

    public Worker(Point pos) {
        this.pos = pos;
    }

    /**
     * @return the current position
     */
    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) { this.pos = pos; }

    public void move(Direction dir) {
        pos = pos.move(dir);
    }
}
