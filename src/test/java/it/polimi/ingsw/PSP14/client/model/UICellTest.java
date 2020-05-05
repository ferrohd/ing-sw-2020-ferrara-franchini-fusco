package it.polimi.ingsw.PSP14.client.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UICellTest {

    UICell cell;

    @BeforeEach
    void setupCell() {
        cell = new UICell();
    }

    @Test
    void getTowerHeight() {
        assertEquals(0, cell.getTowerHeight());
    }

    @Test
    void incrementTowerHeight() {
        cell.incrementTowerHeight();
        assertEquals(1, cell.getTowerHeight());
    }

    @Test
    void decrementTowerHeight() {
        cell.incrementTowerHeight();
        cell.incrementTowerHeight();
        cell.decrementTowerHeight();
        assertEquals(1, cell.getTowerHeight());
    }

    @Test
    void hasDome() {
        assertFalse(cell.hasDome());
    }

    @Test
    void setDome() {
        cell.setDome(true);
        assertTrue(cell.hasDome());
    }

    @Test
    void getWorker() {
        assertNull(cell.getWorker());
    }

    @Test
    void hasWorker() {
        assertFalse(cell.hasWorker());
    }

    @Test
    void setWorker() {
        UIWorker _w = new UIWorker(0, new UIPlayer("Ada", null));
        cell.setWorker(_w);
        assertEquals(_w, cell.getWorker());
    }

    @Test
    void unsetWorker() {
        UIWorker _w = new UIWorker(0, new UIPlayer("Ada", null));
        cell.setWorker(_w);
        cell.unsetWorker();
        assertNull(cell.getWorker());
    }
}