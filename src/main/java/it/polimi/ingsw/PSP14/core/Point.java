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
    Point(int x, int y) {
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
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
//
//    public void set(int x, int y) {
//        setX(x);
//        setY(y);
//    }
}
