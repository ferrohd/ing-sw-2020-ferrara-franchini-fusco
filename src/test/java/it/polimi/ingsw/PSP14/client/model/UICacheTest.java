package it.polimi.ingsw.PSP14.client.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UICacheTest {

    UICache cache;

    @BeforeEach
    void setUp() {
        cache = new UICache();
        cache.addPlayer("Ada", null);
    }

    @Test
    void getCellWithPoint() {
        assertEquals(0, cache.getCell(new UIPoint(0,0)).getTowerHeight());
    }

    @Test
    void getCellWithCoordinates() {
        assertEquals(0, cache.getCell(0,0).getTowerHeight());
    }

    @Test
    void addPlayer() {
        cache.addPlayer("Bob", null);
        assertEquals("Bob", cache.getPlayer("Bob").getUsername());
    }

    @Test
    void removePlayer() {
        cache.removePlayer("Ada");
        assertEquals(0, cache.getPlayers().size());
    }
}