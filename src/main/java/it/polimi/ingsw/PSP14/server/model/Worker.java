package it.polimi.ingsw.PSP14.server.model;

/**
 * Model for a worker
 */
public class Worker {
    private Point pos;

    public Worker(Point pos) {
        this.pos = pos;
    }

    /**
     * @param dir the direction in which to move the worker
     * @throws InvalidActionException if the worker tries to go outside the board
     */
    public void move(Direction dir) throws InvalidActionException {
        pos = new Point(
                pos.getX() + dir.getXOffset(),
                pos.getY() + dir.getYOffset()
        );
        //TODO: Check if movement is allowed, else throw exception
    }

    /**
     * @return the current position
     */
    public Point getPos() {
        return pos;
    }
}
