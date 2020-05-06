package it.polimi.ingsw.PSP14.client.model;

/**
 * An immutable object containing integer 2D coordinates.
 */
public class UIPoint {
    private final int x;
    private final int y;

    /**
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public UIPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the X coordinate of this point.
     * @return UIPoint.x
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y coordinate of this point.
     * @return UIPoint.y
     */
    public int getY() {
        return y;
    }

    /**
     * Compare two points and return a boolean whether they are equals or not.
     * @param point a point to confront with
     * @return whether the points are in the same position
     */
    public boolean equals(UIPoint point) {
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
