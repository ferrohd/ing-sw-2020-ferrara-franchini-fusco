package it.polimi.ingsw.PSP14.core;

/**
 * Model for a worker
 */
public class Worker {
    private Point pos;

    Worker(Point pos) {
        this.pos = pos;
    }

    /**
     * @param dir the direction in which to move the worker
     * @throws InvalidActionException if movement goes out of the board
     */
    public void move(Direction dir) throws InvalidActionException {
        pos = new Point(
                pos.getX() + dir.getXOffset(),
                pos.getY() + dir.getYOffset()
        );
    }

    /**
     * @return the current position
     */
    public Point getPos() {
        return pos;
    }
}
