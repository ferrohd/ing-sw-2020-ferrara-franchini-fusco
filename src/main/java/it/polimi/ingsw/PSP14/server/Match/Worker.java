package it.polimi.ingsw.PSP14.server.Match;

public class Worker {
    private Point pos;

    Worker(Point pos) {
        this.pos = pos;
    }

    public void move(Direction dir) throws InvalidActionException {
        pos = new Point(
                pos.getX() + dir.getXOffset(),
                pos.getY() + dir.getYOffset()
        );
    }

    public Point getPos() {
        return pos;
    }
}
