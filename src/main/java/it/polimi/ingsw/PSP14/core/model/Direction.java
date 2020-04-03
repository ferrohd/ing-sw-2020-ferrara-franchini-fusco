package it.polimi.ingsw.PSP14.core.model;

/**
 * Defines the eight main cardinal directions.
 */
public enum Direction {
    NW(-1, 1),
    N(0, 1),
    NE(1, 1),
    W(-1, 0),
    E(1, 0),
    SW(-1, -1),
    S(0, -1),
    SE(1, -1);

    private final int xOffset;
    private final int yOffset;

    /**
     * @param xOffset the offset in the x direction
     * @param yOffset the offset in the y direction
     */
    Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * @return the offset in the x direction
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * @return the offset in the y direction
     */
    public int getYOffset() {
        return yOffset;
    }
}
