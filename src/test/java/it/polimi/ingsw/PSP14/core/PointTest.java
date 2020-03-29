package it.polimi.ingsw.PSP14.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
}