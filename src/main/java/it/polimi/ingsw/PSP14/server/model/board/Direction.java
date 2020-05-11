package it.polimi.ingsw.PSP14.server.model.board;

/**
 * Defines the eight main cardinal directions.
 * N  (x+0, y+1) <br>
 * NE (x+1, y+1) <br>
 * NW (x-1, y+1) <br>
 * W  (x-1, y+0) <br>
 * E  (x+1, y+0) <br>
 * S  (x+0, y-1) <br>
 * SE (x+1, y+1) <br>
 * SW (x-1, y-1)
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
