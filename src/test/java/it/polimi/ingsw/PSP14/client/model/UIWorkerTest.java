package it.polimi.ingsw.PSP14.client.model;

import it.polimi.ingsw.PSP14.client.view.cli.UICell;
import it.polimi.ingsw.PSP14.client.view.cli.UIPlayer;
import it.polimi.ingsw.PSP14.client.view.cli.UIWorker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UIWorkerTest {

    UIWorker worker;
    UIPlayer player = new UIPlayer("Ada", 0, null);
    UICell cell = new UICell(1, 1);

    @BeforeEach
    void setUp() {
        worker = new UIWorker(0, player);
    }

    @Test
    void remove() {
        worker.setCell(cell);
        cell.setWorker(worker);
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
        UICell _c1 = new UICell(0, 0);
        worker.setCell(_c1);
        assertEquals(_c1, worker.getCell());
    }

    @Test
    void unsetCell() {
        assertNull(worker.getCell());
    }
}