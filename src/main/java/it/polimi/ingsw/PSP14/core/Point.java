package it.polimi.ingsw.PSP14.core;

/**
 * An immutable object containing integer 2D coordinates.
 */
public class Point {
    private int x;
    private int y;

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

    public Point move(Direction dir) {
        return new Point(
                x + dir.getXOffset(),
                y + dir.getYOffset()
        );
    }

    public int distance(Point p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }
}
