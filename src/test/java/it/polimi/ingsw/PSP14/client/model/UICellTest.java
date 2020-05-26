package it.polimi.ingsw.PSP14.client.model;

import it.polimi.ingsw.PSP14.client.view.cli.UICell;
import it.polimi.ingsw.PSP14.client.view.cli.UIPlayer;
import it.polimi.ingsw.PSP14.client.view.cli.UIWorker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UICellTest {

    UICell cell;

    @BeforeEach
    void setupCell() {
        cell = new UICell(0, 0);
    }

    @Test
    void getCoordinates() {
        assertEquals(0, cell.getX());
        assertEquals(0, cell.getY());
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
        UIWorker _w = new UIWorker(0, new UIPlayer("Ada", 0, null));
        cell.setWorker(_w);
        _w.setCell(cell);
        assertEquals(_w, cell.getWorker());
        assertEquals(_w.getCell(), cell);
    }

    @Test
    void unsetWorker() {
        assertNull(cell.getWorker());
        UIWorker _w = new UIWorker(0, new UIPlayer("Ada", 0, null));
        cell.setWorker(_w);
        assertNotNull(cell.getWorker());
        cell.unsetWorker();
        assertNull(cell.getWorker());
    }
}