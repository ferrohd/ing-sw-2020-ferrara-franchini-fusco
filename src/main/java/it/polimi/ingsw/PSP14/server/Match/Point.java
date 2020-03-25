package it.polimi.ingsw.PSP14.server.Match;

/**
 * Immutable object containing integer 2D coordinates.
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
