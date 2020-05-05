package it.polimi.ingsw.PSP14.client.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UIPointTest {

    UIPoint point = new UIPoint(0,1);

    @Test
    void getX() {
        assertEquals(0, point.getX());
    }

    @Test
    void getY() {
        assertEquals(1, point.getY());
    }

    @Test
    void testEquals() {
        assertTrue(new UIPoint(0,1).equals(point));
    }

    @Test
    void testToString() {
        assertEquals("(0, 1)", point.toString());
    }
}