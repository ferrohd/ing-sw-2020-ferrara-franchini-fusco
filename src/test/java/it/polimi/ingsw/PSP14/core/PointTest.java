package it.polimi.ingsw.PSP14.core;

import it.polimi.ingsw.PSP14.core.Direction;
import it.polimi.ingsw.PSP14.core.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    private Point point = new Point(0, 0);

    @Test
    void coordinatesAreCorrect() {
        assertEquals(0, point.getX());
        assertEquals(0, point.getY());
    }

    @Test
    void equalsReturnsTrue() {
        assertTrue(point.equals(new Point(0, 0)));
    }

    @Test
    void equalsReturnsFalse() {
        assertFalse(point.equals(new Point(1, 1)));
    }

    @Test
    void moveShouldWorkCorrectly() {

        Point p = new Point(0, 0);

        assertTrue(new Point(1, 1).equals(p.move(Direction.NE)));
    }

    @Test
    void distanceShouldThrow() {
        assertThrows(NullPointerException.class, () -> {
            new Point(0, 0).distance(null);
        });
    }

    @Test
    void distanceShouldWorkCorrectly() {
        assertEquals(2, new Point(0, 0).distance(new Point(1, 1)));
        assertEquals(1, new Point(0, 0).distance(new Point(1, 0)));
        assertEquals(0, new Point(0, 0).distance(new Point(0, 0)));
    }
}