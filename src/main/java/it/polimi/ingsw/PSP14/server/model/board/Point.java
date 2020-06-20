package it.polimi.ingsw.PSP14.server.model.board;

import java.io.Serializable;

/**
 * An immutable object containing integer 2D coordinates.
 */
public class Point implements Serializable {
    private final int x;
    private final int y;

    /**
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Compare two points and return a boolean whether they are equals or not.
     * @param point a point to confront with
     * @return whether the points are in the same position
     */
    public boolean equals(Point point) {
        return this.x == point.x && this.y == point.y;
    }

    /**
     * Create a new point from a current one with an offset specified by a Direction.
     *
     * @param dir the direction
     * @return the new moved point
     */
    public Point move(Direction dir) {
        return new Point(
                x + dir.getXOffset(),
                y + dir.getYOffset()
        );
    }

    /**
     * Finds the manhattan distance between two points
     *
     * @param p the other point
     * @return the distance
     */
    public int distance(Point p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
