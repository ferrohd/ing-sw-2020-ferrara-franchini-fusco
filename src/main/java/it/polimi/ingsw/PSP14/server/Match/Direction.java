package it.polimi.ingsw.PSP14.server.Match;

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

    Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }
}
