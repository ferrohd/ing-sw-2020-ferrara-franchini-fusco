package it.polimi.ingsw.PSP14.client.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UIWorkerTest {

    UIWorker worker;
    UIPlayer player = new UIPlayer("Ada", null);
    UICell cell = new UICell();

    @BeforeEach
    void setUp() {
        worker = new UIWorker(0, player);
    }

    @Test
    void remove() {
        worker.remove();
        assertNull(cell.getWorker());
    }

    @Test
    void getId() {
        assertEquals(0, worker.getId());
    }

    @Test
    void getPlayer() {
        assertEquals("Ada", worker.getPlayer().getUsername());
    }

    @Test
    void getCell() {
        assertNull(worker.getCell());
    }

    @Test
    void setCell() {
        UICell _c1 = new UICell();
        worker.setCell(_c1);
        assertEquals(_c1, worker.getCell());
    }

    @Test
    void unsetCell() {
        assertNull(worker.getCell());
    }
}